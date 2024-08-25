package org.sid.ebankingbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager; // on ne trouve pas c'est la resson d'ajouter une methode dans SecurityConfig qui retourne un objet de type AuthenticationManager
    @Autowired
    private JwtEncoder jwtEncoder; // on ne trouve pas c'est la resson d'ajouter une methode dans SecurityConfig qui retourne un objet de type JwtEncoder

    @GetMapping("/profile")
    public Authentication authenticate(Authentication authentication) {
        return authentication;
    }

    @PostMapping("/login") // cette methode permet d'authentifier un utilisateur
    public Map<String,String> login(String username,String password){
        // pour authentifier un utilisateur on doit utiliser l'objet authenticationManager
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
         // si l'authentification est reussie on va generer un token JWT
        Instant instant = Instant.now();
        String scope = authentication.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(username)
                .issuedAt(instant)
                .expiresAt(instant.plus(10, java.time.temporal.ChronoUnit.MINUTES))
                .claim("scope",scope)
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(),jwtClaimsSet);
        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
         return Map.of("access_token",jwt);
    }
}
