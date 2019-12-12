package edisonbro.com.edisonbroautomation.databasewired;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edisonbro.com.edisonbroautomation.Connections.Http_Connect;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariabes_div;

/**
 * Created by Divya on 9/12/2018.
 */

public class LogAdapter extends SQLiteOpenHelper {

    private static SQLiteDatabase dbs=null;
    Context mcontext;
    private static final int DATABASE_VERSION = 3;
    private static final String DBS_NAME ="";
    public static  String TABLE_NAME=StaticVariabes_div.housename+"_Log";


    private static String DBS_PATH=null;

    public static final String IDNO="sno";
    public static final String NAME="hn";
    public static final String VALUE="hna";

    private static final String TAG1="Localdbhelper - ";

    //creating ID's/columns to table
    public static final String ID = "ID";
    public static final String DevTyp = "devtyp";
    public static final String DevNo = "devno";
    public static final String Sw1 = "S1";
    public static final String Sw2 = "S2";
    public static final String Sw3 = "S3";
    public static final String Sw4 = "S4";
    public static final String Sw5 = "S5";
    public static final String Sw6 = "S6";
    public static final String Sw7 = "S7";
    public static final String Sw8 = "S8";
    public static final String Sw9 = "S9";
    public static final String Sw10 ="S10";
    public static final String Sw11 = "S11";
    public static final String Sw12 ="S12";
    public static final String Fn1 ="F1";
    public static final String DateTime= "datetime";


    // private  String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY,"+ROOMNAME+" TEXT ,"+VALUE+" TEXT "+");";

    // String homenam="+ StaticVariabes_div.housename+"";



    private String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY , " +
            DevTyp + " TEXT ," + DevNo + " TEXT ," + Sw1 + " TEXT ," + Sw2 + " TEXT ,"+ Sw3 + " TEXT ,"+ Sw4 + " TEXT ,"
            + Sw5 + " TEXT ,"+ Sw6 + " TEXT ,"+ Sw7 + " TEXT ,"+ Sw8 + " TEXT ,"+ Sw9 + " TEXT ,"+ Sw10 + " TEXT ,"+ Sw11 + " TEXT ,"
            + Sw12 + " TEXT ,"+ Fn1 + " TEXT ,"+ DateTime + " DateTime " + ");";


