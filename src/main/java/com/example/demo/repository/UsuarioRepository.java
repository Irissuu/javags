package com.example.demo.repository;

import com.example.demo.model.UsuarioJava;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioJava, Long> {
    UsuarioJava findByEmail(String email);
}


