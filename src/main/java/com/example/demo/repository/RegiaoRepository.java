package com.example.demo.repository;

import com.example.demo.model.RegiaoJava;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegiaoRepository extends JpaRepository<RegiaoJava, Long> {
}