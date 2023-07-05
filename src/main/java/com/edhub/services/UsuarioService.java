package com.edhub.services;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.edhub.exceptions.EdhubExceptions;
import com.edhub.models.RestablecerPasswordToken;
import com.edhub.models.Usuario;
import com.edhub.repositories.PasswordTokenRepository;
import com.edhub.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// clase de servicio para entidad usuario
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordTokenRepository passwordTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public void agregarUsuario(Usuario usuario) {
        if(usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new EdhubExceptions("El usuario ya existe", HttpStatus.CONFLICT);
        }
        usuarioRepository.save(usuario);
    }

    public Usuario obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new EdhubExceptions("Usuario " + username + " no hallado", HttpStatus.NOT_FOUND));
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new EdhubExceptions("El usuario no existe", HttpStatus.NOT_FOUND));
    }

    public Usuario obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new EdhubExceptions("El usuario no existe", HttpStatus.NOT_FOUND));
    }


    public void eliminarUsuario(Long id) {
        if(!usuarioRepository.existsById(id)) {
            throw new EdhubExceptions("Usuario no fue hallado", HttpStatus.NOT_FOUND);
        }
        usuarioRepository.deleteById(id);
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        if(usuario.getIdUsuario() != null) {
            if (!usuarioRepository.existsById(usuario.getIdUsuario())) {
                throw new EdhubExceptions("Usuario " + usuario.getUsername() + " no hallado", HttpStatus.NOT_FOUND);
            }
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public boolean existePorId(Long id) {
        return usuarioRepository.existsById(id);
    }

    public void crearTokenRestablecerPasswordParaUsuario(Usuario usuario, String token) {
        RestablecerPasswordToken miToken = new RestablecerPasswordToken(usuario, token);
        passwordTokenRepository.save(miToken);
    }

    public String validarRestablecerPasswordToken(String token) {
        final RestablecerPasswordToken passToken = passwordTokenRepository.findByToken(token);

        return !tokenEncontrado(passToken) ? "invalidToken"
                : tokenExpirado(passToken) ? "expired"
                : null;
    }

    private boolean tokenEncontrado(RestablecerPasswordToken passToken) {
        return passToken != null;
    }

    private boolean tokenExpirado(RestablecerPasswordToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getFechaVencimiento().before(cal.getTime());
    }

    public void cambiarPasswordUsuario(Usuario usuario, String password) {
        usuario.setPassword(passwordEncoder.encode(password));
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obtenerUsuarioPorRestablecerPasswordToken(final String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token).getUsuario());
    }
}
