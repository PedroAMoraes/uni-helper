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
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.HashMap;

import br.edu.fatecguarulhos.unihelper.DAOs.MateriaDAO;
import br.edu.fatecguarulhos.unihelper.DAOs.UsuarioDAO;
import br.edu.fatecguarulhos.unihelper.R;
import br.edu.fatecguarulhos.unihelper.formularios.FormularioMateria;
import br.edu.fatecguarulhos.unihelper.models.Materia;
import br.edu.fatecguarulhos.unihelper.models.Usuario;

public class ManutecaoMateria extends AppCompatActivity {

    private EditText edtMateria, edtQtdAvaliacoes, edtMediaMinima, edtData, edtFormula, edtNotaAtividade;
    private TextView txtMedia;
    private Spinner spnAtividades;
    private Materia materia;
    private HashMap<String, Double> notas;
    private FormularioMateria formMateria;
    private MateriaDAO materiaDAO;
    private Button btnAlterar, btnDeletar;
    private String idMateria;


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
        edtMediaMinima = findViewById(R.id.edtMediaMinima_manutencaoMateria);
        edtData = findViewById(R.id.edtData_manutencaoMateria);
        edtFormula = findViewById(R.id.edtFormula_manutencaoMateria);
        btnDeletar = findViewById(R.id.btnDeletar);
        btnAlterar = findViewById(R.id.btnAlterar);
        spnAtividades = findViewById(R.id.spnrAtividades);
        edtNotaAtividade = findViewById(R.id.edtNotaAtividade_manutencaoMateria);
        txtMedia = findViewById(R.id.txtMedia_manutencaoMateria);
        formMateria = new FormularioMateria(edtMateria, edtQtdAvaliacoes, edtMediaMinima, edtData, edtFormula);
        materiaDAO = new MateriaDAO(this, FirebaseAuth.getInstance().getUid());
    }
    private void configurarComponentes(){
        edtMateria.setText(materia.getNome());
        edtData.setText(materia.getDataProva());
        edtMediaMinima.setText(String.valueOf(materia.getMediaMinima()));
        edtFormula.setText(materia.getFormulaMedia());
        edtQtdAvaliacoes.setText(String.valueOf(materia.getQtdAvaliacoes()));
        definirOnTextChanged();
        inicializarSpinner();
        atualizarMedia();
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formMateria.mostrarEscolhaDateTime(edtData, view.getContext());
            }
        });
    }
    private void inicializarSpinner(){
        atualizarTamanhoSpinner(materia.getQtdAvaliacoes());
        spnAtividades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                exibitNotaAtividade(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void exibitNotaAtividade(String atividade){
        edtNotaAtividade.setText(String.valueOf(notas.get(atividade) == null ? 0 : notas.get(atividade)));
    }
    private void atualizarNota(){
        String atividade = spnAtividades.getSelectedItem().toString();
        String inputNota = edtNotaAtividade.getText().toString();
        Double nota = inputNota.isEmpty() ? 0.0 : Double.parseDouble(inputNota);
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
        if(materia.getQtdAvaliacoes() > Integer.valueOf(edtQtdAvaliacoes.getText().toString())){
            deletarNotasAtividadesEliminadas(materia.getQtdAvaliacoes(), Integer.valueOf(edtQtdAvaliacoes.getText().toString()));

        }
        materia.setQtdAvaliacoes(Integer.parseInt(edtQtdAvaliacoes.getText().toString()));
        materia.setMediaMinima(Double.parseDouble(edtMediaMinima.getText().toString()));
        materia.setDataProva(edtData.getText().toString());
        materia.setFormulaMedia(edtFormula.getText().toString());
        materia.setNotas(notas);
        atualizarMedia();
    }
    private void atualizarMedia(){
        String strMedia = "Media final: ";
        double media = materia.calcularNotaFinal();
        strMedia += String.format("%.2f", media);
        txtMedia.setText(strMedia);
    }
    private void deletarNotasAtividadesEliminadas(int qtdAvaliacoes, Integer notaQtdAvaliacoes) {
        for(int i = notaQtdAvaliacoes; i < qtdAvaliacoes; i ++){
            if(notas.get(("A"+i)) != null) notas.remove("A"+i);
        }
    }
    private void atualizarTamanhoSpinner(int tamanhoAtual){
        String[] items = new String[tamanhoAtual];
        for(int i = 0; i < items.length; i++)
            items[i] = "A" + (i+1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spnAtividades.setAdapter(adapter);
    }

    public void deletarMateria(View view){
        materiaDAO.deleteMateria(materia);
        finish();
    }

    public void voltar(View view){
        finish();
    }
    private void definirOnTextChanged() {
        edtQtdAvaliacoes.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int valorEdtNota = Integer.parseInt((
                        edtQtdAvaliacoes.getText().toString().isEmpty() ?
                                "1":
                                edtQtdAvaliacoes.getText().toString()
                ));
                atualizarTamanhoSpinner(valorEdtNota);
            }
        });
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
    }
}