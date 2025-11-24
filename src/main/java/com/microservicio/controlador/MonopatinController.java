package com.microservicio.controlador;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.dto.MonopatinDTO;
import com.microservicio.modelo.Monopatin;
import com.microservicio.servicio.MonopatinService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/monopatines")
@EnableMethodSecurity
public class MonopatinController {

    private final MonopatinService service;

    public MonopatinController(MonopatinService service) {
        this.service = service;
    }

    @Operation(summary="Obtiene todos los monopatines")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public List<MonopatinDTO> getAll() {
        return service.findAll();
    }

    @Operation(summary="Obtiene el monopatin con la id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public MonopatinDTO getById(
    		@Parameter(description = "ID del monopatin a buscar") 
    		@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @Operation(summary="Crea un monopatin")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<MonopatinDTO> create(@RequestBody MonopatinDTO m) {
        MonopatinDTO created = service.create(m);
        return ResponseEntity.created(URI.create("/monopatines/" + created.getId())).body(created);
    }

    @Operation(summary="Modifica los datos de un monopatin")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public MonopatinDTO update(
    		@Parameter(description = "ID del monopatin a actualizar") 
    		@PathVariable("id") Long id, @RequestBody MonopatinDTO m) {
        return service.update(id, m);
    }

    @Operation(summary="Borra el monopatin con la id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(
    		@Parameter(description = "ID del monopatin a eliminar") 
    		@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary="Comienza el mantenimiento de un monopatin con la id")
    @PutMapping("/{id}/mantenimiento/inicio")
    @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE')")
    public Monopatin comenzar(
    		@Parameter(description = "ID del monopatin en mantenimiento") 
    		@PathVariable("id") Long id) {
        return service.comenzarMantenimiento(id);
    }

    @Operation(summary="Finaliza el mantenimiento de un monopatin con la id")
    @PutMapping("/{id}/mantenimiento/fin")
    @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE')")
    public Monopatin terminar(
    		@Parameter(description = "ID del monopatin en mantenimiento") 
    		@PathVariable("id") Long id) {
        return service.terminarMantenimiento(id);
    }
    
    @Operation(summary="Cambia la ubicación del monopatin (int Latitud, int longitud)")
    @PatchMapping("/{id}/localizacion")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Monopatin desplazarMonopatin(
    		@Parameter(description = "ID del monopatin") 
    		@PathVariable("id") Long id, @RequestBody Map<String, Object> fields) {
        return service.cambiarUbicacion(id, fields);
    }
    
    @Operation(summary="Inicia la pausa de un monopatin")
    @PatchMapping("/{id}/pausa")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Monopatin pausa(
    		@Parameter(description = "ID del monopatin a pausar") 
    		@PathVariable("id") Long id) {
        return service.pausarMonopatin(id);
    }
    
    @Operation(summary="Suma kilometros en un monopatin")
    @PatchMapping("/{id}/sumarKilometros")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public Monopatin sumarkm(
    		@Parameter(description = "ID del monopatin") 
    		@PathVariable("id") Long id, 
    		@Parameter(description = "kilometros a sumar (double)") 
    		@RequestParam double km) {
        return service.sumarkm(id,km);
    }
    
    @Operation(summary="Finaliza la pausa de un monopatin")
    @PatchMapping("/{id}/reanudar")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Monopatin reanudar(
    		@Parameter(description = "ID del monopatin a reanudar") 
    		@PathVariable("id") Long id) {
        return service.reanudarMonopatin(id);
    }
    
    @Operation(summary="Obtiene los monopatines mas cercanos desde una ubicacion dada (int latitud, int longitud)")
    @GetMapping("/cercanos")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public List<Monopatin> cercanos(
    		@Parameter(description = "latitud (double)") 
    		@RequestParam double lat,
    		@Parameter(description = "longitud (double)") 
            @RequestParam double lon
    ) {
        return service.monopatinesCercanos(lat, lon);
    }
    
    @Operation(summary="Cuenta todos los monopatines en mantenimiento")
    @GetMapping("/mantenimiento")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public int mantenimiento() {
        return service.countMantenimiento();
    }

    @Operation(summary="Cuenta todos los monopatines disponibles")
    @GetMapping("/disponibles")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public int disponibles() {
        return service.countDisponibles();
    }
    
    @Operation(summary="Cuenta los monopatines en mantenimiento vs los disponibles")
    @GetMapping("/estadistica/estadoMonopatines")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Map<String, Long> reportedisponibles() {
        return service.countEstado();
    }
    
    @Operation(summary="Obtiene todos los monopatines de una parada")
    @GetMapping(params = "paradaId")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<MonopatinDTO> getByParada(@Parameter(description = "id de la parada")@RequestParam Long paradaId) {
        return service.findByParadaId(paradaId);
    }
    
}
