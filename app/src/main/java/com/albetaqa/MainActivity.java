package com.albetaqa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends Activity {

    class DailyURLTask extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage(getString(R.string.loading_betaqa));
            pDialog.show();

        }

        @Override
        protected Object doInBackground(Object[] params) {
            Intent i = new Intent(MainActivity.this, DailyBetaqaService.class);
            i.putExtra("requested",true);
            startService(i);
            return null;
        }
    }

    class DailyBroadcastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            pDialog.dismiss();
            String betaqaURL = intent.getStringExtra("betaqa_url");
            if( betaqaURL != null ) {
                intent = new Intent(MainActivity.this, CardActivity.class);
                Bundle b = new Bundle();
                b.putString("image_url", betaqaURL);
                intent.putExtras(b);

                startActivity(intent);
            }
            else
                Toast.makeText(MainActivity.this, getString(R.string.error_download_betaqa), Toast.LENGTH_SHORT).show();
        }
    }
    ProgressDialog pDialog = null;
    DailyBroadcastReceiver d = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new AlertDialog.Builder(this).setTitle("لم يكتمل بعد")
                .setMessage("البرنامج في مرحلة التطوير, وسيتم الانتهاء منه قريبًا بإذن الله\nقد لا تعمل الشاشات وهذا طبيعي, نسألكم الدعاء")
                .create().show();
        //AlertDialog b =  AlertDialog.;

    }

    @Override
    protected void onResume() {
        super.onResume();
        d = new DailyBroadcastReceiver();
        registerReceiver( d , new IntentFilter( "daily_url" ));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(d);
        d = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            settingBtnClicked(null);
            return true;
        }
        else if(id == R.id.action_about )
        {
            finish();
            Intent intent = new Intent(this, FirstRunActivity.class);
            intent.putExtra("force",true);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }




    public void dailyClicked( View v )
    {
        new DailyURLTask().execute();
    }

    public void rndClicked( View v )
    {
        //finish();
        Intent intent = new Intent(this, CardActivity.class);
        startActivity(intent);
    }

    public void allClicked( View v ){notYet();}
    public void favClicked( View v ){notYet();}

    public void notYet(){
        Toast.makeText(this,"لم يكتمل التطبيق بعد, بإذن الله قريبًا سيتم تفعيل هذه الخاصية", Toast.LENGTH_SHORT).show();
    }

    public void settingBtnClicked( View v )
    {
        startActivity(new Intent(this,SettingsActivity.class));
    }
}
