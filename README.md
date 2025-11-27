# TP-Microservicios-TUDAI



En este microservicio lo que hicimos fue realizar el de monopatin, usamos un repositorio, un controller,un servicio , también implementamos el uso de un controller y de JWT. 
- En el **controller** hicimos todos los endpoints y utilizamos el service para ejecutar la lógica de negocio.
- En el **service** nos comunicamos con el repositorio. En el service vamos a hacer la lógica de negocio. 
- En el **repositorio** usamos el base de JPA (CRUD) -save() - findById() - findAll() - delete()

- **Este microservicio corre en el puerto 8081**

## DIAGRAMAS: 
![WhatsApp Image 2025-11-26 at 11 26 20](https://github.com/user-attachments/assets/37621d42-3b06-4f12-8134-8ab5eb150b7f)

## ENDPOINTS : Microservicio de Monopatin
http://localhost:8081/swagger-ui/index.html#/

##### GET `("/monopatines")`
- Obtiene todos los monopatines  
**PERMITIDO:** ('ROLE_USER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE')

##### GET `("/{id}")`
- Obtiene el monopatin con la id  
**PERMITIDO:**  ('ROLE_USER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE")

##### POST
- Crea un monopatin
**PERMITIDO:** ('ROLE_ADMIN')

##### PUTMAPPING `("/{id}")`
- Modifica los datos de un monopatin  
**PERMITIDO:** ('ROLE_ADMIN')

##### DELETEMAPPING `("/{id}")`
- Borra el monopatin con la id  
**PERMITIDO:** ('ROLE_ADMIN')

##### @PutMapping `("/{id}/mantenimiento/inicio")`
- Comienza el mantenimiento de un monopatin con la id  
**PERMITIDO:** ('ROLE_EMPLOYEE')

##### @PutMapping `("/{id}/mantenimiento/fin")`
- Finaliza el mantenimiento de un monopatin con la id  
**PERMITIDO:** ('ROLE_EMPLOYEE')

##### @PatchMapping `("/{id}/localizacion")`
- Cambia la ubicación del monopatin (int Latitud, int longitud)  
**PERMITIDO:** ('ROLE_ADMIN')

##### @PatchMapping `("/{id}/pausa")`
- Inicia la pausa de un monopatin  
**PERMITIDO:** ('ROLE_USER')

##### @PatchMapping `("/{id}/sumarKilometros")`
- Suma kilometros en un monopatin  
**PERMITIDO:** ('ROLE_ADMIN', 'ROLE_EMPLOYEE')

##### @PatchMapping `("/{id}/reanudar")`
- Finaliza la pausa de un monopatin  
**PERMITIDO:** ('ROLE_USER')

##### @GetMapping `("/cercanos")`
- Obtiene los monopatines mas cercanos desde una ubicacion dada (int latitud, int longitud)  
**PERMITIDO:** ('ROLE_USER')

##### @GetMapping("/mantenimiento")
- Cuenta todos los monopatines en mantenimiento  
**PERMITIDO('ROLE_ADMIN', 'ROLE_EMPLOYEE')**

##### @GetMapping `("/disponibles")`
- Cuenta todos los monopatines disponibles  
**PERMITIDO:** ('ROLE_ADMIN', 'ROLE_EMPLOYEE')

##### @GetMapping `("/estadistica/estadoMonopatines")`
- Cuenta los monopatines en mantenimiento vs los disponibles  
**PERMITIDO:**  ('ROLE_ADMIN')
