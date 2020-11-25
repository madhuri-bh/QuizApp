package com.example.quizapp.Database;

import android.database.sqlite.SQLiteQuery;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.paging.DataSource;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.quizapp.data.States;

import java.util.List;


@Dao
public interface StatesDao {

    @Query("SELECT * FROM States ORDER BY state asc")
    DataSource.Factory<Integer, States> getAllStates();

    @RawQuery(observedEntities = States.class)
    DataSource.Factory<Integer, States> getAllSortedStates(SupportSQLiteQuery query);

    @Insert
    void insert(States states);

    @Update
    void update(States states);

    @Delete
    void delete(States states);

    @Query("SELECT * FROM States ORDER BY RANDOM() LIMIT 1")
    States getStateForNotification();

    @Query("SELECT DISTINCT * FROM States ORDER BY RANDOM() LIMIT 4")
    List<States> getQuizStates();


}
