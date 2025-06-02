package com.example.demo.service;

import com.example.demo.model.UsuarioJava;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    @CachePut(value = "usuarios", key = "#result.id")
    public UsuarioJava createUsuario(UsuarioJava usuario) {
        return usuarioRepository.save(usuario);
    }

    @Cacheable(value = "usuarios", key = "#id")
    public UsuarioJava readUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "usuarios", key = "'todos'")
    public Page<UsuarioJava> readUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Transactional
    @CachePut(value = "usuarios", key = "#result.id")
    public UsuarioJava updateUsuario(Long id, UsuarioJava usuario) {
        Optional<UsuarioJava> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            return null;
        }

        UsuarioJava usuarioExistente = usuarioOptional.get();

        usuario.setCpf(usuarioExistente.getCpf());
        usuario.setId(id);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    @CacheEvict(value = "usuarios", key = "#id")
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
        limparCacheTodosUsuarios();
    }

    @CacheEvict(value = "usuarios", key = "'todos'")
    public void limparCacheTodosUsuarios() {}
}
