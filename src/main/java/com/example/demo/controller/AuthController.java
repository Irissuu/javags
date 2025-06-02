package com.example.demo.controller;


import com.example.demo.dto.AuthDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.model.UsuarioJava;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthDTO authDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                authDTO.email(), authDTO.senha()
        );

        var auth = authenticationManager.authenticate(usernamePassword);
        var usuario = (UsuarioJava) auth.getPrincipal();
        var token = tokenService.generateToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(token, usuario.getRole()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerDTO) {
        if (usuarioRepository.findByEmail(registerDTO.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPwd = new BCryptPasswordEncoder().encode(registerDTO.senha());

        UsuarioJava newUser = new UsuarioJava(
                registerDTO.nome(),
                registerDTO.cpf(),
                registerDTO.email(),
                encryptedPwd,
                registerDTO.role()
        );

        usuarioRepository.save(newUser);
        return ResponseEntity.ok().build();
    }


}
