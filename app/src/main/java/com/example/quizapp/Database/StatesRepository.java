package com.example.quizapp.Database;

import android.app.Application;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.quizapp.data.States;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StatesRepository {
    private static StatesRepository statesRepository = null;
    private StatesDao statesDao;
    private int PAGE_SIZE = 15;
    private ExecutorService executors = Executors.newSingleThreadExecutor();

    private StatesRepository(Application application) {
        StatesDatabase database = StatesDatabase.getInstance(application);
        statesDao = database.statesDao();
    }

    public static StatesRepository getStatesRepository(Application application) {
        if (statesRepository == null) {
            synchronized (StatesRepository.class) {

                if (statesRepository == null) {
                    statesRepository = new StatesRepository(application);
                }
            }
        }
        return statesRepository;
    }

    public void insertStates(final States states) {
        executors.execute(new Runnable() {
            @Override
            public void run() {
                statesDao.insert(states);
            }
        });
    }

    public void updateStates(final States states) {
        executors.execute(new Runnable() {
            @Override
            public void run() {
                statesDao.update(states);
            }
        });
    }

    public void deleteStates(final States states) {
        executors.execute(new Runnable() {
            @Override
            public void run() {
                statesDao.delete(states);
            }
        });
    }

    public LiveData<PagedList<States>> getAllStates() {
        return new LivePagedListBuilder<>(
                statesDao.getAllStates(), PAGE_SIZE
        ).build();
    }

    public LiveData<PagedList<States>> getAllSortStates(String sortBy){
        LiveData data = new LivePagedListBuilder<>(
                statesDao.getAllSortedStates(ConstructQuery(sortBy)), PAGE_SIZE
        ).build();
        return data;
    }

    public SupportSQLiteQuery ConstructQuery(String sortBy){
        String query = "SELECT * FROM States ORDER BY " + sortBy + " ASC";
        return new SimpleSQLiteQuery(query);
    }

    @WorkerThread
    public States getStateForNotification() {
        return statesDao.getStateForNotification();
    }

    public Future<List<States>> getQuizStates(){
        Callable<List<States>> callable = new Callable<List<States>>() {
            @Override
            public List<States> call() throws Exception {
                return statesDao.getQuizStates();
            }
        };
        return executors.submit(callable);
    }


}
