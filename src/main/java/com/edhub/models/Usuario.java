package com.edhub.models;

import java.time.LocalDateTime;
import java.util.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
@AllArgsConstructor
@Entity
@Table(
        name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_usuario_username", columnNames = "username"),
                @UniqueConstraint(name = "unique_usuario_email", columnNames = "email")
        }
)
// esto es la herencia de entidades significa que podemos usar consultas polimórficas para recuperar todas las entidades de subclase
// al consultar una superclase.
// se define una jerarquia de entidades, donde Usuario será padre de Tutor, cada entidad tendrá su tabla y el campo primario de Tutor será 
// el mismo de Usuario
@Inheritance(strategy = InheritanceType.JOINED)
// clase modelo que representa la tabla usuarios en la BD
public class Usuario implements UserDetails {

    // atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "username")
    @Size(max = 30)
    @NotBlank
    private String username;

    @Column(name = "email")
    @Email
    @NotBlank
    private String email;

    @Column(name = "password")
    @Size(min = 8)
    @NotBlank
    private String password;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private Role role;

    @Column(name = "fecha_creacion")
    @NotNull
    private LocalDateTime fechaCreacion;

    // define la asociación indica qué acciones deben conectarse en cascada para esa asociación.
    // all = PERSIST, MERGE, REMOVE, REFRESH, DETACH
    @OneToMany(mappedBy = "usuarioRemitente", cascade = CascadeType.ALL)
    private Set<Mensaje> mensajesEnviados;

    @OneToMany(mappedBy = "usuarioDestinatario", cascade = CascadeType.ALL)
    private Set<Mensaje> mensajesRecibidos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<Comentario> comentarios;

    @OneToMany(mappedBy = "calificado", cascade = CascadeType.ALL)
    private Set<Calificacion> calificacionesComoCalificador;

    @OneToMany(mappedBy = "calificador", cascade = CascadeType.ALL)
    private Set<Calificacion> calificacionesComoCalificado;

    @OneToOne(mappedBy = "usuario")
    private RestablecerPasswordToken restablecerPasswordToken;

    // constructores
    public Usuario() {
        this.calificacionesComoCalificado = new HashSet<>();
        this.calificacionesComoCalificador = new HashSet<>();
        this.mensajesEnviados = new HashSet<>();
        this.mensajesRecibidos = new HashSet<>();
    }

    public Usuario(String username, String email, String password, Role role, LocalDateTime fechaCreacion) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.fechaCreacion = fechaCreacion;
    }

    public void addMensajeEnviado(Mensaje mensaje) {
        if (mensajesEnviados != null) {
            mensajesEnviados.add(mensaje);
        }
    }

    public void addMensajeRecibido(Mensaje mensaje) {
        if (mensajesRecibidos != null) {
            mensajesRecibidos.add(mensaje);
        }
    }


    // agrega las calificaciones hechas a ese usuario
    public void addCalificacionesCalificado(Calificacion calificacion) {
        if (calificacion != null) {
            this.calificacionesComoCalificador.add(calificacion);
        }
    }

    // agrega las calificaciones hechas por ese usuario
    public void addCalificacionesCalificador(Calificacion calificacion) {
        if (calificacion != null) {
            this.calificacionesComoCalificador.add(calificacion);
        }
    }

    //métodos de la interface userDetails
    // obtenemos una representación String de una autoridad otorgada(roles) al objeto Authentication.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    // validar si una cuenta está expirada o no
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    // validar si una cuenta está bloqueada o no
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    // validar si los credencianes han expirado o no
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    // validar si una cuenta está habilitada o no
    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // getter y setter
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Set<Mensaje> getMensajesEnviados() {
        return mensajesEnviados;
    }

    public Set<Mensaje> getMensajesRecibidos() {
        return mensajesRecibidos;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public Set<Calificacion> getCalificacionesComoCalificador() {
        return calificacionesComoCalificador;
    }

    public void setCalificacionesComoCalificador(Set<Calificacion> calificacionesComoCalificador) {
        this.calificacionesComoCalificador = calificacionesComoCalificador;
    }

    public Set<Calificacion> getCalificacionesComoCalificado() {
        return calificacionesComoCalificado;
    }

    public void setCalificacionesComoCalificado(Set<Calificacion> calificacionesComoCalificado) {
        this.calificacionesComoCalificado = calificacionesComoCalificado;
    }

    public void setMensajesEnviados(Set<Mensaje> mensajesEnviados) {
        this.mensajesEnviados = mensajesEnviados;
    }

    public void setMensajesRecibidos(Set<Mensaje> mensajesRecibidos) {
        this.mensajesRecibidos = mensajesRecibidos;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    // métodos para comparar objetos de esta clase
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(idUsuario, usuario.idUsuario) && Objects.equals(username, usuario.username)
                && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, username, email);
    }

    // proporciona información del objeto actual
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", fechaCreacion=" + fechaCreacion +
                ", comentarios=" + comentarios +
                '}';
    }

    // este método se ejecutará antes de persistir el objeto mensaje para agregar la fecha actual
    @PrePersist
    public void prePersist() {
        fechaCreacion = LocalDateTime.now();
    }

}
