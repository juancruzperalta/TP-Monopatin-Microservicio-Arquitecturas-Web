package com.microservicio.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.modelo.Monopatin;


public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {
}