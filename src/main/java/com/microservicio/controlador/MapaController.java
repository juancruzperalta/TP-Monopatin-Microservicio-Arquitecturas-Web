package com.microservicio.controlador;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.modelo.Mapa;
import com.microservicio.servicio.MapaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
@RestController
@RequestMapping("/mapas")
@EnableMethodSecurity
public class MapaController {
	private final MapaService service;

    public MapaController(MapaService service) {
        this.service = service;
    }
    
    @Operation(summary = "Lista todos los mapas")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<Mapa> getAll() {
        return service.findAll();
    }

    @Operation(summary = "Buscar un mapa por ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Mapa getById(@Parameter(description = "ID del mapa") 
    					@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Crea un nuevo mapa")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Mapa> create(@RequestBody Mapa m) {
        Mapa created = service.create(m);
        return ResponseEntity.created(URI.create("/mapas/" + created.getId())).body(created);
    }
    
    @Operation(summary = "Actualizar un mapa por ID")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Mapa update(@Parameter(description = "ID del mapa")
    				   @PathVariable Long id, @RequestBody Mapa m) {
        return service.update(id, m);
    }
    
    @Operation(summary = "Eliminar un mapa")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@Parameter(description = "ID del mapa")
    								   @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
