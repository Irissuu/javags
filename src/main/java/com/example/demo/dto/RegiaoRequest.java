package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class RegiaoRequest {

    @NotBlank
    private String estado;

    @NotBlank
    private String distrito;

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
}
