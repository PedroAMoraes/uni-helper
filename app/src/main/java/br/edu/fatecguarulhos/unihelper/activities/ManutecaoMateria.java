package br.edu.fatecguarulhos.unihelper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.HashMap;

import br.edu.fatecguarulhos.unihelper.DAOs.MateriaDAO;
import br.edu.fatecguarulhos.unihelper.R;
import br.edu.fatecguarulhos.unihelper.formularios.FormularioMateria;
import br.edu.fatecguarulhos.unihelper.models.Materia;

public class ManutecaoMateria extends AppCompatActivity {

    private EditText edtMateria, edtQtdAvaliacoes, edtData, edtFormula, edtNotaAtividade;
    private Spinner spnAtividades;
    private Materia materia;
    private HashMap<String, Integer> notas;
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
        notas = materia.getNotas();
        edtMateria = findViewById(R.id.edtMateria_manutencaoMateria);
        edtQtdAvaliacoes = findViewById(R.id.edtQtdAvaliacoes_manutencaoMateria);
        edtData = findViewById(R.id.edtData_manutencaoMateria);
        edtFormula = findViewById(R.id.edtFormula_manutencaoMateria);
        btnDeletar = findViewById(R.id.btnDeletar);
        btnAlterar = findViewById(R.id.btnAlterar);
        spnAtividades = findViewById(R.id.spnrAtividades);
        edtNotaAtividade = findViewById(R.id.edtNotaAtividade_manutencaoMateria);
        formMateria = new FormularioMateria(edtMateria, edtQtdAvaliacoes, edtData, edtFormula);
        materiaDAO = new MateriaDAO(this, FirebaseAuth.getInstance().getUid());
    }
    private void configurarComponentes(){
        edtMateria.setText(materia.getNome());
        edtData.setText(materia.getDataProva());
        edtFormula.setText(materia.getFormulaMedia());
        edtQtdAvaliacoes.setText(String.valueOf(materia.getQtdAvaliacoes()));
        edtNotaAtividade.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                //atualizarNota();
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //atualizarNota();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                atualizarNota();
            }
        });
        inicializarSpinner();
    }
    private void inicializarSpinner(){
        String[] items = new String[materia.getQtdAvaliacoes()];
        for(int i = 0; i < items.length; i++)
            items[i] = "A" + (i+1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spnAtividades.setAdapter(adapter);
        spnAtividades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                exibitNotaAtividade(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //exibitNotaAtividade("A1");
    }
    private void exibitNotaAtividade(String atividade){
        edtNotaAtividade.setText(String.valueOf(notas.get(atividade) == null ? 0 : notas.get(atividade)));
    }
    private void atualizarNota(){
        String atividade = spnAtividades.getSelectedItem().toString();
        String inputNota = edtNotaAtividade.getText().toString();
        int nota = Integer.parseInt(inputNota.isEmpty() ? "0" : inputNota);
        notas.put(atividade, nota);
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
        materia.setNotas(notas);
    }
    public void deletarMateria(View view){
        materiaDAO.deleteMateria(materia);
    }

    public void voltar(View view){
        finish();
    }
}