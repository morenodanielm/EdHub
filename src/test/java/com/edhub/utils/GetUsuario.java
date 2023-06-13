package com.edhub.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.edhub.models.Role;
import com.edhub.models.Usuario;

public class GetUsuario {

    public static List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        // usuario 1
        Usuario usuario1 = new Usuario(
                "carlos19",
                "carlos@mail.com",
                "12345678",
                Role.ROLE_USER,
                LocalDateTime.now());

        // usuario 2
        Usuario usuario2 = new Usuario(
                "daniel21",
                "daniel@mail.com",
                "12345678",
                Role.ROLE_ADMIN,
                LocalDateTime.now());

        usuarios.add(usuario1);
        usuarios.add(usuario2);
        return usuarios;
    }

}
