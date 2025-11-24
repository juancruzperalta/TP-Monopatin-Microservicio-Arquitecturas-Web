package com.microservicio.servicio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.microservicio.dto.MonopatinDTO;
import com.microservicio.modelo.Monopatin;
import com.microservicio.repositorio.MonopatinRepository;

@Service
public class MonopatinService {

	private final MonopatinRepository repository;

    public MonopatinService(MonopatinRepository repository) {
        this.repository = repository;
    }

    public List<MonopatinDTO> findAll() {
    	List<Monopatin> monopatines = repository.findAll();
        List<MonopatinDTO> dtos = new ArrayList<>();
        for (Monopatin m : monopatines) {
            dtos.add(EntidadaDTO(m));
        }
        return dtos;
    }

    public MonopatinDTO findById(Long id) {
    	Monopatin monopatin = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el monopatin"));
        return EntidadaDTO(monopatin);
    }

    public MonopatinDTO create(MonopatinDTO dto) {
    	Monopatin monopatin = DTOaEntidad(dto);
    	monopatin.setId(null);
    	return EntidadaDTO(repository.save(monopatin));
    }

    public MonopatinDTO update(Long id, MonopatinDTO dto) {
    	 Monopatin existing = repository.findById(id)
    			 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el monopatin"));
    	 
    	 	existing.setParadaID(dto.getParadaId());
    	    existing.setActivo(dto.isActivo());
    	    existing.setKilometros(dto.getKilometros());
    	    existing.setMantenimiento(dto.isMantenimiento());
    	    existing.setLatitud(dto.getLatitud());
    	    existing.setLongitud(dto.getLongitud());
            return EntidadaDTO(repository.save(existing));

    }
    
    public Monopatin cambiarUbicacion(Long id, Map<String, Object> fields) {
    	 Monopatin existing = repository.findById(id)
    			 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el monopatin"));

        if (fields.containsKey("latitud")) {
            Object v = fields.get("latitud");
            if (v == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "latitud no puede ser nula");

            existing.setLatitud(Double.parseDouble(v.toString()));
        }

        if (fields.containsKey("longitud")) {
            Object v = fields.get("longitud");
            if (v == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "longitud no puede ser nula");

            existing.setLongitud(Double.parseDouble(v.toString()));
        }

        return repository.save(existing);
    }
    
    public Monopatin pausarMonopatin(Long id) {
    	Monopatin m = repository.findById(id)
    			 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el monopatin"));

        m.setActivo(false);

        return repository.save(m);
    }
    
    public Monopatin reanudarMonopatin(Long id) {
    	Monopatin m = repository.findById(id)
   			 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el monopatin"));

        m.setActivo(true);

        return repository.save(m);
    }

    public void delete(Long id) {
        Monopatin existing = repository.findById(id)
   			 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el monopatin"));
        repository.delete(existing);
    }
    
    public Monopatin comenzarMantenimiento(Long id) {
        Monopatin m = repository.findById(id)
   			 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el monopatin"));

        m.setMantenimiento(true);
        m.setActivo(false);

        return repository.save(m);
    }
    
    public Monopatin terminarMantenimiento(Long id) {
        Monopatin m = repository.findById(id)
   			 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el monopatin"));

        m.setMantenimiento(false);
        m.setActivo(true);

        return repository.save(m);
    }
    
    public List<Monopatin> monopatinesCercanos(double lat, double lon) {
        List<Monopatin> todos = repository.findAll();

        if (todos.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay monopatines");

        return todos.stream()
                .sorted(Comparator.comparingDouble(
                        m -> distancia(lat, lon, m.getLatitud(), m.getLongitud())
                ))
                .collect(Collectors.toList());
    }

    private double distancia(double lat1, double lon1, double lat2, double lon2) {
        double dx = lat1 - lat2;
        double dy = lon1 - lon2;
        return Math.sqrt(dx*dx + dy*dy);
    }
    
    
    public int countMantenimiento() {
        List<Monopatin> monopatines = repository.findAll();
        
        int count = 0;
        Iterator<Monopatin> it = monopatines.iterator();

        while (it.hasNext()) {
            Monopatin m = it.next();
            if (m.isMantenimiento()) {
                count++;
            }
        }

        return count;
    }

    public int countDisponibles() {
        List<Monopatin> monopatines = repository.findAll();

        int count = 0;

        for (Monopatin m : monopatines) {
            if (!m.isMantenimiento() && m.isActivo()) {
                count++;
            }
        }
        return count;
    }

	public Monopatin sumarkm(Long id, double km) {
	    if (km < 0) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los km deben ser positivos");
	    }

	    Monopatin m = repository.findById(id)
   			 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el monopatin"));
	    m.setKilometros(m.getKilometros() + km);
	    return repository.save(m);
	}

	public Map<String, Long> countEstado() {
		long enOperacion = this.countDisponibles();
	    long enMantenimiento = this.countMantenimiento();

	    Map<String, Long> resultado = new HashMap<>();
	    resultado.put("Monopatines En operacion", enOperacion);
	    resultado.put("Monopatines En mantenimiento", enMantenimiento);

	    return resultado;
	}

	private Monopatin DTOaEntidad(MonopatinDTO dto) {
	    Monopatin monopatin = new Monopatin();
	    monopatin.setParadaID(dto.getParadaId());
	    monopatin.setLatitud(dto.getLatitud());
	    monopatin.setLongitud(dto.getLongitud());
	    monopatin.setActivo(dto.isActivo());
	    monopatin.setKilometros(dto.getKilometros());
	    monopatin.setMantenimiento(dto.isMantenimiento());

        return monopatin;
	}
	
	public MonopatinDTO EntidadaDTO(Monopatin monopatin) {
		MonopatinDTO dto = new MonopatinDTO();
        dto.setId(monopatin.getId());
        dto.setLatitud(monopatin.getLatitud());
        dto.setLongitud(monopatin.getLongitud());
        dto.setActivo(monopatin.isActivo());
        dto.setKilometros(monopatin.getKilometros());
        dto.setMantenimiento(monopatin.isMantenimiento());

        if (monopatin.getParadaID() != null) {
            dto.setParadaId(monopatin.getParadaID());
        }

        return dto;
	}

	public List<MonopatinDTO> findByParadaId(Long paradaId) {
	    List<Monopatin> lista = repository.findByParadaId(paradaId);

	    List<MonopatinDTO> dtos = new ArrayList<>();
	    for (Monopatin m : lista) {
	        dtos.add(EntidadaDTO(m));
	    }
	    return dtos;
	}

    
}