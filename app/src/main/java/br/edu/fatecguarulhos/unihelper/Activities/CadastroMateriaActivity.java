package br.edu.fatecguarulhos.unihelper.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Date;
import java.sql.Timestamp;

import br.edu.fatecguarulhos.unihelper.DAOs.MateriaDAO;
import br.edu.fatecguarulhos.unihelper.Formularios.FormularioMateria;
import br.edu.fatecguarulhos.unihelper.Models.Materia;
import br.edu.fatecguarulhos.unihelper.R;

public class CadastroMateriaActivity extends AppCompatActivity {

    private Button btnSalvar;
    private EditText edtMateria, edtNota, edtData, edtFormula;
    private FormularioMateria formMateria;
    private MateriaDAO materiaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_materia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciaizarComponentes();
        configurarComponentes();
    }

    private void iniciaizarComponentes() {
        btnSalvar = findViewById(R.id.btnSalvarMateria);
        edtMateria = findViewById(R.id.edtMateria);
        edtNota = findViewById(R.id.edtNota);
        edtData = findViewById(R.id.edtData);
        edtFormula = findViewById(R.id.edtFormula);
        formMateria = new FormularioMateria(edtMateria, edtNota, edtData, edtFormula);
        materiaDAO = new MateriaDAO(this);
    }
    private void configurarComponentes(){
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formMateria.mostrarEscolhaDateTime(edtData, view.getContext());
            }
        });
    }
    public void salvarMateria(View view){
        if(formMateria.camposValidos())
            materiaDAO.cadastrarMateria(criarMateria());
    }
    private Materia criarMateria(){
        Materia materia = new Materia();
        materia.setNome(edtMateria.getText().toString());
        materia.setNota(Float.valueOf(edtNota.getText().toString()));
        materia.setData(formMateria.getDataMateria());
        materia.setFormulaMedia(edtFormula.getText().toString());
        return materia;
    }


    public void voltar(View view){
        finish();
    }
}