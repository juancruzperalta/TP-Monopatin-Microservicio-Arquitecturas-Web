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

import com.microservicio.dto.MonopatinDTO;
import com.microservicio.dto.ParadaDTO;
import com.microservicio.servicio.ParadaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/paradas")
@EnableMethodSecurity
public class ParadaController {

    private final ParadaService service;

    public ParadaController(ParadaService service) {
        this.service = service;
    }
    
    @Operation(summary="Obtiene todas las paradas")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<ParadaDTO> getAll() {
        return service.findAll();
    }
    
    @Operation(summary="Obtiene una parada especifica")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ParadaDTO getById(@Parameter(description = "ID de la Parada a buscar")@PathVariable Long id) {
        return service.findById(id);
    }
    
    @Operation(summary="Crea una nueva parada")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<ParadaDTO> create(@RequestBody ParadaDTO p) {
    	ParadaDTO createdDto = service.create(p);
        return ResponseEntity.created(URI.create("/paradas/" + createdDto.getId())).body(createdDto);
    }
    
    @Operation(summary="Modifica los datos de una parada")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ParadaDTO update(@Parameter(description = "ID de la Parada a modificar")@PathVariable Long id, 
    					@RequestBody ParadaDTO p) {
        return service.update(id, p);
    }
    
    @Operation(summary="Obtiene todos los monopatines de una parada")
    @GetMapping("/{id}/monopatines")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<MonopatinDTO> getMonopatinesEnParada(@Parameter(description = "ID de la Parada")@PathVariable Long id) {
        return service.getMonopatines(id);
    }

    @Operation(summary="Elimina una parada")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@Parameter(description = "ID de la Parada a eliminar")@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary="Corrobora si en una parada especifica se encuentra un monopatin en especifico")
    @GetMapping("/{id}/monopatin/{idMonopatin}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Boolean hayMonopatinEnParada(@Parameter(description = "ID de la Parada")@PathVariable Long id,
    									@Parameter(description = "ID del monopatin")@PathVariable Long idMonopatin) {
        return service.getMonopatinByParada(id,idMonopatin);
    }
}
