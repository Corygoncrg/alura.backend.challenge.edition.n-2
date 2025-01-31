package com.example.alura.challenge.edition.n2.domain.service;

import com.example.alura.challenge.edition.n2.domain.dto.authentication.AuthenticationDTO;
import com.example.alura.challenge.edition.n2.domain.dto.authentication.TokenJWTDTO;
import com.example.alura.challenge.edition.n2.domain.model.User;
import com.example.alura.challenge.edition.n2.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager manager;


    /**
     * Method to return a token in the body when login is authenticated
     * @param dto AuthenticationDTO with login and password values
     * @return ResponseEntity<TokenJWTDTO>
     */
    public ResponseEntity<TokenJWTDTO> login(AuthenticationDTO dto) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
    var authentication = manager.authenticate(authenticationToken);
    var tokenJWT = tokenService.createToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDTO(tokenJWT));
    }

}
