package appewtc.masterung.myishihara;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    //Explicit
    private TextView txtQuestion;
    private ImageView imvIshihara;
    private RadioGroup ragChoice;
    private RadioButton radChoice1, radChoice2, radChoice3, radChoice4;
    private Button btnAnswer;
    private MyModel objMyModel;
    private int intIndex, intRadioButton, intUserChoose[], intAnswerTrue[], intScore;
    private String strChoice[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initial Widget
        initialWidget();

        //Setup Score
        setupScore();

        //button Controller
        buttonController();

        //radioButton Controller
        radioController();

        //About MyModel
        aboutMyModel();


    }   // onCreate

    private void setupScore() {

        intUserChoose = new int[10];
        intAnswerTrue = new int[10];

        intAnswerTrue[0] = 1;
        intAnswerTrue[1] = 2;
        intAnswerTrue[2] = 3;
        intAnswerTrue[3] = 1;
        intAnswerTrue[4] = 2;
        intAnswerTrue[5] = 3;
        intAnswerTrue[6] = 1;
        intAnswerTrue[7] = 2;
        intAnswerTrue[8] = 4;
        intAnswerTrue[9] = 4;

    }   //setupScore

    private void setArray() {

        radChoice1.setText(strChoice[0]);
        radChoice2.setText(strChoice[1]);
        radChoice3.setText(strChoice[2]);
        radChoice4.setText(strChoice[3]);

    }   // setArray

    private void aboutMyModel() {

        objMyModel = new MyModel();

        objMyModel.setOnMyModelChangeListener(new MyModel.OnMyModelChangeListener() {
            @Override
            public void onMyModelChangeListener(MyModel myModel) {

                switch (myModel.getIntButton()) {
                    case 2:
                        imvIshihara.setImageResource(R.drawable.ishihara_02);
                        strChoice = getResources().getStringArray(R.array.time2);
                        setArray();
                        break;
                    case 3:
                        imvIshihara.setImageResource(R.drawable.ishihara_03);
                        strChoice = getResources().getStringArray(R.array.time3);
                        setArray();
                        break;
                    case 4:
                        imvIshihara.setImageResource(R.drawable.ishihara_04);
                        strChoice = getResources().getStringArray(R.array.time4);
                        setArray();
                        break;
                    case 5:
                        imvIshihara.setImageResource(R.drawable.ishihara_05);
                        strChoice = getResources().getStringArray(R.array.time5);
                        setArray();
                        break;
                    case 6:
                        imvIshihara.setImageResource(R.drawable.ishihara_06);
                        strChoice = getResources().getStringArray(R.array.time6);
                        setArray();
                        break;
                    case 7:
                        imvIshihara.setImageResource(R.drawable.ishihara_07);
                        strChoice = getResources().getStringArray(R.array.time7);
                        setArray();
                        break;
                    case 8:
                        imvIshihara.setImageResource(R.drawable.ishihara_08);
                        strChoice = getResources().getStringArray(R.array.time8);
                        setArray();
                        break;
                    case 9:
                        imvIshihara.setImageResource(R.drawable.ishihara_09);
                        strChoice = getResources().getStringArray(R.array.time9);
                        setArray();
                        break;
                    case 10:
                        imvIshihara.setImageResource(R.drawable.ishihara_10);
                        strChoice = getResources().getStringArray(R.array.time10);
                        setArray();
                        break;
                }   // switch

            }   // event
        });


    }   // aboutMyModel

    private void radioController() {

        ragChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                //sound Effect
                MediaPlayer soundRadio = MediaPlayer.create(getBaseContext(), R.raw.effect_btn_shut);
                soundRadio.start();

                //setup intRadioButton
                switch (checkedId) {

                    case R.id.radioButton:
                        intRadioButton = 1;
                        break;
                    case R.id.radioButton2:
                        intRadioButton = 2;
                        break;
                    case R.id.radioButton3:
                        intRadioButton = 3;
                        break;
                    case R.id.radioButton4:
                        intRadioButton = 4;
                        break;
                    default:
                        intRadioButton = 0;
                        break;

                }   // switch

            }   // event
        });


    }   // radioController

    private void buttonController() {

        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayer soundButton = MediaPlayer.create(getBaseContext(), R.raw.effect_btn_long);
                soundButton.start();

                //Check Zero
                if (intRadioButton == 0) {

                    MyAlertDialog objMyAlert = new MyAlertDialog();
                    objMyAlert.myDialog(MainActivity.this, "มีช่องว่าง", "กรุณาตอบคำถาม");

                } else {


                    //Check Score
                    checkScore();


                    //Check Times
                    if (intIndex == 9) {

                        //Show Intent
                        Intent objIntent = new Intent(MainActivity.this, ShowScoreActivity.class);

                        objIntent.putExtra("Score", intScore);

                        startActivity(objIntent);
                        finish();

                    } else {

                        txtQuestion.setText(Integer.toString(intIndex + 2) + ". What is this ?");
                        intIndex += 1;

                        //Show Sent Value to Model
                        objMyModel.setIntButton(intIndex + 1);

                    }   // Check Time

                }  // Check Zero


            }   // event
        });

    }   // buttonController

    private void checkScore() {

        intUserChoose[intIndex] = intRadioButton;
        if (intUserChoose[intIndex] == intAnswerTrue[intIndex]) {
            intScore += 1;
        }


    }   // checkScore

    private void initialWidget() {

        txtQuestion = (TextView) findViewById(R.id.textView2);
        imvIshihara = (ImageView) findViewById(R.id.imageView);
        ragChoice = (RadioGroup) findViewById(R.id.radChoice);
        radChoice1 = (RadioButton) findViewById(R.id.radioButton);
        radChoice2 = (RadioButton) findViewById(R.id.radioButton2);
        radChoice3 = (RadioButton) findViewById(R.id.radioButton3);
        radChoice4 = (RadioButton) findViewById(R.id.radioButton4);
        btnAnswer = (Button) findViewById(R.id.button);


    }   // initialWidget


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemAboutMe:

                //Show WebView
                Intent objIntent = new Intent(Intent.ACTION_VIEW);
                objIntent.setData(Uri.parse("http://androidthai.in.th/about-me.html"));
                startActivity(objIntent);
                break;
            case R.id.itemHowto:

                Intent howIntent = new Intent(MainActivity.this, HowToUserActivity.class);
                startActivity(howIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}   // Main Class
