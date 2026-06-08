package br.edu.fatecguarulhos.unihelper.formularios;

import android.widget.EditText;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.edu.fatecguarulhos.unihelper.models.Usuario;

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
        int camposVazios = 0;
        for (int i = 0; i < campos.size(); i++) {
            valido = verificarSeEstaVazio(campos.get(i));
            if(!valido) camposVazios++;
        }
        List<Boolean> camposValidos = List.of(validarSenha());
        boolean emailValido = validarEmail();
        return (senhasBatem() && camposVazios == 0 && emailValido);
    }
    private Boolean validarEmail(){
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        String email = editEmail.getText().toString();
        if (email.isBlank()) {
                return false;
            }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        boolean valido = matcher.matches();
        if(!valido) editEmail.setError("Email inválido!");
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
    private Boolean senhasBatem(){
        String senha = editSenha.getText().toString();
        String confirmarSenha = editConfirmarSenha.getText().toString();
        if(senha.isBlank() || confirmarSenha.isBlank())
            return false;
        if(senha.toString().equals(confirmarSenha.toString())) return true;
        else {
            editConfirmarSenha.setError("A senha deve ser igual nos dois campos");
            return false;
        }
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
        usuario.setSenha(editSenha.getText().toString());
        return usuario;
    }
}
