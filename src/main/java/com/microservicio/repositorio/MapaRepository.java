package com.microservicio.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.modelo.Mapa;


public interface MapaRepository extends JpaRepository<Mapa, Long> {
}
