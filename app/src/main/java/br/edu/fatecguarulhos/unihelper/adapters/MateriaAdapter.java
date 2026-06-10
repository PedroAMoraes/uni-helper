package br.edu.fatecguarulhos.unihelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import br.edu.fatecguarulhos.unihelper.R;
import br.edu.fatecguarulhos.unihelper.models.Materia;

public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.MateriaHolder> {

    private Context context;
    private ArrayList<Materia> materias;

    public MateriaAdapter(Context context, HashMap<String, Materia> materias) {
        this.context = context;
        this.materias = new ArrayList<>(materias.values());
    }

    @NonNull
    @Override
    public MateriaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_materia, parent, false);
        return new MateriaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriaHolder holder, int position) {
        Materia materia = materias.get(position);
        holder.setDetails(materia);
    }

    @Override
    public int getItemCount() {
        return materias.size();
    }
    class MateriaHolder extends RecyclerView.ViewHolder{
        private TextView txtNome, txtNota, txtData;
        MateriaHolder(View itemView){
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNomeMateria_cardMateria);
            txtNota = itemView.findViewById(R.id.txtNota_cardMateria);
            txtData = itemView.findViewById(R.id.txtDataProva_cardMateria);
        }
        void setDetails(Materia materia){
            txtNome.setText("Materia: " + materia.getNome());
            txtNota.setText("Nota: WIP" );
            txtData.setText("Data: " + materia.getDataProva());
        }
    }
}
