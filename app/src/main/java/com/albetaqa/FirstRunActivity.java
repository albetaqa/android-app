package com.albetaqa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class FirstRunActivity extends ActionBarActivity {

    private boolean acceptClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean  force = false;

        Intent intent = getIntent();
        if( intent!= null)
            force = intent.getBooleanExtra( "force" , false );


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FirstRunActivity.this);
        boolean firstRun = preferences.getBoolean("first_run",true);

        if(force || firstRun) {
            setContentView(R.layout.activity_first_run);

            final Button b = (Button) this.findViewById(R.id.accept_button);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    synchronized (this) {
                        if (!acceptClicked) {
                            b.setText("جاري التحميل - فصبر جميل");

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FirstRunActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("first_run", false);
                            editor.apply();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                    }

                                    initializeAppResources();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                            startActivity(new Intent(FirstRunActivity.this, MainActivity.class));
                                        }
                                    });
                                }
                            }).start();
                            acceptClicked = true;
                        }
                    }
                }
            });
        }
        else
        {
            finish();
            startActivity(new Intent(FirstRunActivity.this, MainActivity.class));
        }
    }

    private void initializeAppResources() {
        // TODO
        // create a folder for files
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

        return super.onOptionsItemSelected(item);
    }

    private void toast(String t)
    {
        Toast toast = Toast.makeText(getApplicationContext(), t,  Toast.LENGTH_LONG);
        toast.show();
    }

    public void help(View v){toast( getString( R.string.splash_help_content) );}

    public void settingBtnClicked( View v )
    {
        startActivity(new Intent(this,SettingsActivity.class));
    }

}
