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

import br.edu.fatecguarulhos.unihelper.DAOs.UsuarioDAO;
import br.edu.fatecguarulhos.unihelper.R;

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
    }
    private void inicializarComponentes(){
        text_nome_perfil = findViewById(R.id.text_nome_perfil);
        text_email_perfil=findViewById(R.id.text_email_perfil);
        text_nome_perfil.setText("teste");



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