package com.edhub.config;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
// proporciona las operaciones sobre los jwts, para generarlos, firmarlos y validarlos
public class JwtService {

    // llave de encriptación se usará para firmar el token
    private static final String SECRET_KEY = "5166546A576E5A7134743777217A25432A462D4A614E645267556B5870327335";

    // obtenemos el username del token, el username puede ser un user o un email en este caso
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }
    
    // extrae un solo claim, privilegio, petición o reivindicación
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    // generar un token sin claims extras, solo con userDetails
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // genera el token, con claims extras y el userDetails
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                // establece identificación del sujeto(nombre o quién) del token JWT
                .setSubject(userDetails.getUsername())
                // establece la hora en que se emitió este token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // establece hora de expiración de este token(después de pasar la fecha, ya no se debe aceptar dicho token)
                // en este caso vence en un día
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 24))
                // define con que se firma el token, en este caso getSignInKey genera el secreto y establecemos que la firma con ese secreto va a ser con el
                // algoritmo HS256(SECURE_KEY es de 256 bits)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                // finalmente, generamos el token
                .compact();

    }
    
    //validar si el token es válido, comparando el username del token con el del userDetails y verificando que el token no haya expirado
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        //si la expiración del token es antes de la actual, expiró, de lo contrario, sigue vigente
        return extractExpiration(token).before(new Date());
    }
    
     // obtener la expiración del token de los privilegios o claims
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // obtener todos los claims(privilegios) del token
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(jwtToken)
        .getBody();
    }

    // genera el secreto con el cuál se firmará el token
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
