package br.edu.fatecguarulhos.unihelper.models;

import android.util.Log;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Materia {

    private String nome, formulaMedia, id, dataProva, dataOrdenar;
    private int qtdAvaliacoes;
    private HashMap<String, Double> notas = new HashMap<>();
    private double mediaMinima;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataProva() {
        return dataProva;
    }

    public void setDataProva(String dataProva) {
        String[] strData = dataProva.split("/");
        String dataInicio = strData[2];
        dataInicio += strData[1];
        dataInicio += strData[0];
        dataOrdenar = dataInicio;
        this.dataProva = dataProva;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public double getMediaMinima() {
        return mediaMinima;
    }

    public void setMediaMinima(double mediaMinima) {
        this.mediaMinima = mediaMinima;
    }

    public String getFormulaMedia() { return formulaMedia; }

    public void setFormulaMedia(String formulaMedia) { this.formulaMedia = formulaMedia; }

    public int getQtdAvaliacoes() { return qtdAvaliacoes; }

    public void setQtdAvaliacoes(int qtdAvaliacoes) { this.qtdAvaliacoes = qtdAvaliacoes; }

    public HashMap<String, Double> getNotas() {
        return notas;
    }

    public void setNotas(HashMap<String, Double> notas) {
        this.notas = notas;
    }

    public String getDataOrdenar() {
        return dataOrdenar;
    }

    public void setDataOrdenar(String dataOrdenar) {
        this.dataOrdenar = dataOrdenar;
    }

    public double calcularNotaFinal() {
        preencherNotasAusentes();

        try {
            String formula = formulaMedia;
            ExpressionBuilder builder = new ExpressionBuilder(formula);

            for (String chaveAtividade : notas.keySet()) {
                builder.variable(chaveAtividade);
            }

            Expression expressao = builder.build();

            for (Map.Entry<String, Double> entrada : notas.entrySet()) {
                double valorNota = (entrada.getValue() != null) ? entrada.getValue() : 0.0;
                expressao.setVariable(entrada.getKey(), valorNota);
            }

            return expressao.evaluate();

        } catch (IllegalArgumentException e) {
            Log.e("ErroCalculo", "Fórmula inválida ou variável faltando: " + e.getMessage(), e);
            return 0.0;
        } catch (ArithmeticException e) {
            Log.e("ErroCalculo", "Erro de divisão por zero na fórmula: " + e.getMessage(), e);
            return 0.0;
        } catch (Exception e) {
            Log.e("ErroCalculo", "Erro inesperado ao calcular: " + e.getMessage(), e);
            return 0.0;
        }
    }
    private void preencherNotasAusentes() {
        if (this.notas == null) {
            this.notas = new HashMap<>();
        }

        for (int i = 1; i <= qtdAvaliacoes; i++) {
            String chave = "A" + i;

            if (!this.notas.containsKey(chave) || this.notas.get(chave) == null) {
                this.notas.put(chave, 0.0);
            }
        }
    }

}
