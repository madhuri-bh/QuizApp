package com.example.quizapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quizapp.Custom.QuizView;
import com.example.quizapp.Custom.QuizViewModel;
import com.example.quizapp.Settings.SettingsActivity;
import com.example.quizapp.data.States;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private QuizView quizView;
    private QuizViewModel quizViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        quizView = findViewById(R.id.quiz_view);

        quizViewModel.quizData.observe(this, new Observer<List<States>>() {
            @Override
            public void onChanged(List<States> states) {
                if(states != null) {
                    if(states.size() == 4 ) {
                        quizView.setData(states);
                    }  else {
                        Toast.makeText(MainActivity.this, "Add more States", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        quizView.setOptionsClickListener(new QuizView.OptionsClickListener() {
            @Override
            public void optionsClick(boolean result) {
                updateResult(result);
            }
        });
    }

    private void updateResult(boolean result) {
        if(result) {
            Toast.makeText(MainActivity.this, "Correct Answer", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Wrong Answer", Toast.LENGTH_LONG).show();
        }
        quizViewModel.refreshGame();
        quizView.resetGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_list:
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_settings:
                Intent intent1 = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent1);
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }
}
