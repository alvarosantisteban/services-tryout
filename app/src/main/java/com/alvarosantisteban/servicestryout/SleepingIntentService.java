package com.alvarosantisteban.servicestryout;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

/**
 * An IntentService that just sleeps for 5 seconds and then wakes up.
 *
 * If the app is killed while sleeping, the wake up Toast is not delivered.
 *
 * @author Alvaro Santisteban Dieguez 4/05/15 - alvarosantisteban@gmail.com
 */
public class SleepingIntentService extends IntentService {

    private Handler mHandler;

    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public SleepingIntentService() {
        super("Name of the worker thread: SleepingIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SleepingIntentService.this, "Go to sleep!", Toast.LENGTH_SHORT).show();
            }
        });

        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        long endTime = System.currentTimeMillis() + 5 * 1000;
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SleepingIntentService.this, "Wake up!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
