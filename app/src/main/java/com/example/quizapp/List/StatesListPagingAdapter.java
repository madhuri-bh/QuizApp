package com.example.quizapp.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.quizapp.R;
import com.example.quizapp.data.States;

public class StatesListPagingAdapter extends PagedListAdapter<States, StatesViewHolder> {

    private ClickListener clickListener;

    public StatesListPagingAdapter() {
        super(DIFFUTIL_CALLBACK);
    }

    @NonNull
    @Override
    public StatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list, parent, false);
        StatesViewHolder viewHolder = new StatesViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatesViewHolder holder, final int position) {
        final States currentStates = getItem(position);
        if (currentStates != null) {
            holder.bind(currentStates);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public interface ClickListener{
        void itemClick(int position, View view);
    }

    public States getStatesAtPosition(int position){
        return getItem(position);
    }

    private static DiffUtil.ItemCallback<States> DIFFUTIL_CALLBACK = new DiffUtil.ItemCallback<States>() {
        @Override
        public boolean areItemsTheSame(@NonNull States oldItem, @NonNull States newItem) {
            return (oldItem.getState().equals(newItem.getState()));
        }

        @Override
        public boolean areContentsTheSame(@NonNull States oldItem, @NonNull States newItem) {
            return oldItem.isStatesEqual(newItem);
        }
    };
}


