package com.microservicio.servicio;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.microservicio.modelo.Mapa;
import com.microservicio.repositorio.MapaRepository;

@Service
public class MapaService {

    private static final HttpStatusCode NOT_FOUND = null;
	private final MapaRepository repository;

    public MapaService(MapaRepository repository) {
        this.repository = repository;
    }

    public List<Mapa> findAll() {
        return repository.findAll();
    }

    public Mapa findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Mapa no encontrado"));
    }

    public Mapa create(Mapa m) {
        m.setId(null);
        return repository.save(m);
    }

    public Mapa update(Long id, Mapa m) {
        Mapa existing = findById(id);

        existing.setParadas(m.getParadas());
        return repository.save(existing);
    }

    public void delete(Long id) {
        Mapa existing = findById(id);
        repository.delete(existing);
    }
}
