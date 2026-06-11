package br.edu.fatecguarulhos.unihelper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import br.edu.fatecguarulhos.unihelper.DAOs.UsuarioDAO;
import br.edu.fatecguarulhos.unihelper.R;
import br.edu.fatecguarulhos.unihelper.models.Usuario;

public class PerfilActivity extends AppCompatActivity {

    private Intent it;
    private TextView text_nome_perfil,text_email_perfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inicializarComponentes();
        carregarDadosUsuario();
    }
    private void inicializarComponentes(){
        text_nome_perfil = findViewById(R.id.text_nome_perfil);
        text_email_perfil=findViewById(R.id.text_email_perfil);
    }

    private void carregarDadosUsuario() {
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioAtual == null) {
            text_nome_perfil.setText("Usuário não logado");
            text_email_perfil.setText("");
            return;
        }

        String uid = usuarioAtual.getUid();

        FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Usuario usuario = documentSnapshot.toObject(Usuario.class);

                        if (usuario != null) {
                            text_nome_perfil.setText(usuario.getNome());
                            text_email_perfil.setText(usuario.getEmail());
                        }
                    } else {
                        text_nome_perfil.setText("Nome não encontrado");
                        text_email_perfil.setText(usuarioAtual.getEmail());
                    }
                })
                .addOnFailureListener(e -> {
                    text_nome_perfil.setText("Erro ao carregar nome");
                    text_email_perfil.setText(usuarioAtual.getEmail());
                });
    }
    public void voltar(View view){
        finish();
    }

    public void encerrarSessao(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}