package appewtc.masterung.botetraining;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private MainActivity mActivity;

    public MainActivityTest() {
        super(MainActivity.class);

    } // MainActivityTest

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);
        mActivity = getActivity();

    } //setUp

    public void testHelloWorld() {

        final TextView t = (TextView) mActivity.findViewById(R.id.txtHello);
        assertEquals("Hello master", t.getText().toString());

    }   // testHelloWorld


}   // Main Class