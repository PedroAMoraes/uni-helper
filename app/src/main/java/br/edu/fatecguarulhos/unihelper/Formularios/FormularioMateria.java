package br.edu.fatecguarulhos.unihelper.Formularios;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class FormularioMateria {

    private EditText edtMateria, edtNota, edtData, edtFormula;

    private Calendar calendario = Calendar.getInstance();

    public FormularioMateria(EditText edtMateria, EditText edtNota, EditText edtData, EditText edtFormula) {
        this.edtMateria = edtMateria;
        this.edtNota = edtNota;
        this.edtData = edtData;
        this.edtFormula = edtFormula;
    }

    public boolean camposValidos(){
        boolean campoVazio = false;
        campoVazio = campoVazio(edtMateria, campoVazio);
        campoVazio = campoVazio(edtNota, campoVazio);
        campoVazio = campoVazio(edtData, campoVazio);
        campoVazio = campoVazio(edtFormula, campoVazio);
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
}
