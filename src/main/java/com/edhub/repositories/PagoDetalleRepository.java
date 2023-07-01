package com.edhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edhub.models.PagoDetalle;

// clase de acceso a datos para la entidad pagoDetalle
public interface PagoDetalleRepository extends JpaRepository<PagoDetalle, Long>{

}
