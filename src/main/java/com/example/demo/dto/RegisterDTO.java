package com.example.demo.dto;

import com.example.demo.model.UserRole;

public record RegisterDTO(String nome, String cpf, String email, String senha, UserRole role) {}

