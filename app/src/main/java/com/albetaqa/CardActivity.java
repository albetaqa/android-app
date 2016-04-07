package com.albetaqa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.albetaqa.util.ZoomableImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Random;


public class CardActivity extends Activity {



    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CardActivity.this);
            pDialog.setMessage(getString(R.string.loading_betaqa));
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                InputStream content = (InputStream) url.getContent();
                bitmap = BitmapFactory.decodeStream(content);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                touch.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(CardActivity.this, getString(R.string.error_download_betaqa), Toast.LENGTH_SHORT).show();

            }
        }
    }


    ProgressDialog pDialog = null;
    private ZoomableImageView touch = null;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        touch = (ZoomableImageView)findViewById(R.id.zimage);

        String imageUrl = null;

        Bundle extras = getIntent().getExtras();
        if( extras != null )
            imageUrl = extras.getString("image_url");

        if( imageUrl != null )
        {
            // Toast.makeText( this, imageUrl, Toast.LENGTH_SHORT).show();
            new LoadImage().execute(imageUrl);
        }
        // random betaqa or waraqa
        else
        {
            findViewById( R.id.refreshBtn ).setVisibility( View.VISIBLE );
            randomBetaqa();
        }
        /*
        int[] cards = {R.drawable.ad3yatelrasoul011,

                R.drawable.adab0027,
                R.drawable.akhlaq0069,
                R.drawable.akhlaq0245,
                R.drawable.alzeenamno007,
                R.drawable.aqeda0057,
                R.drawable.ebadat0033,
                R.drawable.ebadat0072,
                R.drawable.ebadat0167,
                R.drawable.manhyat0055
        };
        int randomCard = 0;
        int index = (int)((Math.random()*1000)%10);
        randomCard = cards[index];


        touch.setImageBitmap( BitmapFactory.decodeResource(getApplicationContext().getResources(), randomCard));
        */
    }

    private void randomBetaqa() {
        String imageUrl = null;
        String baseURL = null;
        Random random = new Random(new Date().getTime());
        int bOrW = random.nextInt();
        InputStream input = null;
        int rnd = 0;

        // random betaqa
        if( bOrW%2 == 0 )
        {
            int betaqaCount = 6932;
            rnd = random.nextInt(betaqaCount);
            input = getResources().openRawResource(R.raw.albetaqa);
            baseURL = "http://albetaqa.com/social/img/albetaqa/albetaqa/albetaqa/";
        }
        // random waraqa
        else
        {
            int waraqaCount = 3160;
            rnd = random.nextInt(waraqaCount);
            input = getResources().openRawResource(R.raw.waraqas);
            baseURL = "http://albetaqa.com/social/img/alwaraqa/alwaraqa/alwaraqa/";
        }

        BufferedReader r = new BufferedReader( new InputStreamReader(input));

        try {
            for(int i=0; i<rnd-1; i++)
                r.readLine();

            imageUrl = r.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, imageUrl, Toast.LENGTH_SHORT).show();
        new LoadImage().execute(baseURL+imageUrl);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

    public void settingBtnClicked( View v )
    {
        startActivity(new Intent(this,SettingsActivity.class));
    }

    public void refreshClicked(View v)
    {
        randomBetaqa();
    }

    public void shareClicked(View v)
    {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString( R.string.share_text ));


//        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", null);

        try {
            File file = new File(Environment.getExternalStorageDirectory().getPath().toString()+"/Images/");
            if( !file.exists() )
                file.mkdir();

            file = new File(file.getAbsolutePath() , "betaqa_temp.png");

            if( !file.exists() )
                file.createNewFile();

            FileOutputStream outStream = null;
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            if(outStream!=null)
                outStream.close();

            Uri uri = Uri.fromFile(file);

            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);//new URL("http://albetaqa.com/social/img/albetaqa/albetaqa/albetaqa/Ad3yatElrasoul007.jpg") );
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.share)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
