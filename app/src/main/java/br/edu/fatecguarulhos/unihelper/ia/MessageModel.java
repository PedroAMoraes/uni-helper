package br.edu.fatecguarulhos.unihelper.ia;

public class MessageModel {
    private String texto;
    private boolean usuario;


    public MessageModel(String texto, boolean usuario){
        this.texto=texto;
        this.usuario=usuario;
    }

    public String getTexto(){
        return texto;
    }
    public boolean isUsuario(){
        return usuario;
    }
}
