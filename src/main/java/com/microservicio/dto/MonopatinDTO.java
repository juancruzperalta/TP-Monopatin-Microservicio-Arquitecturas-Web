package com.microservicio.dto;

public class MonopatinDTO {

	private Long id;
    private Long paradaId;
    private boolean activo;
    private double kilometros;
    private boolean mantenimiento;
    private double latitud;
    private double longitud;

    public MonopatinDTO() {
    }

    public MonopatinDTO(Long id,Long paradaId, boolean activo, double kilometros,
                        boolean mantenimiento, double latitud, double longitud) {
        this.id = id;
    	this.paradaId = paradaId;
        this.activo = activo;
        this.kilometros = kilometros;
        this.mantenimiento = mantenimiento;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParadaId() { 
    	return paradaId; 
    	}
    
    public void setParadaId(Long paradaId) { 
    	this.paradaId = paradaId; 
    	}

    public boolean isActivo() { 
    	return activo; 
    	}
    
    public void setActivo(boolean activo) { 
    	this.activo = activo; 
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
}
