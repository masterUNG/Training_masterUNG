package appewtc.masterung.myofficer;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ShowOfficerListView extends ListActivity{

    //Explicit
    private OfficerTABLE objOfficerTABLE;
    private SimpleCursorAdapter objSimpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_officer_list_view);

        objOfficerTABLE = new OfficerTABLE(this);

        deleteOfficerData();

        //Syn JSON to OfficerTABLE
        synJSONtoOfficerTABLE();

        //Create Listview
        Cursor objCursor = objOfficerTABLE.readAllData();
        String[] from = new String[]{OfficerTABLE.COLUMN_OFFICER};
        int[] target = new int[]{R.id.txtShowListOfficer};
        objSimpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_show_officer_list_view, objCursor, from, target);
        setListAdapter(objSimpleCursorAdapter);

    }   // onCreate

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Cursor objCursor = (Cursor) l.getItemAtPosition(position);

        String strOfficer = objCursor.getString(objCursor.getColumnIndex(OfficerTABLE.COLUMN_OFFICER));
        String strPosition = objCursor.getString(objCursor.getColumnIndex(OfficerTABLE.COLUMN_POSITION));
        String strImage = objCursor.getString(objCursor.getColumnIndex(OfficerTABLE.COLUMN_IMAGE));
        String strVideo = objCursor.getString(objCursor.getColumnIndex(OfficerTABLE.COLUMN_VIDEO));
        String strSound = objCursor.getString(objCursor.getColumnIndex(OfficerTABLE.COLUMN_SOUND));

        //Intent to DetailOfficerActivity
        Intent objIntent = new Intent(ShowOfficerListView.this, DetailOfficerActivity.class);
        objIntent.putExtra("Officer", strOfficer);
        objIntent.putExtra("Position", strPosition);
        objIntent.putExtra("Image", strImage);
        objIntent.putExtra("Video", strVideo);
        objIntent.putExtra("Sound", strSound);
        startActivity(objIntent);
        finish();
    }   // onListItenClick

    private void deleteOfficerData() {
        SQLiteDatabase objSQLite = openOrCreateDatabase("Office.db", MODE_PRIVATE, null);
        objSQLite.delete("officerTABLE", null, null);
    }   // deleteOfficerData

    private void synJSONtoOfficerTABLE() {

        //Setup Policy
        if (Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy objMyPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(objMyPolicy);

        }   // if

        InputStream objInputStream = null;
        String strJSON = "";

        //Create InputStream
        try {

            HttpClient objHttpClicnt = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/test1/php_get_data_officer.php");
            HttpResponse objHttpResponse = objHttpClicnt.execute(objHttpPost);
            HttpEntity objHttpEntity = objHttpResponse.getEntity();
            objInputStream = objHttpEntity.getContent();

        } catch (Exception e) {
            Log.d("Office", "Error from InputStream ==> " + e.toString());
        }



        //Create strJSON
        try {

            BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
            StringBuilder objStringBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = objBufferedReader.readLine()) != null) {

                objStringBuilder.append(strLine);

            }   // while

            objInputStream.close();
            strJSON = objStringBuilder.toString();



        } catch (Exception e) {
            Log.d("Office", "Error from strJSON ==> " + e.toString());
        }


        //Update Record to SQLite
        try {

            final JSONArray objJSONArray = new JSONArray(strJSON);

            for (int intTimes = 0; intTimes < objJSONArray.length(); intTimes++) {

                JSONObject objJSONObject = objJSONArray.getJSONObject(intTimes);

                String strOfficer = objJSONObject.getString("Officer");
                String strPosition = objJSONObject.getString("Position");
                String strImage = objJSONObject.getString("Image");
                String strVideo = objJSONObject.getString("Video");
                String strSound = objJSONObject.getString("Sound");

                long addOfficer = objOfficerTABLE.addRecordOfficerTable(ShowOfficerListView.this, strOfficer, strPosition, strImage, strVideo, strSound);

            }   // for


        } catch (Exception e) {
            Log.d("Office", "Error from Update ==> " + e.toString());
        }




    }   // synJSONtoOfficerTABLE


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_officer_list_view, menu);
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
