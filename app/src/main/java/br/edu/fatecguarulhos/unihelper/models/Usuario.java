package br.edu.fatecguarulhos.unihelper.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private HashMap<String, Materia> materias;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        validarSenha(senha);
        this.senha = senha;
    }
    private void validarSenha(String senha){
        if(senha.isBlank()) throw new RuntimeException("Senha não pode estar vazia!");
    }

    public HashMap<String, Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(HashMap<String, Materia> materias) {
        this.materias = materias;
    }
}
