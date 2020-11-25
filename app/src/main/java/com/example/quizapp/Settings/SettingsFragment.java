package com.example.quizapp.Settings;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.quizapp.Notification.NotificationWorker;
import com.example.quizapp.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SettingsFragment extends PreferenceFragmentCompat {
    private String NOTIFICATION_WORK = "work";
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        SwitchPreference switchPreference = findPreference("notification_pref");
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                long currentTime = System.currentTimeMillis();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);

                if(calendar.getTimeInMillis() < currentTime){
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                }

                final WorkManager workManager = WorkManager.getInstance(requireActivity());
                final PeriodicWorkRequest.Builder workRequestBuilder = new PeriodicWorkRequest.Builder(
                        NotificationWorker.class,
                        1,
                        TimeUnit.DAYS
                );
                workRequestBuilder.setInitialDelay(calendar.getTimeInMillis() - currentTime, TimeUnit.MILLISECONDS);
                Boolean notification = (Boolean) newValue;
                if(notification) {
                    workManager.enqueueUniquePeriodicWork(
                            NOTIFICATION_WORK,
                            ExistingPeriodicWorkPolicy.REPLACE,
                            workRequestBuilder.build());
                } else {
                    workManager.cancelUniqueWork(NOTIFICATION_WORK);
                }
                return true;
            }
        });
    }
}
