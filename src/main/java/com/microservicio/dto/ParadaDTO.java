package com.microservicio.dto;

import java.util.List;

public class ParadaDTO {

	private Long id;
    private double latitud;
    private double longitud;
    //Guardo una lista de ids, no de objetos
    private List<Long> monopatinesId;

    public ParadaDTO() {
    }

    public ParadaDTO(Long id,double latitud, double longitud, List<Long> monopatinesId) {
        this.id = id;
    	this.latitud = latitud;
        this.longitud = longitud;
        this.monopatinesId = monopatinesId;
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

    public List<Long> getMonopatinesId() { 
    	return monopatinesId; 
    	}
    
    public void setMonopatinesId(List<Long> monopatinesId) { 
    	this.monopatinesId = monopatinesId; 
    	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long nuevo) {
		id = nuevo;
	}
}
