package br.edu.fatecguarulhos.unihelper.interfaces;

import java.util.List;

import br.edu.fatecguarulhos.unihelper.models.Materia;

public interface FirebaseCallback {
    void onCallbackForAll(List<Materia> lista);
    void onCallBackByid(Materia e);
}
