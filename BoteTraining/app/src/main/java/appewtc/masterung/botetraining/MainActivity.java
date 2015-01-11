package appewtc.masterung.botetraining;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    //Explicit
    private EditText edtTHB;
    private RadioGroup radUnit;
    private RadioButton radUSD, radGBP, radYEN;
    private TextView txtAnswer;
    private Button btnExchange;
    private double douFactor, douTHB, douAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_both);

        bindWidget();

        radUnit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radUSD:
                        douFactor = 0.03;
                        break;
                    case R.id.radGBP:
                        douFactor = 0.02;
                        break;
                    case R.id.radYEN:
                        douFactor = 3.64;
                        break;
                    default:
                        douFactor = 0;
                }
            }
        });

        clickExchange();

    }   // onCreate

    private void clickExchange() {

        btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                douTHB = Double.parseDouble(edtTHB.getText().toString().trim());
                douAnswer = douTHB * douFactor;
                txtAnswer.setText(String.valueOf(douAnswer));


            }   // event
        });

    }   // clickExchange

    private void bindWidget() {

        edtTHB = (EditText) findViewById(R.id.edtTHB);
        radUnit = (RadioGroup) findViewById(R.id.radUnit);
        radUSD = (RadioButton) findViewById(R.id.radUSD);
        radGBP = (RadioButton) findViewById(R.id.radGBP);
        radYEN = (RadioButton) findViewById(R.id.radYEN);
        txtAnswer = (TextView) findViewById(R.id.txtAnswer);
        btnExchange = (Button) findViewById(R.id.btnExchange);

    }   // bindWidget


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
