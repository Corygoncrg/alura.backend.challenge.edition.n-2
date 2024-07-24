package com.example.alura.challenge.edition.n2.controller;

import com.example.alura.challenge.edition.n2.domain.dto.authentication.AuthenticationDTO;
import com.example.alura.challenge.edition.n2.domain.service.AuthenticationService;
import com.example.alura.challenge.edition.n2.domain.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    private ResponseEntity<AuthenticationService> login(@RequestBody @Valid AuthenticationDTO dto) {
        return loginService.login(dto);
    }

}
