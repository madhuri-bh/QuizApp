package com.example.quizapp.Notification;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.quizapp.Database.StatesRepository;
import com.example.quizapp.data.States;

public class NotificationWorker extends Worker {

    private StatesRepository statesRepository;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        statesRepository = StatesRepository.getStatesRepository((Application) context.getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        States states = statesRepository.getStateForNotification();
        Notifications.getDailyNotification(getApplicationContext(), states);
        return Result.success();
    }
}
