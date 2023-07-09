package xuan.code;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class quanlycauhoi extends SQLiteOpenHelper {
    TextView txt_1;
    private static final String DB_PATH = "/data/data/xuan.code/databases/";
    private static final String DB_NAME = "tracnghiemdata.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tb_cauhoi";
    private static final String KEY_ID = "_id";
    private static final String KEY_CAUHOI = "cauhoi";
    private static final String KEY_A = "cau_a";
    private static final String KEY_B = "cau_b";
    private static final String KEY_C = "cau_c";
    private static final String KEY_D = "cau_d";
    private static final String KEY_DA = "dap_an";
    private SQLiteDatabase myDatabase;
    private final Context myContext;

    public quanlycauhoi(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (myDatabase != null) {
            myDatabase.close();
        }
        super.close();
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            // Database doesn't exist
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            // Database already exists
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }
    public List<cauhoi> layngaunhien(int socau){
        List<cauhoi> dscauhoi = new ArrayList<cauhoi>();
        String limit="0, "+socau;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor contro=db.query(TABLE_NAME,null,null,null,null,null,"random()",limit);
        contro.moveToFirst();
        do{
            cauhoi x = new cauhoi();
            x._id=Integer.parseInt(contro.getString(0));
            x.cauhoi=contro.getString(1);
            x.cau_a=contro.getString(2);
            x.cau_b=contro.getString(3);
            x.cau_c=contro.getString(4);
            x.cau_d=contro.getString(5);
            x.dap_an=contro.getString(6);
            dscauhoi.add(x);
        }while (contro.moveToNext());
        return dscauhoi;
    }
    public Cursor laytacacauhoi() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No need to implement this method if you're using a pre-existing database
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if needed
    }
}
