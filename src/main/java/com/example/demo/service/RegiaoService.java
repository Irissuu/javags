package com.example.demo.service;


import com.example.demo.model.RegiaoJava;
import com.example.demo.repository.RegiaoRepository;
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
public class RegiaoService {

    private final RegiaoRepository regiaoRepository;

    @Autowired
    public RegiaoService(RegiaoRepository regiaoRepository) {
        this.regiaoRepository = regiaoRepository;
    }

    @Transactional
    @CachePut(value = "regioes", key = "#result.id")
    public RegiaoJava createRegiao(RegiaoJava regiao) {
        return regiaoRepository.save(regiao);
    }

    @Cacheable(value = "regioes", key = "#id")
    public RegiaoJava readRegiaoById(Long id) {
        return regiaoRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "regioes", key = "'todos'")
    public Page<RegiaoJava> readRegioes(Pageable pageable) {
        return regiaoRepository.findAll(pageable);
    }

    @Transactional
    @CachePut(value = "regioes", key = "#result.id")
    public RegiaoJava updateRegiao(Long id, RegiaoJava regiao) {
        Optional<RegiaoJava> regiaoOptional = regiaoRepository.findById(id);
        if (regiaoOptional.isEmpty()) {
            return null;
        }

        regiao.setId(id);
        return regiaoRepository.save(regiao);
    }

    @Transactional
    @CacheEvict(value = "regioes", key = "#id")
    public void deleteRegiao(Long id) {
        regiaoRepository.deleteById(id);
        limparCacheTodasRegioes();
    }

    @CacheEvict(value = "regioes", key = "'todos'")
    public void limparCacheTodasRegioes() {}
}
