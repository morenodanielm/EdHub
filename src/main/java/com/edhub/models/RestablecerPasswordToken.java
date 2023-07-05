package com.edhub.models;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restablecer_password_token")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestablecerPasswordToken {
 
    private static final int EXPIRATION = 60 * 24;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String token;
 
    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;
 
    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    public RestablecerPasswordToken(Usuario usuario, String token) {
        this.usuario = usuario;
        this.token = token;
    }
}
