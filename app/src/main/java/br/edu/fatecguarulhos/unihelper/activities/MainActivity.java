package br.edu.fatecguarulhos.unihelper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import br.edu.fatecguarulhos.unihelper.DAOs.MateriaDAO;
import br.edu.fatecguarulhos.unihelper.R;
import br.edu.fatecguarulhos.unihelper.adapters.MateriaAdapter;
import br.edu.fatecguarulhos.unihelper.interfaces.FirebaseCallback;
import br.edu.fatecguarulhos.unihelper.models.Materia;

public class MainActivity extends AppCompatActivity {

    private Intent it;
    private RecyclerView rvMaterias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rvMaterias = findViewById(R.id.rvListaMaterias);
        exibirMaterias();
    }

    private void exibirMaterias() {
        MateriaDAO materiaDAO = new MateriaDAO(this, FirebaseAuth.getInstance().getUid());
        materiaDAO.getMaterias(new FirebaseCallback() {
            @Override
            public void onCallbackForAll(HashMap<String, Materia> map) {
                gerarRecyclerViewMaterias(map);
            }

            @Override
            public void onCallBackByid(Materia materia) {

            }
        });
    }
    private void gerarRecyclerViewMaterias(HashMap<String, Materia> map){
        rvMaterias.setLayoutManager(new LinearLayoutManager(this));
        MateriaAdapter adapter = new MateriaAdapter(this, map);
        rvMaterias.setAdapter(adapter);
    }

    public void cadastrarMaterias(View view){
        it = new Intent(getApplicationContext(), CadastroMateriaActivity.class);
        startActivity(it);
    }

    public void perfil(View view){
        it = new Intent(getApplicationContext(), PerfilActivity.class);
        startActivity(it);
    }
    public void perfil(){
        it = new Intent(getApplicationContext(), PerfilActivity.class);
        startActivity(it);
    }
    public void cadastrarMaterias(){
        it = new Intent(getApplicationContext(), CadastroMateriaActivity.class);
        startActivity(it);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_perfil) perfil();
        if(item.getItemId() == R.id.item_novaMateria) cadastrarMaterias();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        exibirMaterias();
    }
}