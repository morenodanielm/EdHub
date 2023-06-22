package com.edhub.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.edhub.exceptions.EdhubExceptions;
import com.edhub.models.Usuario;
import com.edhub.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public void agregarUsuario(Usuario usuario) {
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

    public void eliminarUsuario(Long id) {
        if(!usuarioRepository.existsById(id)) {
            throw new EdhubExceptions("Usuario no fue hallado", HttpStatus.NOT_FOUND);
        }
        usuarioRepository.deleteById(id);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        if(!usuarioRepository.existsById(id)) {
            throw new EdhubExceptions("Usuario " + usuario.getUsername() + " no hallado", HttpStatus.NOT_FOUND);
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public boolean existePorId(Long id) {
        return usuarioRepository.existsById(id);
    }
}
