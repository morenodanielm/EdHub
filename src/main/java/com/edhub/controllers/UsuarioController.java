package com.edhub.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import com.edhub.services.dto.PasswordDTO;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import com.edhub.models.Usuario;
import com.edhub.services.UsuarioService;
import com.edhub.utils.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuario")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JavaMailSender mailSender;
    private final MessageSource messages;
    private final Environment env;

    // retornará todos los usuarios
    @GetMapping
    public List<Usuario> obtenerTodos() {
        return usuarioService.obtenerTodos();
    }

    // retornará un usuario en específico
    @GetMapping("/findByUsername/{username}")
    public Usuario obtenerPorUsername(@PathVariable String username) {
        return usuarioService.obtenerPorUsername(username);
    }

    // actualizará un usuario
    @PutMapping("/update-user")
    public ResponseEntity<Void> actualizarUsuario(@RequestBody Usuario usuario) {
        usuarioService.actualizarUsuario(usuario);
        return ResponseEntity.ok().build();
    }

    // eliminará un usuario
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // restablecerá la contraseña
    @PostMapping("/user/resetPassword")
    public GenericResponse restablecerPassword(HttpServletRequest request, @RequestParam("email") String email) {
        Usuario usuario = usuarioService.obtenerPorEmail(email);
        String token = UUID.randomUUID().toString();
        usuarioService.crearTokenRestablecerPasswordParaUsuario(usuario, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request), 
        request.getLocale(), token, usuario));
        return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    }

    // cargará página para restablecer contraseña
    @GetMapping("/user/changePassword")
    public ResponseEntity<String> showChangePasswordPage(Locale locale, @RequestParam("token") String token) {
        String result = usuarioService.validarRestablecerPasswordToken(token);
        if(result != null) {
            String message = messages.getMessage("auth.message." + result, null, locale);
            return ResponseEntity.badRequest().body(message);
        } else {
            return ResponseEntity.ok(token);
        }
    }

    // guardará la nueva contraseña
    @PostMapping("/user/savePassword")
    public ResponseEntity<GenericResponse> savePassword(@RequestBody PasswordDTO passwordDTO, final Locale locale) {

        String result = usuarioService.validarRestablecerPasswordToken(passwordDTO.getToken());

        if(result != null) {
            return ResponseEntity.ok(new GenericResponse(messages.getMessage("auth.message." + result, null, locale)));
        }

        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorRestablecerPasswordToken(passwordDTO.getToken());
        if(usuario.isPresent()) {
            usuarioService.cambiarPasswordUsuario(usuario.get(), passwordDTO.getNewPassword());
            return ResponseEntity.ok(new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale)));
        } else {
            return ResponseEntity.ok(new GenericResponse(messages.getMessage("auth.message.invalid", null, locale)));
        }
    }

    // métodos de utilidad, no son endpoints
    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final Usuario usuario) {
        final String url = contextPath + "/user/changePassword?token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, usuario);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private SimpleMailMessage constructEmail(String subject, String body, Usuario usuario) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(usuario.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
}
