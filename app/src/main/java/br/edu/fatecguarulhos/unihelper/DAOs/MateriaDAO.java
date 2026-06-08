package br.edu.fatecguarulhos.unihelper.DAOs;

import android.content.Context;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.edu.fatecguarulhos.unihelper.models.Materia;

public class MateriaDAO {

    private CollectionReference materiaColletion;
    private Context context;

    public MateriaDAO(Context context){
        materiaColletion = FirebaseFirestore.getInstance().collection("materia");
        this.context = context;
    }

    public void cadastrarMateria(Materia materia){
        registrarMateriaFirebaseAuth(materia);
        salvarMateriaFirestore(materia);
    }

    private void registrarMateriaFirebaseAuth(Materia materia){
    }
    private void salvarMateriaFirestore(Materia materia){
        /*
        materiaColletion.add(materia)
                .addOnSuccessListener(documentReference -> {
                }).addOnFailureListener( e ->{System.out.println("Erro -> " + e.getStackTrace());});
         */
        materiaColletion.document(materia.getNome()).set(materia)
                .addOnSuccessListener(documentReference -> {
                }).addOnFailureListener(
                        e ->{System.out.println("Erro -> " + e.getStackTrace());}
                );
    }
}
