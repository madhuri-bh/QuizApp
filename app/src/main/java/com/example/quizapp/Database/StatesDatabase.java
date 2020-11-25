package com.example.quizapp.Database;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quizapp.data.States;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {States.class}, version = 1, exportSchema = false)
public abstract class StatesDatabase extends RoomDatabase {

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public abstract StatesDao statesDao();

    public static StatesDatabase INSTANCE = null;

    public static StatesDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (StatesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            StatesDatabase.class,
                            "StatesDatabase")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    executor.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            prepopulateDb(context.getAssets(), INSTANCE.statesDao());
                                        }
                                    });
                                }
                            }).fallbackToDestructiveMigration().
                                    build();
                }
            }
        }
        return INSTANCE;
    }

    private static void prepopulateDb(AssetManager assetManager, StatesDao statesDao)  {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String json = "";
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(assetManager.open("states-capital.json"))
            );
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            json = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            JSONObject statesJsonObject = new JSONObject(json);
            JSONObject section = statesJsonObject.getJSONObject("sections");
            populateFromJson(section.getJSONArray("States (A-L)"), statesDao);
            populateFromJson(section.getJSONArray("States (M-Z)"), statesDao);
            populateFromJson(section.getJSONArray("Union Territories"), statesDao);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void populateFromJson(JSONArray states, StatesDao statesDao){
        try{
            for (int i = 0; i <= states.length(); i++) {
                JSONObject stateObject = states.getJSONObject(i);
                String state = stateObject.getString("key");
                String capital = stateObject.getString("val");
                statesDao.insert(new States(state, capital));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}