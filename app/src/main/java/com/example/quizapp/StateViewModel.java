package com.example.quizapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;
import com.example.quizapp.Database.StatesRepository;
import com.example.quizapp.data.States;

public class StateViewModel extends AndroidViewModel {
    StatesRepository statesRepository;
    public LiveData<PagedList<States>> pagedListLiveData;

    public MutableLiveData<String> sortOrderChanged = new MutableLiveData<>();

    public StateViewModel(@NonNull Application application) {
        super(application);
        statesRepository = StatesRepository.getStatesRepository(application);
        pagedListLiveData = statesRepository.getAllStates();
        sortOrderChanged.setValue("id");
        pagedListLiveData = Transformations.switchMap(sortOrderChanged, new Function<String, LiveData<PagedList<States>>>() {
            @Override
            public LiveData<PagedList<States>> apply(String input) {
                return statesRepository.getAllSortStates(input);
            }
        });
    }

    public void changeSortingOrder(String sortBy){
        switch (sortBy) {
            case "Id" : sortBy = "id";
            break;
            case "State" : sortBy = "state";
            break;
            case "Capital" : sortBy = "capital";
            break;
        }
        sortOrderChanged.postValue(sortBy);
    }

    public void insert(States states) {
        statesRepository.insertStates(states);
    }

    public void delete(States states) {
        statesRepository.deleteStates(states);
    }
}
