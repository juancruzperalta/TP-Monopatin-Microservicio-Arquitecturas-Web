package com.microservicio.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.modelo.Parada;

public interface ParadaRepository extends JpaRepository<Parada, Long> {
}