package br.edu.fatecguarulhos.unihelper.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import br.edu.fatecguarulhos.unihelper.DAOs.ChatDAO;
import br.edu.fatecguarulhos.unihelper.R;
import br.edu.fatecguarulhos.unihelper.ia.ChatAdapter;
import br.edu.fatecguarulhos.unihelper.ia.GeminiCallBack;
import br.edu.fatecguarulhos.unihelper.ia.GeminiService;
import br.edu.fatecguarulhos.unihelper.ia.MessageModel;

public class ChatActivities extends AppCompatActivity {

    private GeminiService geminiService;
    private ChatDAO chatDAO;

    private Button btnEnviar;
    private EditText edtPergunta;

    private RecyclerView recyclerMensagens;
    private ChatAdapter chatAdapter;
    private ArrayList<MessageModel> mensagens;

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
        recyclerMensagens = findViewById(R.id.recyclerMensagens);

        mensagens = new ArrayList<>();
        chatAdapter = new ChatAdapter(mensagens);

        recyclerMensagens.setLayoutManager(new LinearLayoutManager(this));
        recyclerMensagens.setAdapter(chatAdapter);

        geminiService = new GeminiService();
        chatDAO = new ChatDAO();

        carregarHistorico();

        btnEnviar.setOnClickListener(v -> enviar(v));
    }

    private void carregarHistorico() {
        chatDAO.carregarHistorico(queryDocumentSnapshots -> {
            mensagens.clear();

            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                MessageModel mensagem = doc.toObject(MessageModel.class);

                if (mensagem != null) {
                    mensagens.add(mensagem);
                }
            }

            chatAdapter.notifyDataSetChanged();

            if (!mensagens.isEmpty()) {
                recyclerMensagens.scrollToPosition(mensagens.size() - 1);
            }
        });
    }

    private String montarContextoDaConversa() {
        StringBuilder contexto = new StringBuilder();

        contexto.append("Você é o assistente do aplicativo UniHelper.\n");
        contexto.append("Use o histórico abaixo para responder mantendo o contexto da conversa.\n\n");

        for (MessageModel msg : mensagens) {
            if (msg.isUsuario()) {
                contexto.append("Usuário: ");
            } else {
                contexto.append("Assistente: ");
            }

            contexto.append(msg.getTexto());
            contexto.append("\n");
        }

        contexto.append("\nResponda somente a última mensagem do usuário.");

        return contexto.toString();
    }

    public void enviar(View view) {
        String pergunta = edtPergunta.getText().toString().trim();

        if (pergunta.isEmpty()) {
            return;
        }

        MessageModel mensagemUsuario = new MessageModel(pergunta, true);

        mensagens.add(mensagemUsuario);
        chatAdapter.notifyItemInserted(mensagens.size() - 1);
        recyclerMensagens.scrollToPosition(mensagens.size() - 1);

        chatDAO.salvarMensagem(mensagemUsuario);

        edtPergunta.setText("");

        String contextoCompleto = montarContextoDaConversa();

        geminiService.enviarPergunta(contextoCompleto, new GeminiCallBack() {
            @Override
            public void onResposta(String resposta) {
                runOnUiThread(() -> {
                    MessageModel mensagemIA = new MessageModel(resposta, false);

                    mensagens.add(mensagemIA);
                    chatAdapter.notifyItemInserted(mensagens.size() - 1);
                    recyclerMensagens.scrollToPosition(mensagens.size() - 1);

                    chatDAO.salvarMensagem(mensagemIA);
                });
            }

            @Override
            public void onErro(String erro) {
                runOnUiThread(() -> {
                    MessageModel mensagemErro = new MessageModel("Erro: " + erro, false);

                    mensagens.add(mensagemErro);
                    chatAdapter.notifyItemInserted(mensagens.size() - 1);
                    recyclerMensagens.scrollToPosition(mensagens.size() - 1);

                    chatDAO.salvarMensagem(mensagemErro);
                });
            }
        });
    }
}