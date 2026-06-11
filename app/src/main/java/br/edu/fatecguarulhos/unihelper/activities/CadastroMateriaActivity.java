package br.edu.fatecguarulhos.unihelper.activities;

import static android.icu.text.DisplayOptions.DisplayLength.LENGTH_SHORT;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

import br.edu.fatecguarulhos.unihelper.DAOs.MateriaDAO;
import br.edu.fatecguarulhos.unihelper.formularios.FormularioMateria;
import br.edu.fatecguarulhos.unihelper.models.Materia;
import br.edu.fatecguarulhos.unihelper.R;

public class CadastroMateriaActivity extends AppCompatActivity {

    private Button btnSalvar;
    private EditText edtMateria, edtQtdAvaliacoes, edtMediaMinima, edtData, edtFormula;
    private FormularioMateria formMateria;
    private MateriaDAO materiaDAO;
    private String idMateria;

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
    public void generateId(){
        UUID uniqueKey = UUID.randomUUID();
        idMateria = uniqueKey.toString();
    }

    private void iniciaizarComponentes() {
        generateId();
        btnSalvar = findViewById(R.id.btnSalvarMateria);
        edtMateria = findViewById(R.id.edtMateria);
        edtQtdAvaliacoes = findViewById(R.id.edtQtdAvaliacoes);
        edtData = findViewById(R.id.edtData);
        edtFormula = findViewById(R.id.edtFormula);
        edtMediaMinima = findViewById(R.id.edtMediaMinima_cadastroMateria);
        formMateria = new FormularioMateria(edtMateria, edtQtdAvaliacoes, edtMediaMinima, edtData, edtFormula);
        materiaDAO = new MateriaDAO(this, FirebaseAuth.getInstance().getUid());
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
        Toast.makeText(CadastroMateriaActivity.this,"Materia cadastrada",Toast.LENGTH_SHORT).show();

    }
    private Materia criarMateria(){
        Materia materia = new Materia();
        materia.setId(idMateria);
        materia.setNome(edtMateria.getText().toString());
        materia.setQtdAvaliacoes(Integer.parseInt(edtQtdAvaliacoes.getText().toString()));
        materia.setMediaMinima(Double.parseDouble(edtMediaMinima.getText().toString()));
        materia.setDataProva(edtData.getText().toString());
        materia.setFormulaMedia(edtFormula.getText().toString());
        return materia;
    }


    public void voltar(View view){
        finish();
    }
}