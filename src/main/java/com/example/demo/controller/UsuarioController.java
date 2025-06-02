package com.example.demo.controller;

import com.example.demo.dto.UsuarioRequest;
import com.example.demo.dto.UsuarioResponse;
import com.example.demo.model.UsuarioJava;
import com.example.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "api-usuarios-java")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Lista todos os usuários")
    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> readUsuarios(@RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("nome").ascending());
        Page<UsuarioJava> usuariosPage = usuarioService.readUsuarios(pageable);

        Page<UsuarioResponse> responsePage = usuariosPage.map(usuario -> {
            UsuarioResponse dto = new UsuarioResponse();
            dto.setId(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            return dto;
        });

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @Operation(summary = "Retorna um usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> readUsuario(@PathVariable Long id) {
        UsuarioJava usuario = usuarioService.readUsuarioById(id);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequest usuarioRequest) {
        UsuarioJava usuario = new UsuarioJava();
        usuario.setNome(usuarioRequest.getNome());
        usuario.setEmail(usuarioRequest.getEmail());

        usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioRequest.getSenha()));

        UsuarioJava updated = usuarioService.updateUsuario(id, usuario);

        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UsuarioResponse response = new UsuarioResponse();
        response.setId(updated.getId());
        response.setNome(updated.getNome());
        response.setEmail(updated.getEmail());
        response.setCpf(updated.getCpf());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Exclui um usuário por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        UsuarioJava existing = usuarioService.readUsuarioById(id);
        if (existing != null) {
            usuarioService.deleteUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
