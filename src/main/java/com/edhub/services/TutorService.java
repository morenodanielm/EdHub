package com.edhub.services;

import com.edhub.exceptions.EdhubExceptions;
import com.edhub.models.Especialidad;
import com.edhub.models.Role;
import com.edhub.models.Tutor;
import com.edhub.models.Usuario;
import com.edhub.repositories.TutorRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final TutorRepository tutorRepository;
    private final UsuarioService usuarioService;

    public Tutor agregarTutor(Tutor tutor) {
        if(tutorRepository.existsById(tutor.getIdUsuario())) {
            throw new EdhubExceptions("El usuario ya existe", HttpStatus.CONFLICT);
        }
        return tutorRepository.save(tutor);
    }

    public Tutor deUsuarioATutor(Long id) {
        if (!usuarioService.existePorId(id)) {
            throw new EdhubExceptions("El usuario no existe", HttpStatus.NOT_FOUND);
        }
        Usuario usuario = usuarioService.obtenerPorId(id);
        Tutor tutor = new Tutor();
        tutor.setIdUsuario(usuario.getIdUsuario());
        tutor.setCalificacionesComoCalificado(usuario.getCalificacionesComoCalificado());
        tutor.setCalificacionesComoCalificador(usuario.getCalificacionesComoCalificador());
        tutor.setUsername(usuario.getUsername());
        tutor.setEmail(usuario.getEmail());
        tutor.setComentarios(usuario.getComentarios());
        tutor.setMensajesEnviados(usuario.getMensajesEnviados());
        tutor.setMensajesRecibidos(usuario.getMensajesRecibidos());
        tutor.setFechaCreacion(usuario.getFechaCreacion());
        tutor.setPassword(usuario.getPassword());
        tutor.setRole(Role.ROLE_TUTOR);
        return tutor;
    }

    public List<Tutor> obtenerTodoPorEspecialidad(Especialidad especialidad) {
        if (!tutorRepository.existsByEspecialidad(especialidad)) {
            throw new EdhubExceptions("Especialidad no hallada", HttpStatus.NOT_FOUND);
        }
        return tutorRepository.findAllByEspecialidad(especialidad);
    }
}
