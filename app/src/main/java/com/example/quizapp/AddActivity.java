package com.example.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.quizapp.data.States;

public class AddActivity extends AppCompatActivity {

    public static final String EXTRA_DATA_STATES = "extra_data_states";
    public static final String EXTRA_DATA_CAPITAL = "extra_data_capital";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private EditText editState, editCapital;

    private long id;
    private AddViewModel addViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        final Bundle extras = getIntent().getExtras();
        addViewModel = new ViewModelProvider(this).get(AddViewModel.class);

        editState = findViewById(R.id.editState);
        editCapital = findViewById(R.id.editCapital);
        Button submitButton = findViewById(R.id.submit);

        if (extras != null) {
            String state = extras.getString(EXTRA_DATA_STATES, "");
            String capital = extras.getString(EXTRA_DATA_CAPITAL, "");
            Long state_id = extras.getLong(EXTRA_DATA_ID, 0L);

            if (!state.isEmpty()) {
                editState.setText(state);
            }

            if (!capital.isEmpty()) {
                editCapital.setText(capital);
            }

            submitButton.setText("UPDATE");
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String State = editState.getText().toString();
                String Capital = editCapital.getText().toString();
                if (!State.isEmpty() && !Capital.isEmpty()) {
                    if (extras != null) {
                        long id = extras.getLong(EXTRA_DATA_ID);
                        States states = new States(id, State, Capital);
                        addViewModel.update(states);
                    } else {
                        States states = new States(id, State, Capital);
                        addViewModel.insert(states);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Add title and content", Toast.LENGTH_SHORT).show();
                }

                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
