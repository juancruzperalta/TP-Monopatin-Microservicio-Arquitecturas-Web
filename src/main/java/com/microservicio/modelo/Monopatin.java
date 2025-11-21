package com.microservicio.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "monopatin")
public class Monopatin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="parada")
	private Long paradaId;
	@Column (name="activo")
	private boolean activo;
	@Column (name="kilometros")
	private double kilometros;
	@Column (name="mantenimiento")
	private boolean mantenimiento;
	@Column(name="latitud")
	private double latitud;
	@Column(name="longitud")
	private double longitud;
	

	public Monopatin() {
	}

	public Monopatin(Long paradaId, boolean activo, double kilometros, boolean mant, double longitud,double latitud) {
		super();
		this.paradaId = paradaId;
		this.activo = activo;
		this.kilometros = kilometros;
		this.mantenimiento = mant;
		this.longitud = longitud;
		this.latitud = latitud;
	}

	public Long getId() {
		return id;
	}

	public Long getParadaID() {
		return paradaId;
	}

	public void setParadaID(Long paradaId) {
		this.paradaId = paradaId;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getKilometros() {
		return kilometros;
	}

	public void setKilometros(double kilometros) {
		this.kilometros = kilometros;
	}

	public boolean isMantenimiento() {
		return mantenimiento;
	}

	public void setMantenimiento(boolean mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

}
