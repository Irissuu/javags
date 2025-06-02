package com.example.demo.controller;

import com.example.demo.dto.RegiaoRequest;
import com.example.demo.dto.RegiaoResponse;
import com.example.demo.model.RegiaoJava;
import com.example.demo.service.RegiaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/regioes")
@Tag(name = "api-regioes-java")
public class RegiaoController {

    private final RegiaoService regiaoService;

    @Autowired
    public RegiaoController(RegiaoService regiaoService) {
        this.regiaoService = regiaoService;
    }

    @Operation(summary = "Cadastra uma região")
    @PostMapping
    public ResponseEntity<RegiaoResponse> createRegiao(@Valid @RequestBody RegiaoRequest regiaoRequest) {
        RegiaoJava regiao = new RegiaoJava();
        regiao.setEstado(regiaoRequest.getEstado());
        regiao.setDistrito(regiaoRequest.getDistrito());

        RegiaoJava saved = regiaoService.createRegiao(regiao);

        RegiaoResponse response = new RegiaoResponse();
        response.setId(saved.getId());
        response.setEstado(saved.getEstado());
        response.setDistrito(saved.getDistrito());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todas as regiões")
    @GetMapping
    public ResponseEntity<Page<RegiaoResponse>> readRegioes(@RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("distrito").ascending());
        Page<RegiaoJava> regioesPage = regiaoService.readRegioes(pageable);

        Page<RegiaoResponse> responsePage = regioesPage.map(regiao -> {
            RegiaoResponse dto = new RegiaoResponse();
            dto.setId(regiao.getId());
            dto.setEstado(regiao.getEstado());
            dto.setDistrito(regiao.getDistrito());
            return dto;
        });

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @Operation(summary = "Busca uma região por ID")
    @GetMapping("/{id}")
    public ResponseEntity<RegiaoResponse> readRegiaoById(@PathVariable Long id) {
        RegiaoJava regiao = regiaoService.readRegiaoById(id);
        if (regiao == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        RegiaoResponse response = new RegiaoResponse();
        response.setId(regiao.getId());
        response.setEstado(regiao.getEstado());
        response.setDistrito(regiao.getDistrito());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza uma região por ID")
    @PutMapping("/{id}")
    public ResponseEntity<RegiaoResponse> updateRegiao(@PathVariable Long id, @Valid @RequestBody RegiaoRequest regiaoRequest) {
        RegiaoJava regiao = new RegiaoJava();
        regiao.setEstado(regiaoRequest.getEstado());
        regiao.setDistrito(regiaoRequest.getDistrito());

        RegiaoJava updated = regiaoService.updateRegiao(id, regiao);
        if (updated == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        RegiaoResponse response = new RegiaoResponse();
        response.setId(updated.getId());
        response.setEstado(updated.getEstado());
        response.setDistrito(updated.getDistrito());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Exclui uma região por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegiao(@PathVariable Long id) {
        RegiaoJava existing = regiaoService.readRegiaoById(id);
        if (existing != null) {
            regiaoService.deleteRegiao(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

