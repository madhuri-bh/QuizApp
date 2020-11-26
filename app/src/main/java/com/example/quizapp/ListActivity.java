package com.example.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.List.StatesListPagingAdapter;
import com.example.quizapp.data.States;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class ListActivity extends AppCompatActivity {

    public static final int NEW_DATA_REQUEST_CODE = 1;
    public static final int NEW_UPDATE_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_STATES = "extra_data_states";
    public static final String EXTRA_DATA_CAPITAL = "extra_data_capital";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private States states;

    private SharedPreferences sharedPreferences;

    private StateViewModel stateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        final StateViewModel stateViewModel = new ViewModelProvider(this).get(StateViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sort = sharedPreferences.getString("sort_pref", "id");
        stateViewModel.changeSortingOrder(sort);

        final StatesListPagingAdapter statesListPagingAdapter = new StatesListPagingAdapter();
        recyclerView.setAdapter(statesListPagingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        stateViewModel.pagedListLiveData.observe(this, new Observer<PagedList<States>>() {
            @Override
            public void onChanged(PagedList<States> states) {
                statesListPagingAdapter.submitList(states);
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.FloatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, NEW_DATA_REQUEST_CODE);
            }
        });

        final ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout);
        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Deleted", BaseTransientBottomBar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stateViewModel.insert(states);
                    }
                });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                states = statesListPagingAdapter.getStatesAtPosition(position);
                stateViewModel.delete(states);
                snackbar.show();
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

        statesListPagingAdapter.setOnItemClickListener(new StatesListPagingAdapter.ClickListener() {
            @Override
            public void itemClick(int position, View view) {
                States currentStates = statesListPagingAdapter.getStatesAtPosition(position);
                launchUpdateNotesActivity(currentStates);
            }
        });

    }

    public void launchUpdateNotesActivity(States currentStates) {
        Intent intent = new Intent(ListActivity.this, AddActivity.class);
        intent.putExtra(EXTRA_DATA_STATES, currentStates.getState());
        intent.putExtra(EXTRA_DATA_CAPITAL, currentStates.getCapital());
        intent.putExtra((EXTRA_DATA_ID), currentStates.getId());
        startActivityForResult(intent, NEW_UPDATE_REQUEST_CODE);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("sort_pref")) {
                String s = sharedPreferences.getString("sort_pref", "id");
                stateViewModel.changeSortingOrder(s);
            }
        }
    };
}

