package br.edu.fatecguarulhos.unihelper.Models;

import java.time.LocalDate;
import java.util.Date;

public class Materia {

    private String nome, formulaMedia;
    private float nota;
    private LocalDate dataProva;

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getFormulaMedia() { return formulaMedia; }

    public void setFormulaMedia(String formulaMedia) { this.formulaMedia = formulaMedia; }

    public float getNota() { return nota; }

    public void setNota(float nota) { this.nota = nota; }

    public LocalDate getData() { return dataProva; }

    public void setData(LocalDate data) { this.dataProva = data; }

}
