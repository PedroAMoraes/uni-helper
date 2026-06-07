package br.edu.fatecguarulhos.unihelper.DAOs;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import br.edu.fatecguarulhos.unihelper.Activities.CadastroActivity;
import br.edu.fatecguarulhos.unihelper.Models.Usuario;

public class UsuarioDAO {
    private CollectionReference usuariosCollection;
    private FirebaseAuth auth;
    private Context context;
    public UsuarioDAO(Context context){
        usuariosCollection = FirebaseFirestore.getInstance().collection("usuarios");
        auth = FirebaseAuth.getInstance();
        this.context = context;
    }
    public void cadastrarUsuario(Usuario usuario){
        try{
            registrarUsuarioFirebaseAuth(usuario);
            salvarUsuarioFirestore(usuario);
        } catch (Exception e){
            System.out.println("ERRO -> " +e.getStackTrace());
        }
    }
    private void registrarUsuarioFirebaseAuth(Usuario usuario){
        auth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(context.getMainExecutor(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    usuario.setId(auth.getUid());
                }else {
                    throw new RuntimeException("Usuário não pôde ser cadastrado");
                }
            }
        });
    }
    private void salvarUsuarioFirestore(Usuario usuario){
        usuariosCollection.add(usuario)
                .addOnSuccessListener(documentReference -> {

                }).addOnFailureListener( e ->{

                });
    }
}
