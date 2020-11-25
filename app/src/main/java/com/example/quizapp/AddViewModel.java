package com.example.quizapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.quizapp.Database.StatesRepository;
import com.example.quizapp.data.States;

public class AddViewModel extends AndroidViewModel {
    private StatesRepository statesRepository;
    public LiveData<PagedList<States>> pagedListLiveData;

    public AddViewModel(Application application) {
        super(application);
        statesRepository = StatesRepository.getStatesRepository(application);
        pagedListLiveData = statesRepository.getAllStates();
    }

    public void insert(States states) {
        statesRepository.insertStates(states);
    }

    public void update(States states) {
        statesRepository.updateStates(states);
    }
}
