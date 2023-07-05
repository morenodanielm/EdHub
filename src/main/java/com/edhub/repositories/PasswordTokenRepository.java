package com.edhub.repositories;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edhub.models.RestablecerPasswordToken;
import com.edhub.models.Usuario;

public interface PasswordTokenRepository extends JpaRepository<RestablecerPasswordToken, Long>{
    
    RestablecerPasswordToken findByToken(String token);

    RestablecerPasswordToken findByUsuario(Usuario usuario);

    Stream<RestablecerPasswordToken> findAllByFechaVencimientoLessThan(Date ahora);

    void deleteByFechaVencimientoLessThan(Date ahora);

    @Modifying
    @Query("delete from RestablecerPasswordToken t where t.fechaVencimiento <= ?1")
    void deleteAllExpiredSince(Date ahora);
}
