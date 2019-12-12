package edisonbro.com.edisonbroautomation.databasewired;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;


public class SQLiteHelper extends SQLiteOpenHelper {

    // DATABASE NAME //
    private static final String DATABASE_NAME = "tempsample.db";
    // TABLE NAMES //
    private static final String TEMP = "TEMP";

    // TABLE ONE //
    // TEMPLATE_FIELDS CULLUM ENTRIES //
    private static final HashMap<String, String> TEMPLATE_FIELDS = new HashMap<String, String>() {
        {
            put("a", "TEXT PRIMARY KEY");
        }
    };

    // SQLiteHelper CONSTUCTOR //
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            createTable(sqLiteDatabase, TEMP, TEMPLATE_FIELDS);               // CREATEING TEMPLATETEMPLATE TABLE
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Create Table", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

       /* if (oldVersion < 2) {
            try {
                sqLiteDatabase.execSQL(DATABASE_COMMAND1);
            }catch (SQLException e){
                Log.e("SQLException", e.getMessage());
            }
        }
        if (oldVersion < 3) {
            try {
                sqLiteDatabase.execSQL(DATABASE_COMMAND2);
            }catch (SQLException e){
                Log.e("SQLException", e.getMessage());
            }
        }*/

    }
    // THIS FUNCTION DOES ALL THE TABLE CREATION WORK
    static public void createTable(SQLiteDatabase db, String tableName, HashMap<String, String> fields) {
        String command = "CREATE TABLE " + tableName + " ( S_NO INTEGER DEFAULT 1";

        for (Map.Entry<String, String> entry : fields.entrySet())
            command = command + ", " + entry.getKey() + " " + entry.getValue();

        command = command + " )";

        try {
            db.execSQL(command);
        } catch (SQLException e) {
            Log.e("SQLException", e.getMessage());
        }
    }

  /*  public boolean deleteSCHEDULES(int S_NO)
    {
        SQLiteDatabase db = this.getWritableDatabase();
      //  boolean result=db.delete(SCHEDULES,  "S_NO=" + S_NO, null) > 0;
        return result;
    }*/




    // Deleting all records from EMPLOYEEDETAILS table //
    public void deleteAll() {
        //SQLiteHelper helper = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbdel = this.getWritableDatabase();
       // dbdel.delete(EMPLOYEEDETAILS,null,null);

    }

    // Getting total number of employee's from EMPLOYEEDETAILS table //
    public  long counttemp(String a){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCount= db.rawQuery("select count(*) from TEMP where a='" + a + "'", null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }


    // addded for genersting Template from morpho device and storing here //
    public boolean Inserttemp(String shiftarraylist) {
        //SQLiteHelper helper = new SQLiteHelper(getApplicationContext());
        boolean result = false ;
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        String sql = "INSERT INTO " + "TEMP" + "(S_NO,a)" + " VALUES((SELECT IFNULL(MAX(S_NO), 0) + 1 FROM TEMP),?)";

        SQLiteStatement insert = null;

        try {

            insert = db.compileStatement(sql);

            //for (HashMap<String, String> map : shiftarraylist) {

                String a = shiftarraylist;

                insert.bindString(1, a);

                insert.execute();
                insert.clearBindings();
          //  }
            db.setTransactionSuccessful();
            result = true ;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Transaction Insertion", e.getMessage());

            result = false;
        } finally {

            db.endTransaction();
            db.close();
        }
        return result;
    }

}