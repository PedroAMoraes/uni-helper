package br.edu.fatecguarulhos.unihelper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import br.edu.fatecguarulhos.unihelper.DAOs.MateriaDAO;
import br.edu.fatecguarulhos.unihelper.R;
import br.edu.fatecguarulhos.unihelper.formularios.FormularioMateria;
import br.edu.fatecguarulhos.unihelper.models.Materia;

public class ManutecaoMateria extends AppCompatActivity {

    private EditText edtMateria, edtQtdAvaliacoes, edtData, edtFormula;
    private Materia materia;
    private FormularioMateria formMateria;
    private MateriaDAO materiaDAO;
    private Button btnAlterar, btnDeletar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manutecao_materia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inicializarComponentes();
        configurarComponentes();
    }
    private void inicializarComponentes(){
        Intent it = getIntent();
        materia = new Gson().fromJson(it.getStringExtra("jsonMateria"),Materia.class);
        edtMateria = findViewById(R.id.edtMateria_manutencaoMateria);
        edtQtdAvaliacoes = findViewById(R.id.edtQtdAvaliacoes_manutencaoMateria);
        edtData = findViewById(R.id.edtData_manutencaoMateria);
        edtFormula = findViewById(R.id.edtFormula_manutencaoMateria);
        btnDeletar = findViewById(R.id.btnDeletar);
        btnAlterar = findViewById(R.id.btnAlterar);
        formMateria = new FormularioMateria(edtMateria, edtQtdAvaliacoes, edtData, edtFormula);
        materiaDAO = new MateriaDAO(this, FirebaseAuth.getInstance().getUid());
    }
    private void configurarComponentes(){
        edtMateria.setText(materia.getNome());
        edtData.setText(materia.getDataProva());
        edtFormula.setText(materia.getFormulaMedia());
        edtQtdAvaliacoes.setText(String.valueOf(materia.getQtdAvaliacoes()));
    }
    public void salvarMateria(View view){
        if(formMateria.camposValidos()){
            atualizarMateria();
            materiaDAO.atualizarMateria(materia);
        }
    }
    private void atualizarMateria(){
        materia.setNome(edtMateria.getText().toString());
        materia.setQtdAvaliacoes(Integer.valueOf(edtQtdAvaliacoes.getText().toString()));
        materia.setDataProva(edtData.getText().toString());
        materia.setFormulaMedia(edtFormula.getText().toString());
    }

    public void voltar(View view){
        finish();
    }
}