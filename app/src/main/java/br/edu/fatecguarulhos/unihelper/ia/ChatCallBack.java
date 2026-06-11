package br.edu.fatecguarulhos.unihelper.ia;

import com.google.firebase.firestore.QuerySnapshot;
public interface ChatCallBack {


    public interface ChatCallback {
        void onSucesso(QuerySnapshot queryDocumentSnapshots);
    }
}
