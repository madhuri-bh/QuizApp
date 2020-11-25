package com.example.quizapp.List;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.data.States;

public class StatesViewHolder extends RecyclerView.ViewHolder {

    private TextView state;
    private TextView capital;
    public StatesViewHolder(@NonNull View itemView) {
        super(itemView);
        state = itemView.findViewById(R.id.states);
        capital = itemView.findViewById(R.id.capital);
    }

public void bind(States states) {
state.setText(states.getState());
capital.setText(states.getCapital());
}
}
