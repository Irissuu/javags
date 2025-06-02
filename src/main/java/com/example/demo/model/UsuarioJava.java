package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario_Java")
public class UsuarioJava implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @CPF
    private String cpf;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String senha;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UsuarioJava() {}

    public UsuarioJava(String email, String senha, UserRole role) {
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public UsuarioJava(Long id, String nome, String cpf, String email, String senha, UserRole role) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public UsuarioJava(String nome, String cpf, String email, String encryptedPwd, UserRole role) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = encryptedPwd;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @ManyToMany
    @JoinTable(
            name = "usuario_regiao",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "regiao_id")
    )
    private List<RegiaoJava> regioes = new ArrayList<>();


    @Override public String getUsername() { return this.email; }
    @Override public String getPassword() { return this.senha; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}
