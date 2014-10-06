package com.jwilliams.machinistmate.app.Adapters;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by John on 4/23/2014.
 */


public class DbHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.jwilliams.machinistmate.app/databases/";
    private static String DB_NAME = "machinist_mate_db";
    private static String TABLE_NAME = "drillsize";
    private static String SIZE = "size";
    private static String STANDARD = "standard";
    private static String METRIC_COLUMN = "metric";
    private static String METRIC = "'metric'";
    private static String LETTER = "'letter'";
    private static String WIREGAUGE = "'wiregauge'";
    private static String FRACTION = "'fraction'";
    private static String TYPE = "type";
    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
            Log.d("Database ", "exists");
        }

        //dbExist = checkDataBase();

        if(!dbExist){
            Log.d("Database ", "doesn't exist");
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            Log.d("Creating new db", "");
            this.getReadableDatabase();

            try {
                Log.d("Copying data into ", "new database");
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Copy ", "Database");
                throw new Error("Error copying database");
            }
        }
        close();
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
            e.printStackTrace();
        }

        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase()  {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        try {
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        myContext.deleteDatabase(DB_NAME);
    }

    /**
     * DrillChart db queries
     * */
    public Cursor byWireGauge(){
        return myDataBase.rawQuery("select "+ SIZE + "," + STANDARD + ", "
                + METRIC_COLUMN +" from " + TABLE_NAME + " where " + TYPE + " = " + WIREGAUGE,null);
    }

    public Cursor byLetter(){
        return myDataBase.rawQuery("select "+ SIZE + "," + STANDARD + ", "
                + METRIC_COLUMN +" from " + TABLE_NAME + " where " + TYPE + " = " + LETTER,null);
    }

    public Cursor byMetric(){
        return myDataBase.rawQuery("select "+ SIZE + "," + STANDARD + ", "
                + METRIC_COLUMN +" from " + TABLE_NAME + " where " + TYPE + " = " + METRIC,null);
    }

    public Cursor byFraction(){
        return myDataBase.rawQuery("select "+ SIZE + "," + STANDARD + ", "
                + METRIC_COLUMN +" from " + TABLE_NAME + " where " + TYPE + " = " + FRACTION,null);
    }

    public Cursor byAll(){
        return myDataBase.rawQuery("select "+ SIZE + "," + STANDARD + ", "
                + METRIC_COLUMN +" from " + TABLE_NAME,null);
    }

    /**
     * Length db query
     * */
    public Cursor getLengthConversionFactor(int id, String output){
        return myDataBase.rawQuery("select "+ output + " from "
                + "length" + " Where _id = " + id,null);
    }

    /**
     * Volume db query
     * */
    public Cursor getVolumeConversionFactor(int id, String output){
        Log.d(output, Integer.toString(id));
        return myDataBase.rawQuery("select "+ output + " from "
                + "volume" + " Where _id = " + id,null);
    }

    /**
     *
     *CNC Codes queries
     */

    //static String g = "g";
    public Cursor getGCodes(){
        return myDataBase.rawQuery("select code, desc, mill, turn from cncc where type = 'g'",null);
    }

    //static String m = "m";
    public Cursor getMCodes(){
        return myDataBase.rawQuery("select code, desc, mill, turn from cncc where type = 'm'",null);
    }

    //static String add = "add";
    public Cursor getAddCodes(){
        return myDataBase.rawQuery("select code, desc, mill, turn from cncc where type = 'add'",null);
    }

}

