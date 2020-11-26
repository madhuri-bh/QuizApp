package com.example.quizapp.Custom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quizapp.Database.StatesRepository;
import com.example.quizapp.data.States;

import java.util.List;

public class QuizViewModel extends AndroidViewModel {

    public StatesRepository statesRepository;
    public MutableLiveData<List<States>> quizData = new MutableLiveData<>();

    public QuizViewModel(@NonNull Application application) {
        super(application);
        statesRepository = StatesRepository.getStatesRepository(application);
        loadGame();
    }

    private void loadGame() {
        try{
           quizData.postValue(statesRepository.getQuizStates().get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshGame() {
        loadGame();
    }


}
