package appewtc.masterung.myvideo;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


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

        } else {

            MyAlertDalog objMyAlert = new MyAlertDalog();
            objMyAlert.errorDialog(MainActivity.this, "Cannot Connected", "Please Check Your Internet");

        }   // if

    }   // checkInternet


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