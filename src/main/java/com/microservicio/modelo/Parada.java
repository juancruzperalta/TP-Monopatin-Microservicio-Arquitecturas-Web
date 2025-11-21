package com.microservicio.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "parada")
public class Parada {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(mappedBy = "parada")
	private List<Monopatin> monopatines;

	@Column(name="latitud")
	private double latitud;
	@Column(name="longitud")
	private double longitud;
	
	public Parada() {
	}
	public Parada(List<Monopatin> monopatines, double latitud, double longitud) {
		super();
		this.monopatines = monopatines;
		this.latitud = latitud;
		this.longitud = longitud;
	}
	
	public List<Monopatin> getMonopatines() {
		return new ArrayList<Monopatin>(monopatines);
	}
	public void setMonopatines(List<Monopatin> monopatines) {
		this.monopatines = monopatines;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public Long getId() {
		return id;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public void setId(Long id) {
		this.id = id;	
	}
	public boolean tieneMonopatin(Long id) {
		for (Monopatin monopatin : monopatines) {
			  if(monopatin.getId() == id) {
				  return true;
			  }
			}
		return false;
	}
	
}
