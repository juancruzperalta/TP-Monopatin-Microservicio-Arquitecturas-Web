package com.microservicio.repositorio;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.modelo.Monopatin;


public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {
	List<Monopatin> findByParadaId(Long paradaId);
}