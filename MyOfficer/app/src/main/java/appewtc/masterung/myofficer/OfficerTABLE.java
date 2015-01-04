package appewtc.masterung.myofficer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 1/3/15 AD.
 */
public class OfficerTABLE {

    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String TABLE_OFFICER = "officerTABLE";
    public static final String COLUMN_ID_OFFICER = "_id";
    public static final String COLUMN_OFFICER = "Officer";
    public static final String COLUMN_POSITION = "Position";
    public static final String COLUMN_IMAGE = "Image";
    public static final String COLUMN_VIDEO = "Video";
    public static final String COLUMN_SOUND = "Sound";

    public OfficerTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        readSQLite = objMyOpenHelper.getReadableDatabase();

    }   // Constructor


    //Read All Data
    public Cursor readAllData() {

        Cursor objCursor = readSQLite.query(TABLE_OFFICER, new String[]{COLUMN_ID_OFFICER, COLUMN_OFFICER, COLUMN_POSITION, COLUMN_IMAGE, COLUMN_VIDEO, COLUMN_SOUND}, null, null, null, null, null);

        if (objCursor != null) {
            objCursor.moveToFirst();
        }

        return objCursor;
    }   // readAllData


    public long addRecordOfficerTable(Context context, String strOfficer, String strPosition, String strImage, String strVideo, String strSound) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_OFFICER, strOfficer);
        objContentValues.put(COLUMN_POSITION, strPosition);
        objContentValues.put(COLUMN_IMAGE, strImage);
        objContentValues.put(COLUMN_VIDEO, strVideo);
        objContentValues.put(COLUMN_SOUND, strSound);

        return writeSQLite.insert(TABLE_OFFICER, null, objContentValues);
    }   // addRecordOfficerTable

}   // Main Class
