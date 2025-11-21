package com.microservicio.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.microservicio.dto.MonopatinDTO;
import com.microservicio.dto.ParadaDTO;
import com.microservicio.modelo.Monopatin;
import com.microservicio.modelo.Parada;
import com.microservicio.repositorio.ParadaRepository;

@Service
public class ParadaService {

    private static final HttpStatusCode NOT_FOUND = null;
	private final ParadaRepository repository;
    private final MonopatinService monopatinService;

    public ParadaService(ParadaRepository repository,MonopatinService monopatinService) {
        this.repository = repository;
        this.monopatinService = monopatinService;
    }

    public List<ParadaDTO> findAll() {
    	List<Parada> paradas = repository.findAll();
        List<ParadaDTO> dtos = new ArrayList<>();
        for (Parada p : paradas) {
            dtos.add(EntidadaDTO(p));
        }
        return dtos;
    }

    public ParadaDTO findById(Long id) {
        Parada parada = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la parada"));
        return EntidadaDTO(parada);
    }

    public ParadaDTO create(ParadaDTO dto) {
    	 if (dto.getLatitud() == 0 || dto.getLongitud() == 0) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Latitud y longitud son obligatorias");
         }

         Parada parada = DTOaEntidad(dto);
         parada.setId(null);

         return EntidadaDTO(repository.save(parada));
    }

    public ParadaDTO update(Long id, ParadaDTO dto) {
    	Parada existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la parada"));

        if (dto.getLatitud() == 0 || dto.getLongitud() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Latitud y longitud son obligatorias");
        }

        existing.setLatitud(dto.getLatitud());
        existing.setLongitud(dto.getLongitud());

        return EntidadaDTO(repository.save(existing));
    }

    public void delete(Long id) {
    	 Parada existing = repository.findById(id)
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la parada"));

         repository.delete(existing);
    }

	public Boolean getMonopatinByParada(Long id, Long idMonopatin) {
		Parada elegida = repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No existe la parada"));
		return elegida.tieneMonopatin(idMonopatin);
	}

	//CHEQUEAR SI QUEDA BIEN UN SERVICE CON OTRO SERVICE
	public List<MonopatinDTO> getMonopatines(Long id) {
		Parada elegida = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la parada"));
		 List<MonopatinDTO> dtos = new ArrayList<>();

		    for (Monopatin m : elegida.getMonopatines()) {
		        dtos.add(monopatinService.EntidadaDTO(m)); 
		    }

		    return dtos;
	}
    
	private Parada DTOaEntidad(ParadaDTO dto) {
	    Parada parada = new Parada();
	    parada.setLatitud(dto.getLatitud());
	    parada.setLongitud(dto.getLongitud());
	    return parada;
	}
	
	private ParadaDTO EntidadaDTO(Parada parada) {
		List<Long> monopatinesId = new ArrayList<>();
	    if (parada.getMonopatines() != null) {
	        for (Monopatin m : parada.getMonopatines()) {
	            monopatinesId.add(m.getId());
	        }
	    }
	    return new ParadaDTO(
	    		parada.getId(),
	            parada.getLatitud(),
	            parada.getLongitud(),
	            monopatinesId
	    );
	}
}
