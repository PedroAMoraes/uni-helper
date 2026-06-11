package br.edu.fatecguarulhos.unihelper.formularios;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormularioMateria {

    private EditText edtMateria, edtQtdAvaliacoes, edtMediaMinima, edtData, edtFormula;

    private Calendar calendario = Calendar.getInstance();

    public FormularioMateria(EditText edtMateria, EditText edtQtdAvaliacoes, EditText edtMediaMinima, EditText edtData, EditText edtFormula) {
        this.edtMateria = edtMateria;
        this.edtQtdAvaliacoes = edtQtdAvaliacoes;
        this.edtMediaMinima = edtMediaMinima;
        this.edtData = edtData;
        this.edtFormula = edtFormula;
        edtFormula.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String original = edtFormula.getText().toString();
                String upper = original.toUpperCase();

                if (!original.equals(upper)) {
                    edtFormula.setText(upper);
                    edtFormula.setSelection(upper.length());
                }
            }
        });
    }

    public boolean camposValidos(){
        boolean campoVazio = false;
        campoVazio = campoVazio(edtMateria, campoVazio);
        campoVazio = campoVazio(edtQtdAvaliacoes, campoVazio);
        campoVazio = campoVazio(edtMediaMinima, campoVazio);
        campoVazio = campoVazio(edtData, campoVazio);
        campoVazio = campoVazio(edtFormula, campoVazio);
        if(!validarFormula()){
            edtFormula.setError("Formula inválida!");
            return false;
        }
        return !campoVazio;
    }
    private boolean campoVazio(EditText campo, boolean campoVazio){
        if(campo.getText().toString().equals("") || campo.getText().toString().isEmpty()){
            campo.setError("Campo obrigatório");
            campoVazio =  true;
        }
        return campoVazio;
    }
    public LocalDate getDataMateria(){
        String[] strData = edtData.getText().toString().split("/");
        int[] intData = new int[3];
        for (int i = 0; i < 3; i++)
            intData[i] = Integer.parseInt(strData[i]);
        return LocalDate.of(intData[2],intData[1],intData[0]);
    }
    public void mostrarEscolhaDateTime(EditText edtData, Context context){
        new DatePickerDialog(context, (view, ano, mes, dia) -> {
            calendario.set(Calendar.YEAR, ano);
            calendario.set(Calendar.MONTH, mes);
            calendario.set(Calendar.DAY_OF_MONTH, dia);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            edtData.setText(sdf.format(calendario.getTime()));

        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    public boolean validarFormula() {
        boolean inputValido = formatoFormulaValido();
        boolean atividadesValidas = todasAtividadesExistem(gerarListaAux());
        return ( inputValido && atividadesValidas );
    }
    private boolean formatoFormulaValido(){
        String inputFormula = edtFormula.getText().toString();
        String formula = inputFormula.replaceAll("\\s+", "");


        String regex = "^[0-9A\\(\\)\\+\\-\\*\\/]+$";
        if (!formula.matches(regex)) {
            return false;
        }


        if (formula.matches(".*A(?![0-9]).*")) {
            return false;
        }

        if (formula.matches(".*\\d+A.*")) {
            return false;
        }


        if (formula.matches(".*\\)([A\\d\\(]).*")) {
            return false;
        }

        if (formula.matches(".*[A\\d]\\(.*")) {
            return false;
        }


        int qtdParenteses = 0;
        for (char c : formula.toCharArray()) {
            if (c == '(') qtdParenteses++;
            if (c == ')') qtdParenteses--;
            if (qtdParenteses < 0) return false;
        }

        return qtdParenteses == 0;
    }
    public boolean todasAtividadesExistem(HashMap<String, Double> notasMap) {
        String inputFormula = edtFormula.getText().toString();


        HashSet<String> atividadesBuscadas = new HashSet<>(notasMap.keySet());

        Pattern pattern = Pattern.compile("A\\d+");
        Matcher matcher = pattern.matcher(inputFormula);


        while (matcher.find()) {
            String atividade = matcher.group();


            if (!notasMap.containsKey(atividade)) {
                return false;
            }


            atividadesBuscadas.remove(atividade);
        }


        return atividadesBuscadas.isEmpty();
    }


    private HashMap<String, Double> gerarListaAux(){
        HashMap<String, Double> listaAux = new HashMap<>();
        int qtdMaterias = Integer.parseInt(edtQtdAvaliacoes.getText().toString());
        for(int i = 0; i < qtdMaterias; i++)
            listaAux.put("A"+(i+1), 1.0);
        return listaAux;
    }
}
