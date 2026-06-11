package br.edu.fatecguarulhos.unihelper.ia;

public class MessageModel {

    private String texto;
    private boolean usuario;
    private long timestamp;

    public MessageModel() {
    }

    public MessageModel(String texto, boolean usuario) {
        this.texto = texto;
        this.usuario = usuario;
        this.timestamp = System.currentTimeMillis();
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isUsuario() {
        return usuario;
    }

    public void setUsuario(boolean usuario) {
        this.usuario = usuario;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}