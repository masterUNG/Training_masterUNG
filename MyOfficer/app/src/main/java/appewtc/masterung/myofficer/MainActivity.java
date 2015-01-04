package appewtc.masterung.myofficer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
    private OfficerTABLE objOfficerTABLE;
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
        objOfficerTABLE = new OfficerTABLE(this);

        //Tester Add New Data
//        testerAddNewData();

        //Delete All Data
        deleteAllData();

        //Syn JSON to UserTABLE
        synJSONtoUserTABLE();


    }   // onCreate

    //Event Click Login
    public void clickLogin(View view) {

        strUserChoose = edtUser.getText().toString().trim();
        strPasswordChoose = edtPassword.getText().toString().trim();

        //Check Zero
        if (strUserChoose.equals("") || strPasswordChoose.equals("") ) {

            //Call Dialog
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.negativeDialog(MainActivity.this, "Have Space", "Please Fill All the Blank");

        } else {

            //Check User
            checkUser();

        }   // Check Zero

    }   // clickLogin

    private void checkUser() {

        try {

            String arrayDATA[] = objUserTABLE.searchUser(strUserChoose);
            strPasswordTrue = arrayDATA[2];
            strName = arrayDATA[3];

            Log.d("Officer", "Password ==> " + strPasswordTrue);

            //Check Password
            checkPassword();

        } catch (Exception e) {

            //User false
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.negativeDialog(MainActivity.this, "User False", "No " + strUserChoose + " in my Database");

        }

    }   // checkUser

    private void checkPassword() {

        if (strPasswordChoose.equals(strPasswordTrue)) {

            positiveDialog();

        } else {
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.negativeDialog(MainActivity.this, "Password False", "Please Try Agains, Password False");
        }

    }   // checkPassword

    private void positiveDialog() {

        AlertDialog.Builder objAlert = new AlertDialog.Builder(this);
        objAlert.setIcon(R.drawable.friend);
        objAlert.setTitle("Welcome User");
        objAlert.setMessage("Welcome " + strName + "\n" + "To my Company");
        objAlert.setCancelable(false);
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent objIntent = new Intent(MainActivity.this, ShowOfficerListView.class);
                deleteAllData();
                finish();
                startActivity(objIntent);
                dialog.dismiss();

            }
        });
        objAlert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                edtUser.setText("");
                edtPassword.setText("");
            }
        });
        objAlert.show();

    }   // positiveDialog


    private void initialWidget() {

        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

    }   // initialWidget

    private void deleteAllData() {

        SQLiteDatabase objSQLite = openOrCreateDatabase("Office.db", MODE_PRIVATE, null);
        objSQLite.delete("userTABLE", null, null);

    }   // deleteAllData

    private void synJSONtoUserTABLE() {

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
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/test1/php_get_data_master.php");
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
                String strUser = objJSONObject.getString("User");
                String strPassword = objJSONObject.getString("Password");
                String strName = objJSONObject.getString("Name");

                long addValue = objUserTABLE.addRecordToUserTABLE(MainActivity.this, strUser, strPassword, strName);

            }   // for


        } catch (Exception e) {
            Log.d("Office", "Error from Update ==> " + e.toString());
        }




    }   // synJSONtoUserTABLE

    private void testerAddNewData() {

        objUserTABLE.addRecordToUserTABLE(MainActivity.this, "testUser", "testPass", "testName");
        objOfficerTABLE.addRecordOfficerTable(MainActivity.this, "Officer", "Position", "Image", "Video", "Sound");

    }   // testerAddNewData


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
