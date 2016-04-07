package com.albetaqa;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class DailyBetaqaService extends Service {
    private static volatile boolean closing = false;
    private final Thread t;
    private final Handler h;
    private String lastReadURL = null;
    private volatile boolean requested = false;

    public DailyBetaqaService() {
        h = new Handler(Looper.getMainLooper());

        t = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (!closing) {
                            try {

                                try {
                                    URL url = new URL(getString(R.string.daily_betaqa_url));

                                    // Read text returned by the server
                                    InputStream in2 = url.openStream();
                                    InputStreamReader in1 = new InputStreamReader(in2);
                                    BufferedReader in = new BufferedReader(in1);
                                    final String s = in.readLine();
                                    in.close();

                                    // Display a notification in case a new Betaqa is found
                                    if( s != null && lastReadURL != null && !s.equals(lastReadURL) ) {
                                        h.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(DailyBetaqaService.this.getApplicationContext(), getString(R.string.daily_betaqa_notify), Toast.LENGTH_SHORT).show();

                                                // TODO check api version here and have different functions for different APIs
                                                notifiyNewDailyAPI16Plus(s);
                                            }
                                        });
                                    }



                                    lastReadURL = s;

                                    // Send a broadcast with last read URL in case the main activity send an intent requiring that
                                    //""

                                    if( requested ) {
                                        requested = false;
                                        Intent dailyURL = new Intent("daily_url");
                                        dailyURL.putExtra("betaqa_url", lastReadURL);
                                        sendBroadcast(dailyURL);
                                    }

                                }
                                catch (MalformedURLException e) {
                                }
                                catch (IOException e) {
                                }

                                Thread.sleep( 5000 );
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }
        );
    }

    @TargetApi(16)
    private void notifiyNewDailyAPI16Plus(String s) {
        Intent intent = new Intent(this, CardActivity.class);
        Bundle b = new Bundle();
        b.putString("image_url", s);
        intent.putExtras(b);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(this)
                .setContentTitle(getString(R.string.daily_betaqa_notify))
                .setContentText("بطاقة اليوم")
                .setSmallIcon(R.drawable.app_icon)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
//                                                        .addAction(R.drawable.icon, "Call", pIntent)
//                                                        .addAction(R.drawable.icon, "More", pIntent)
//                                                        .addAction(R.drawable.icon, "And more", pIntent)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        closing = false;

        requested = intent.getBooleanExtra( "requested" , false );

        if( !t.isAlive() )
            t.start();
        else {
            t.interrupt();
        }
        return START_STICKY;
    }

    public void end()
    {
        if( t != null ) {
            closing = true;
            t.interrupt();
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
