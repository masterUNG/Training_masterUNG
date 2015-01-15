package appewtc.masterung.myvideo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.InputStream;
import java.net.URL;


public class ResultActivity extends ActionBarActivity {

    //Explicit
    private ImageView imvStroy;
    private TextView txtShowStory;
    private VideoView myVideoView;
    private String strStory, strImageURL, strVideoURL;
    private ProgressDialog objProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Initial Widget
        initialWidget();

        //Get Value from Intent
        getValueFromIntent();

        //Show Story
        txtShowStory.setText(strStory);

        //Process Download From URL
        new DownloadImageFromURL().execute(strImageURL);

    }   // onCreate


    private class DownloadImageFromURL extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {

            String strImageURL = params[0];
            Bitmap objBitmap = null;

            try {

                InputStream objInputStream = new URL(strImageURL).openStream();
                objBitmap = BitmapFactory.decodeStream(objInputStream);

            } catch (Exception e) {
                Log.d("video", "Error for LoadImage ==> " + e.toString());
            }

            return objBitmap;
        }   // doInBackground


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            objProgressDialog = new ProgressDialog(ResultActivity.this);
            objProgressDialog.setTitle("Download Image");
            objProgressDialog.setMessage("Please Wait Few Minute");
            objProgressDialog.setIndeterminate(false);
            objProgressDialog.show();

        }   // onPreExecute

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            imvStroy.setImageBitmap(bitmap);
            objProgressDialog.dismiss();

        } // onPostExecute

    }   // DownloadImageFromURL Class


    private void getValueFromIntent() {
        strStory = getIntent().getExtras().getString("Story");
        strImageURL = getIntent().getExtras().getString("ImageURL");
        strVideoURL = getIntent().getExtras().getString("VideoURL");
    }   // getValueFromIntent

    private void initialWidget() {
        imvStroy = (ImageView) findViewById(R.id.imvStory);
        txtShowStory = (TextView) findViewById(R.id.txtShowStory);
        myVideoView = (VideoView) findViewById(R.id.videoView);
    }   //initialWidget


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}   // Main Class
