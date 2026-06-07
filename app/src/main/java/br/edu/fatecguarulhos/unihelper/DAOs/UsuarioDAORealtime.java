package br.edu.fatecguarulhos.unihelper.DAOs;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.edu.fatecguarulhos.unihelper.Models.Usuario;

public class UsuarioDAORealtime {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    public UsuarioDAORealtime(){
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.d(TAG, "connected");
                } else {
                    Log.d(TAG, "not connected");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Listener was cancelled");
            }
        });
        ref = FirebaseDatabase.getInstance().getReference("usuarios");

        database = FirebaseDatabase.getInstance();
    }

    public void registrarUsuario(Usuario u){
        String userId = ref.push().getKey();
        u.setId(userId);
        ref.child(userId).setValue(u)
                .addOnSuccessListener(a -> {
                    //Toast informando sucesso
                })
                .addOnFailureListener(ex->{
                    throw new RuntimeException(ex.getMessage());
                });
    }
}
