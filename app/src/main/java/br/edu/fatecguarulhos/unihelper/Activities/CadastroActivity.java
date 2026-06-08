package br.edu.fatecguarulhos.unihelper.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import br.edu.fatecguarulhos.unihelper.DAOs.UsuarioDAO;
import br.edu.fatecguarulhos.unihelper.DAOs.UsuarioDAORealtime;
import br.edu.fatecguarulhos.unihelper.Formularios.FormularioCadastro;
import br.edu.fatecguarulhos.unihelper.Models.Usuario;
import br.edu.fatecguarulhos.unihelper.R;

public class CadastroActivity extends AppCompatActivity {
    private EditText editNome, editEmail, editSenha, editConfirmarSenha;
    private FirebaseAuth auth;
    private UsuarioDAO usuarioDAO;
    private FormularioCadastro formulario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inicializarComponentes();
    }
    private void inicializarComponentes(){
        editNome = findViewById(R.id.edit_nome_cadastro);
        editEmail = findViewById(R.id.edit_email_cadastro);
        editSenha = findViewById(R.id.edit_senha_cadastro);
        editConfirmarSenha = findViewById(R.id.edit_confirmarSenha_cadastro);
        auth = FirebaseAuth.getInstance();
        formulario = new FormularioCadastro(editNome, editEmail, editSenha, editConfirmarSenha);
    }
    public void cadastrarUsuario(View view){
        if(formulario.formularioValido()) {
            try{
                usuarioDAO = new UsuarioDAO(this);
                Usuario u = criarUsuario();
                usuarioDAO.cadastrarUsuario(u);
            } catch (Exception e){
                System.out.println(e.getStackTrace());
            }
        }
    }
    private Usuario criarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNome(editNome.getText().toString());
        usuario.setEmail(editEmail.getText().toString());
        usuario.setSenha(String.valueOf(editNome.getText().toString().hashCode()));
        return usuario;
    }
}