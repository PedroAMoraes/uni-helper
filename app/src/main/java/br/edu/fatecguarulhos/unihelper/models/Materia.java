package br.edu.fatecguarulhos.unihelper.models;

import java.time.LocalDate;

public class Materia {

    private String nome, formulaMedia;
    private int qtdAvaliacoes;
    private LocalDate dataProva;

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getFormulaMedia() { return formulaMedia; }

    public void setFormulaMedia(String formulaMedia) { this.formulaMedia = formulaMedia; }

    public int getQtdAvaliacoes() { return qtdAvaliacoes; }

    public void setQtdAvaliacoes(int qtdAvaliacoes) { this.qtdAvaliacoes = qtdAvaliacoes; }

    public LocalDate getData() { return dataProva; }

    public void setData(LocalDate data) { this.dataProva = data; }

}
