package br.edu.fatecguarulhos.unihelper.DAOs;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.fatecguarulhos.unihelper.interfaces.FirebaseCallback;
import br.edu.fatecguarulhos.unihelper.models.Materia;
import br.edu.fatecguarulhos.unihelper.models.Usuario;

public class MateriaDAO {

    private CollectionReference materiaColletion;
    private Context context;
    private String uidAluno;


    public MateriaDAO(Context context, String uidAluno){
        materiaColletion = FirebaseFirestore.getInstance().collection("usuarios");
        this.context = context;
        this.uidAluno = uidAluno;
    }

    public void cadastrarMateria(Materia materia){
        salvarMateriaFirestore(materia);
    }
    public void atualizarMateria(Materia materia){
        salvarMateriaFirestore(materia);
    }
    private void salvarMateriaFirestore(Materia materia){
        materiaColletion.document(uidAluno)
                .update(("materias." + materia.getId()), materia)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Matéria salva!", Toast.LENGTH_SHORT).show();
                });
    }
    public void getMaterias(FirebaseCallback callback){

        DocumentReference docRef = materiaColletion.document(uidAluno);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Usuario aluno = document.toObject(Usuario.class);

                        if (aluno != null && aluno.getMaterias() != null) {
                            HashMap<String,Materia> materiaList = aluno.getMaterias();
                            Map<String, Materia> people = aluno.getMaterias();
                            // not yet sorted
                            List<Materia> peopleByAge = new ArrayList<>(people.values());

                            Collections.sort(peopleByAge, Comparator.comparing(Materia::getDataOrdenar));


                            callback.onCallbackForAll(peopleByAge);
                        }
                    } else {
                        Log.d("FirestoreData", "No such document exists");
                    }
                } else {
                    Log.d("FirestoreData", "Fetch failed with: ", task.getException());
                }
            }
        });


    }
    public void deleteMateria(Materia materia){
        materiaColletion.document(uidAluno)
                .update(("materias." + materia.getId()), FieldValue.delete())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Matéria excluida!", Toast.LENGTH_SHORT).show();
                });
    }
}