    public LogAdapter(Context context,String tablename,String DBS_NAME) {
        super(context, DBS_NAME, null, DATABASE_VERSION);
        TABLE_NAME=tablename;
        DBS_NAME=DBS_NAME;
        mcontext=context;
        DBS_PATH="/data/data/"+mcontext.getPackageName()+"/databases/";

        // StaticVariabes_div.log("Local DATABASE"+"constructor Called", TAG1);

    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {

        sdb.execSQL(CREATE_TABLE);

        StaticVariabes_div.log("Local DATABASE Created", TAG1);

    }



    @Override
    public void onUpgrade(SQLiteDatabase sdb, int oldVersion, int newVersion) {
        sdb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(dbs);
    }


    public void open() {
        try
        {
            // String DB_PATH = DBS_PATH+ DBS_NAME;
            dbs = this.getWritableDatabase();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public boolean isDbExists() throws IOException {
        SQLiteDatabase sldb = null;
        try {
            String DB_PATH = DBS_PATH+ DBS_NAME;
            sldb = SQLiteDatabase.openDatabase(DB_PATH, null,
                    SQLiteDatabase.OPEN_READWRITE
                            + SQLiteDatabase.NO_LOCALIZED_COLLATORS);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sldb != null)
            sldb.close();

        return sldb != null ? true : false;

    }


    public void close() {
        try {
            if (dbs != null) {
                dbs.close();
                StaticVariabes_div.log("Local DATABASE"+"closed", TAG1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public  boolean insertData(String devTyp, String devno,String S1,String S2,String S3,String S4,String S5
            ,String S6 ,String S7,String S8,String S9 ,String S10,String S11,String S12,String F1,String dateTime) {

        StaticVariabes_div.log("data insert " + devTyp +"  "+ devno+"  "+ S1+"  "+S2+"  "+S3+"  "+S4+"  "+ S5

                +"  "+ S6 +"  "+ S7+"  "+ S8+"  "+ S9 +"  "+S10+"  "+S11+"  "+S12+"  "+F1+"  "+dateTime, TAG1);


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        //  contentValues1.put(ID, id);
        contentValues1.put(DevTyp, devTyp);
        contentValues1.put(DevNo, devno);
        contentValues1.put(Sw1, S1);
        contentValues1.put(Sw2, S2);
        contentValues1.put(Sw3, S3);
        contentValues1.put(Sw4, S4);
        contentValues1.put(Sw5, S5);
        contentValues1.put(Sw6, S6);
        contentValues1.put(Sw7, S7);
        contentValues1.put(Sw8, S8);
        contentValues1.put(Sw9, S9);
        contentValues1.put(Sw10, S10);
        contentValues1.put(Sw11, S11);
        contentValues1.put(Sw12, S12);
        contentValues1.put(Fn1, F1);
        contentValues1.put(DateTime, dateTime);

        //  long result = db.insertWithOnConflict(TABLE_NAME, null, contentValues1,SQLiteDatabase.CONFLICT_REPLACE);
        boolean result = db.insert(TABLE_NAME, null, contentValues1)>0;
        return  result;
        /*if (result == -1)
            return false;
        else
            return true;*/
    }

    //getting number of rows from table
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,TABLE_NAME);
        return numRows;
    }
    //getting data based on roomnames
/*    public List<String> getData1() {
        List<String> arr=new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + StaticVariabes_div.housename +" ORDER BY " + POSITIONNO + " ASC",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(ROOMNAME));

                arr.add(name);
                cursor.moveToNext();
            }
        }

        return arr;
    }*/

    //ORDER BY " + SQLiteHelper.listDate+ " ASC"

   //getting data based on roomnames select * from User ORDER BY date(date_of_birth) asc SELECT * FROM Table1 ORDER BY date_column DESC LIMIT 1
    public String getdata_devno(String devn) {
        List<String> arrs1=new ArrayList<String>();
        List<String> arrs2=new ArrayList<String>();
        List<String> arrs3=new ArrayList<String>();
        List<String> arrs4=new ArrayList<String>();
        List<String> arrs5=new ArrayList<String>();
        List<String> arrs6=new ArrayList<String>();
        List<String> arrs7=new ArrayList<String>();
        List<String> arrs8=new ArrayList<String>();
        List<String> arrdate=new ArrayList<String>();


        Http_Connect.S1db=null;
        Http_Connect.S2db=null;
        Http_Connect.S3db=null;
        Http_Connect.S4db=null;
        Http_Connect.S5db=null;
        Http_Connect.S6db=null;
        Http_Connect.S7db=null;
        Http_Connect.S8db=null;

        SQLiteDatabase db = this.getReadableDatabase();
        devn="'"+devn+"'";
        Cursor  cursor = db.rawQuery("select * from " + TABLE_NAME +"  WHERE  devno ="+devn+"  ORDER BY datetime(dateTime)" ,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String dte = cursor.getString(cursor.getColumnIndex(DateTime));
                String s1dt = cursor.getString(cursor.getColumnIndex(Sw1));
                String s2dt = cursor.getString(cursor.getColumnIndex(Sw2));
                String s3dt = cursor.getString(cursor.getColumnIndex(Sw3));
                String s4dt = cursor.getString(cursor.getColumnIndex(Sw4));
                String s5dt = cursor.getString(cursor.getColumnIndex(Sw5));
                String s6dt = cursor.getString(cursor.getColumnIndex(Sw6));
                String s7dt = cursor.getString(cursor.getColumnIndex(Sw7));
                String s8dt = cursor.getString(cursor.getColumnIndex(Sw8));
                arrdate.add(dte);
                arrs1.add(s1dt);
                arrs2.add(s2dt);
                arrs3.add(s3dt);
                arrs4.add(s4dt);
                arrs5.add(s5dt);
                arrs6.add(s6dt);
                arrs7.add(s7dt);
                arrs8.add(s8dt);
                cursor.moveToNext();
            }
        }

        Http_Connect.dtedb=arrdate;
        Http_Connect.S1db=arrs1;
        Http_Connect.S2db=arrs2;
        Http_Connect.S3db=arrs3;
        Http_Connect.S4db=arrs4;
        Http_Connect.S5db=arrs5;
        Http_Connect.S6db=arrs6;
        Http_Connect.S7db=arrs7;
        Http_Connect.S8db=arrs8;

        return "true";
    }


    public String getdistinct_devno() {
        List<String> arrdevtt=new ArrayList<String>();
        List<String> arrdytt=new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor  cursor = db.rawQuery("select DISTINCT(devno) from " + TABLE_NAME ,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String devnt = cursor.getString(cursor.getColumnIndex(DevNo));
                arrdevtt.add(devnt);
                cursor.moveToNext();
            }
        }

        Http_Connect.Stat_devn_arr=arrdevtt;

        return "true";
    }


    public String get_devtype(String devn) {

        List<String> arrdytt=new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        devn="'"+devn+"'";
        Cursor  cursor = db.rawQuery("select DISTINCT(devtyp) from " + TABLE_NAME +" WHERE  devno ="+devn ,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String dytt = cursor.getString(cursor.getColumnIndex(DevTyp));
                StaticVariabes_div.log(devn+"  dytt  "+dytt, TAG1);
                arrdytt.add(dytt);
                cursor.moveToNext();
            }
        }

        Http_Connect.Stat_dytt_arr=arrdytt;

        return "true";
    }


    public String get_devtype_single(String devn) {

        String dytt=null;

        SQLiteDatabase db = this.getReadableDatabase();
        devn="'"+devn+"'";
        Cursor  cursor = db.rawQuery("select DISTINCT(devtyp) from " + TABLE_NAME +" WHERE  devno ="+devn ,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                dytt = cursor.getString(cursor.getColumnIndex(DevTyp));
                StaticVariabes_div.log(devn+"  dytt  "+dytt, TAG1);
                cursor.moveToNext();
            }
        }



        return dytt;
    }


    public boolean deleteall(){

        SQLiteDatabase db= this.getWritableDatabase();
        boolean del_table=db.delete(TABLE_NAME, null, null)>0;
        return del_table;
    }


}

