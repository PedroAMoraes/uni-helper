package br.edu.fatecguarulhos.unihelper.DAOs;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import br.edu.fatecguarulhos.unihelper.ia.MessageModel;

public class ChatDAO {

    private FirebaseFirestore db;
    private String uid;

    public ChatDAO() {
        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getUid();
    }

    public void salvarMensagem(MessageModel mensagem) {

        if (uid == null) return;

        db.collection("usuarios")
                .document(uid)
                .collection("historicoChat")
                .add(mensagem);
    }

    public void carregarHistorico(OnSuccessListener<QuerySnapshot> listener) {

        if (uid == null) return;

        db.collection("usuarios")
                .document(uid)
                .collection("historicoChat")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(listener);
    }
}