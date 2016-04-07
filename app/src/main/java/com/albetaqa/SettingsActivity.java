package com.albetaqa;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class SettingsActivity extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }



    private void toast(String t)
    {
        Toast toast = Toast.makeText(getApplicationContext(), t,  Toast.LENGTH_LONG);
        toast.show();
    }

    public void dailyHelp(View v){toast( getString( R.string.daily_help) );}
    public void dailyAutoDownloadHelp(View v){toast( getString( R.string.daily_auto_download_help) );}
    public void dailyNotificationHelp(View v){toast( getString( R.string.daily_notification_help) );}

    public void rndHelp(View v){toast( getString( R.string.rnd_help) );}
    public void rndAutoDownloadHelp(View v){toast( getString( R.string.rnd_auto_download_help) );}
    public void rndNotificationHelp(View v){toast( getString( R.string.rnd_notification_help) );}
    public void rndDailyLimitHelp(View v){toast( getString( R.string.rnd_daily_limit_help) );}

    public void historyHelp(View v){toast( getString( R.string.history_help) );}
    public void historyLimitHelp(View v){toast( getString( R.string.history_limit_help) );}
}