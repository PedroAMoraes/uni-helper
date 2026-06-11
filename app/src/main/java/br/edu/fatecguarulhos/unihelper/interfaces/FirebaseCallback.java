package br.edu.fatecguarulhos.unihelper.interfaces;

import java.util.HashMap;
import java.util.List;

import br.edu.fatecguarulhos.unihelper.models.Materia;

public interface FirebaseCallback {
    void onCallbackForAll(HashMap<String, Materia> map);
    void onCallBackByid(Materia materia);
}
