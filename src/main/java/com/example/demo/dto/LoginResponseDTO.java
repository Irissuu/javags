package com.example.demo.dto;

import com.example.demo.model.UserRole;

public record LoginResponseDTO(String token, UserRole role) {}
