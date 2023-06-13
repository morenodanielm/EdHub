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
        if(usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new EdhubExceptions("El username " + usuario.getUsername() + " ya existe", HttpStatus.CONFLICT);
        }
        usuarioRepository.save(usuario);
    }

    public Usuario obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
        .orElseThrow(() -> new EdhubExceptions("Usuario " + username + " no hallado", HttpStatus.NOT_FOUND));
    }

    public void eliminarUsuario(String username) {
       noExisteUsername(username);
        usuarioRepository.deleteByUsername(username);
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        noExisteUsername(usuario.getUsername());
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    private void noExisteUsername(String username) {
        if(!usuarioRepository.existsByUsername(username)) {
            throw new EdhubExceptions("Usuario " + username + " no hallado", HttpStatus.NOT_FOUND);
        }
    }
    
}
