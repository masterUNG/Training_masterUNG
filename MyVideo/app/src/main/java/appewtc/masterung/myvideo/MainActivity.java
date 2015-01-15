package appewtc.masterung.myvideo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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


public class MainActivity extends ActionBarActivity {

    //Explicit
    private UserTABLE objUserTABLE;
    private ServiceTABLE objServiceTABLE;
    private EditText edtUser, edtPassword;
    private String strUserChoose, strPasswordChoose, strPasswordTrue, strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initial Widget
        initialWidget();

        //Call Database
        objUserTABLE = new UserTABLE(this);
        objServiceTABLE = new ServiceTABLE(this);

        //Tester Add Value
        /*
        objUserTABLE.addNewDataToUSER(MainActivity.this, "testUser", "testPass", "testName");
        objServiceTABLE.addValueToServie(MainActivity.this, "story", "Image", "Video");
        */

        //Check Internet
        checkInternet();



    }       // onCreate

    private void initialWidget() {

        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

    }   // initialWidget


    public void clickLogin(View view) {

        strUserChoose = edtUser.getText().toString().trim();
        strPasswordChoose = edtPassword.getText().toString().trim();

        //Check Zero
        if (strUserChoose.equals("") || strPasswordChoose.equals("") ) {

            MyAlertDalog objMyAlert = new MyAlertDalog();
            objMyAlert.errorDialog(MainActivity.this, "Have Space", "Please Fill in Every Blank");

        } else {

            //CheckUser
            checkUser();

        }   // Check Zero

    }   // clickLogin

    private void checkUser() {

        try {

            String arrayDATA[] = objUserTABLE.searchUser(strUserChoose);
            strPasswordTrue = arrayDATA[2];
            strName = arrayDATA[3];

            Log.d("video", "welcome = " +strName);

            //Check Password
            checkPassword();


        } catch (Exception e) {

            MyAlertDalog objMyalert = new MyAlertDalog();
            objMyalert.errorDialog(MainActivity.this, "No User", "No " + strUserChoose + " on my Database");

        }

    }   // checkUser

    private void checkPassword() {

        if (strPasswordChoose.equals(strPasswordTrue)) {

            AlertDialog.Builder objAlert = new AlertDialog.Builder(this);
            objAlert.setIcon(R.drawable.icon_question);
            objAlert.setTitle("Welcome");
            objAlert.setMessage("Welcome " + strName + " to my Video");
            objAlert.setCancelable(false);
            objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent objIntent = new Intent(MainActivity.this, ListVideo.class);
                    startActivity(objIntent);
                    deleteAllData();
                    finish();

                }
            });
            objAlert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    edtUser.setText("");
                    edtPassword.setText("");
                    dialog.dismiss();
                }
            });
            objAlert.show();

        } else {

            MyAlertDalog objMyAlert = new MyAlertDalog();
            objMyAlert.errorDialog(MainActivity.this, "Password False", "Please Try Again Password False");

        }


    }   //checkPassword


    private void checkInternet() {

        ConnectivityManager objConnectivitiManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo objNetworkInfo = objConnectivitiManager.getActiveNetworkInfo();

        if (objNetworkInfo != null && objNetworkInfo.isConnected() ) {

            Log.d("video", "Connected InterNet OK");
            syncJSONtoMySQL();

        } else {

            MyAlertDalog objMyAlert = new MyAlertDalog();
            objMyAlert.errorDialog(MainActivity.this, "Cannot Connected", "Please Check Your Internet");

        }   // if

    }   // checkInternet

    private void syncJSONtoMySQL() {

        //Setup Policy
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);
        }

        //delete All data
        deleteAllData();


        //Create InputStream
        InputStream objInputStream = null;
        String strJSON = "";

        try {

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/jan/php_get_data_master.php");
            HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
            HttpEntity objHttpEntity = objHttpResponse.getEntity();
            objInputStream = objHttpEntity.getContent();

        } catch (Exception e) {
            Log.d("video", "Error from InputStream ==> " + e.toString());
        }


        //Create strJSON
        try {

            BufferedReader objButteredReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
            StringBuilder objStringBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = objButteredReader.readLine()) != null ) {
                objStringBuilder.append(strLine);
            }   // while

            objInputStream.close();
            strJSON = objStringBuilder.toString();

        } catch (Exception e) {
            Log.d("video", "Error strJSON ==> " + e.toString());
        }


        //Update to SQLite
        try {

            final JSONArray objJSONArray = new JSONArray(strJSON);

            for (int i = 0; i < objJSONArray.length(); i++) {

                JSONObject objJSONObject = objJSONArray.getJSONObject(i);
                String strUser = objJSONObject.getString("User");
                String strPassword = objJSONObject.getString("Password");
                String strName = objJSONObject.getString("Name");

                long addValue = objUserTABLE.addNewDataToUSER(MainActivity.this, strUser, strPassword, strName);

            }   // for


        } catch (Exception e) {
            Log.d("video", "Error Update ==> " + e.toString());
        }



    }   // syncJSONtoMySQLite

    private void deleteAllData() {

        SQLiteDatabase objSQLite = openOrCreateDatabase("video.db", MODE_PRIVATE, null);
        objSQLite.delete("userTABLE", null, null);

    }// deleteAllData


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
