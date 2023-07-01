package com.edhub.services;

import com.edhub.exceptions.EdhubExceptions;
import com.edhub.models.*;
import com.edhub.repositories.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
// clase de servicio para entidad tutor
public class TutorService {

    private final TutorRepository tutorRepository;
    private final UsuarioService usuarioService;

    // agregará un nuevo tutor
    public Tutor agregarTutor(Tutor tutor) {
    	// primero se validará si el tutor ya existe
        if (tutor.getIdUsuario() != null) {
            if (tutorRepository.existsById(tutor.getIdUsuario())) {
                throw new EdhubExceptions("El usuario ya existe", HttpStatus.CONFLICT);
            }
        }
        return tutorRepository.save(tutor);
    }

    // actualizará el tutor
    public void actualizarTutor(Tutor tutor) {
    	// se validará que el tutor exista 
        if (!tutorRepository.existsById(tutor.getIdUsuario())) {
            throw new EdhubExceptions("El usuario no existe", HttpStatus.CONFLICT);
        }
        tutorRepository.save(tutor);
    }

    // convertirá un usuario común, en tutor
    public Tutor deUsuarioATutor(Long id, Especialidad[] especialidades) {
    	// se validará que el usuario exista 
        if (!usuarioService.existePorId(id)) {
            throw new EdhubExceptions("El usuario no existe", HttpStatus.NOT_FOUND);
        }
        // crea un objeto tutor a partir del objeto usuario y ademas, agrega los atributos unicos del tutor
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
        tutor.setEspecialidad(EnumSet.copyOf(Arrays.asList(especialidades)));
        tutor.setRole(Role.ROLE_TUTOR);
        return tutorRepository.save(tutor);
    }

    // retornará todos los tutores con cierta especialidad
    public List<Tutor> obtenerTodoPorEspecialidad(Especialidad especialidad) {
        if (!tutorRepository.existsByEspecialidad(especialidad)) {
            throw new EdhubExceptions("Especialidad no hallada", HttpStatus.NOT_FOUND);
        }
        return tutorRepository.findAllByEspecialidad(especialidad);
    }

    // obtiene todos los tutores
    public List<Tutor> obtenerTodos() {
        return tutorRepository.findAll();
    }
}
