package appewtc.masterung.myofficer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class DetailOfficerActivity extends ActionBarActivity {

    //Explicit
    private ImageView imvPicture;
    private TextView txtOfficer, txtPosition;
    private VideoView myVideoView;
    private String strOfficer, strPosition, strImageURL, strVideoURL, strSoundURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_officer);

        //Bind Widget
        bindWidget();

        //get Intent
        myGetIntent();

        //set Text
        setText();

        //Call AsyncTask
        GetXMLTask objGetXMLTask = new GetXMLTask();
        objGetXMLTask.execute(new String[] {strImageURL});

        //Call Video
        //callVideo();

        MediaController objMediaController = new MediaController(this);
        objMediaController.setAnchorView(myVideoView);
        objMediaController.setMediaPlayer(myVideoView);
        Uri objUri = Uri.parse(strVideoURL);
        myVideoView.setMediaController(objMediaController);
        myVideoView.setVideoURI(objUri);
        myVideoView.start();


    }   // onCreate

    private void callVideo() {

        MediaController objMediaController = new MediaController(this);
        objMediaController.setAnchorView(myVideoView);
        objMediaController.setMediaPlayer(myVideoView);
        Uri objUri = Uri.parse(strVideoURL);
        myVideoView.setMediaController(objMediaController);
        myVideoView.setVideoURI(objUri);
        myVideoView.start();

        Log.d("Office", "Video ==> " + strVideoURL);

    }   // callVideo


    //Create AsyncTask
    private class GetXMLTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap objBitmap = null;
            for (String strUrl : params) {

                objBitmap = downloadImage(strUrl);

            }   // for

            return objBitmap;
        }   // doInBackGround

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            imvPicture.setImageBitmap(bitmap);

        }   // onPostExecute

        private Bitmap downloadImage(String strUrl) {

            Bitmap objBitmap = null;
            InputStream objInputStream = null;
            BitmapFactory.Options objBitmapFactory = new BitmapFactory.Options();
            objBitmapFactory.inSampleSize = 1;

            try {

                objInputStream = getHttpConnection(strUrl);
                objBitmap = BitmapFactory.decodeStream(objInputStream, null, objBitmapFactory);
                objInputStream.close();

            } catch (Exception e) {
                Log.d("Officer", "Error from downloadImage ==> " + e.toString());
            }

            return objBitmap;
        }   // downloadImage

        private InputStream getHttpConnection(String strUrl) throws IOException {

            InputStream objInputStream = null;
            URL objURL = new URL(strUrl);
            URLConnection objURLConnection = objURL.openConnection();


            try {

                HttpURLConnection objHttpURLConnection = (HttpURLConnection) objURLConnection;
                objHttpURLConnection.setRequestMethod("GET");
                objHttpURLConnection.connect();

                if (objHttpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                    objInputStream = objHttpURLConnection.getInputStream();
                }   // if

            } catch (Exception e) {
                Log.d("Office", "Error getHttpConncetion ==> " + e.toString());
            }


            return objInputStream;
        }   // getHttpConnection

    }   // GetXMLTask Class




    private void setText() {

        txtOfficer.setText(strOfficer);
        txtPosition.setText(strPosition);

    }   // setText

    private void myGetIntent() {

        strOfficer = getIntent().getExtras().getString("Officer");
        strPosition = getIntent().getExtras().getString("Position");
        strImageURL = getIntent().getExtras().getString("Image");
        strVideoURL = getIntent().getExtras().getString("Video");
        strSoundURL = getIntent().getExtras().getString("Sound");

    }   // myGetIntent

    private void bindWidget() {

        imvPicture = (ImageView) findViewById(R.id.imvOfficer);
        txtOfficer = (TextView) findViewById(R.id.txtShowOfficer);
        txtPosition = (TextView) findViewById(R.id.txtShowPosition);
        myVideoView = (VideoView) findViewById(R.id.videoView);

    }   // bindWidget

    public void clickReadAll(View view) {

        Intent objIntent = new Intent(DetailOfficerActivity.this, ShowOfficerListView.class);
        startActivity(objIntent);
        finish();
    }   // clickReadAll

    public void clickSearch(View view) {

    }   // clickSearch


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_officer, menu);
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
