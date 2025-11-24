package com.microservicio.seguridad;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;


@Component
public class JwtUtil {
	
	/*Acá vamos a validar el token*/
	private final String SECRET = "c2VjcmV0X21hdGl5anVhbl9zdXBlcl9rZXlfMjU2X2JpdHM=";

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
    	Object rolesObject = extractClaims(token).get("roles");
        if (rolesObject instanceof List<?> list) {
            List<String> roles = new ArrayList<>();
            for (Object r : list) {
                roles.add(r.toString());
            }
            return roles;
        }
        return new ArrayList<>();
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Decoders.BASE64.decode(SECRET))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(Decoders.BASE64.decode(SECRET))
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("JWT INVALIDO: " + e.getMessage());
            return false;
        }
    }

}
