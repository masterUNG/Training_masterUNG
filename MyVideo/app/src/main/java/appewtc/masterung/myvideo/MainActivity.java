package appewtc.masterung.myvideo;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    //Explicit
    private UserTABLE objUserTABLE;
    private ServiceTABLE objServiceTABLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call Database
        objUserTABLE = new UserTABLE(this);
        objServiceTABLE = new ServiceTABLE(this);

        //Tester Add Value
        objUserTABLE.addNewDataToUSER(MainActivity.this, "testUser", "testPass", "testName");
        objServiceTABLE.addValueToServie(MainActivity.this, "story", "Image", "Video");

        //Check Internet
        checkInternet();



    }       // onCreate

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



    }   // syncJSONtoMySQLite


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
