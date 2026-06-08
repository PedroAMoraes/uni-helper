package br.edu.fatecguarulhos.unihelper.cards;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import br.edu.fatecguarulhos.unihelper.R;

public class CardMateria extends ConstraintLayout {
    public CardMateria(Context context){
        super(context);
        inicializarComponentes(context);
    }
    public CardMateria(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
        inicializarComponentes(context);
    }
    private void inicializarComponentes(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_materia, this, true);
    }
}
