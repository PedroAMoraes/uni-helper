package br.edu.fatecguarulhos.unihelper.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import br.edu.fatecguarulhos.unihelper.R;
import br.edu.fatecguarulhos.unihelper.ia.ChatAdapter;
import br.edu.fatecguarulhos.unihelper.ia.GeminiCallBack;
import br.edu.fatecguarulhos.unihelper.ia.GeminiService;
import br.edu.fatecguarulhos.unihelper.ia.MessageModel;

public class ChatActivities extends AppCompatActivity {

    private GeminiService geminiService;
    private Button btnEnviar;
    private EditText edtPergunta;

    private RecyclerView recyclerMensagens;
    private ChatAdapter chatAdapter;
    private ArrayList<MessageModel> mensagens;
   // private TextView  txtResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_activities);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnEnviar = findViewById(R.id.btnEnviar);
        edtPergunta = findViewById(R.id.edtPergunta);
        //txtResposta = findViewById(R.id.txtResposta);

        recyclerMensagens = findViewById(R.id.recyclerMensagens);

        mensagens = new ArrayList<>();
        chatAdapter = new ChatAdapter(mensagens);

        recyclerMensagens.setLayoutManager(new LinearLayoutManager(this));
        recyclerMensagens.setAdapter(chatAdapter);

        geminiService = new GeminiService();
        btnEnviar.setOnClickListener(v -> enviar(v));
    }

   public void enviar(View view) {

       String pergunta = edtPergunta.getText().toString().trim();

       if (pergunta.isEmpty()) {
           return;
       }

       mensagens.add(new MessageModel(pergunta, true));
       chatAdapter.notifyItemInserted(mensagens.size() - 1);
       recyclerMensagens.scrollToPosition(mensagens.size() - 1);

       edtPergunta.setText("");

       geminiService.enviarPergunta(pergunta, new GeminiCallBack() {
           @Override
           public void onResposta(String resposta) {

               runOnUiThread(() -> {
                   mensagens.add(new MessageModel(resposta, false));
                   chatAdapter.notifyItemInserted(mensagens.size() - 1);
                   recyclerMensagens.scrollToPosition(mensagens.size() - 1);
               });
           }

           @Override
           public void onErro(String erro) {

               runOnUiThread(() -> {
                   mensagens.add(new MessageModel("Erro: " + erro, false));
                   chatAdapter.notifyItemInserted(mensagens.size() - 1);
                   recyclerMensagens.scrollToPosition(mensagens.size() - 1);
               });
           }
       });
   }

}