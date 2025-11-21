package com.microservicio.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "mapa")
public class Mapa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany
	private List<Parada> paradas;

	public Mapa() {
	}

	public Mapa(List<Parada> paradas) {
		super();
		this.paradas = paradas;
	}

	public List<Parada> getParadas() {
		return new ArrayList<Parada>(paradas);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setParadas(List<Parada> paradas) {
		this.paradas = paradas;
	}

	public void setId(Long id) {
		this.id = id;
		
	}
	
}
