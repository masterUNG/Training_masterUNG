package appewtc.masterung.myvideo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by masterUNG on 1/14/15 AD.
 */
public class MyAlertDalog {

    private AlertDialog.Builder objAlert;

    public void errorDialog(Context context, String strTitle, String strMessage) {

        objAlert = new AlertDialog.Builder(context);
        objAlert.setIcon(R.drawable.danger);
        objAlert.setTitle(strTitle);
        objAlert.setMessage(strMessage);
        objAlert.setCancelable(false);
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        objAlert.show();

    }   // errorDialog

}   // MainClass
