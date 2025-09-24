package com.Adv.developer.instantmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "advikon.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_Credential= "chkcredential";
    public static final String COLUMN_ID = "_id";


    /**
     * //TODO:Playlist column tables
     */
    public static final String COLUMN_Username = "username";
    public static final String COLUMN_Password = "password";


    private static final String DATABASE_CREATE_Credential = "create table "
            + TABLE_Credential + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            +  COLUMN_Username
            + " text not null, " + COLUMN_Password
            + " text not null);";



    private static MySQLiteHelper instance = null;

    public static MySQLiteHelper getInstance(Context context){

        if (instance == null){
            instance = new MySQLiteHelper(context);
        }

        return instance;
    }

    public MySQLiteHelper(Context context) {

        super(context, context.getApplicationInfo().dataDir
                + File.separator + "Advikon"
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_Credential);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

//

        if (oldVersion == 1 && newVersion == 2)
        {

        }



    }

    public ArrayList<String> getCredential() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> data = new ArrayList<String>();
        String query = "SELECT  * FROM " + MySQLiteHelper.TABLE_Credential;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String user = cursor.getString(1);   //0 is the number of id column in your database table
            String pass = cursor.getString(2);
            data.add(user);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        db.close();
        return data;
    }

    public void deletecredential()
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(MySQLiteHelper.TABLE_Credential, null, null);
        }
        catch(Exception e)
        {
            e.getCause();
        }

    }

    public boolean insertcredential(String user,String pass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_Username, user);
        values.put(MySQLiteHelper.COLUMN_Password, pass);
        long insertId = db.insert(MySQLiteHelper.TABLE_Credential, null,
                values);

        if(insertId==-1)
        {
            db.close();
            return  false;
        }
        else
        {
            db.close();
            return true;

        }


    }




}