package br.edu.fatecguarulhos.unihelper.ia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.edu.fatecguarulhos.unihelper.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private ArrayList<MessageModel> mensagens;

    public ChatAdapter(ArrayList<MessageModel> mensagens) {
        this.mensagens = mensagens;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMensagem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMensagem = itemView.findViewById(R.id.txtMensagem);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(
                        R.layout.item_mensagem,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        MessageModel mensagem =
                mensagens.get(position);

        holder.txtMensagem.setText(
                mensagem.getTexto()
        );
    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }
}