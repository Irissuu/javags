package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "regiao_Java")
public class RegiaoJava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String estado;

    @NotBlank
    private String distrito;

    @ManyToMany(mappedBy = "regioes")
    private List<UsuarioJava> usuarios = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public List<UsuarioJava> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioJava> usuarios) {
        this.usuarios = usuarios;
    }
}

