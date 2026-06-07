package br.edu.fatecguarulhos.unihelper.Formularios;

import android.widget.EditText;

import java.util.List;

import br.edu.fatecguarulhos.unihelper.Models.Usuario;

public class FormularioCadastro {
    private EditText editNome, editEmail, editSenha, editConfirmarSenha;
    private List<EditText> campos;
    public FormularioCadastro(EditText editNome, EditText editEmail, EditText editSenha, EditText editConfirmarSenha) {
        this.editNome = editNome;
        this.editEmail = editEmail;
        this.editSenha = editSenha;
        this.editConfirmarSenha = editConfirmarSenha;
        campos = List.of(editNome, editEmail, editSenha, editConfirmarSenha);
    }
    public Boolean formularioValido(){
        boolean valido = false;
        for (int i = 0; i < campos.size(); i++)
            valido = verificarSeEstaVazio(campos.get(i));
        List<Boolean> camposValidos = List.of(validarSenha());
        return valido;
    }
    private Boolean validarSenha(){
        boolean valido = false;
        String senha = editSenha.getText().toString();
        if(senha.isBlank()) editSenha.setError("");
        else if(senha.length() < 6) editSenha.setError("Mínimo de 6 caracteres");
        else valido = true;
        return valido;
    }
    private void senhasBatem(){

    }
    private boolean verificarSeEstaVazio(EditText editText){
        if(editText.getText().toString().isBlank()){
            editText.setError("Campo não pode estar vazio");
            return false;
        }
        else return true;
    }
    public Usuario getUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNome(editNome.getText().toString());
        usuario.setEmail(editEmail.getText().toString());
        usuario.setSenha(editEmail.getText().toString());
        return usuario;
    }
}
