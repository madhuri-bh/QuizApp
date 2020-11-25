package com.example.quizapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "States")
public class States {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "capital")
    private String capital;

    public States(long id, String state, String capital) {
        this.id = id;
        this.state = state;
        this.capital = capital;
    }

    @Ignore
    public States(String state, String capital) {
        this.state = state;
        this.capital = capital;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public boolean isStatesEqual(States s){
        return (state.equals(s.getState()) && capital.equals(s.getCapital()));
    }


}
