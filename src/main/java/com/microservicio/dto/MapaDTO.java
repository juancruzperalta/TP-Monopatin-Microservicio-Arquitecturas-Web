package com.microservicio.dto;

import java.util.List;

public class MapaDTO {

    private List<Long> paradasId;

    public MapaDTO() {
    }

    public MapaDTO(List<Long> paradasId) {
        this.paradasId = paradasId;
    }

    public List<Long> getParadasId() { 
    	return paradasId; 
    	}
    
    public void setParadasId(List<Long> paradasId) { 
    	this.paradasId = paradasId; 
    	}
}
