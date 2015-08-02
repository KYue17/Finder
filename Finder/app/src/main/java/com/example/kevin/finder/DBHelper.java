package com.example.kevin.finder;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;

/**
 * Created by Kevin on 6/11/2015.
 */
public class DBHelper extends SQLiteOpenHelper{

    //Database Name
    public static final String DATABASE_NAME = "PersonManager";

    //Database Version
    public static final int DATABASE_VERSION = 1;

    //Name of table
    public static final String TABLE_TITLE = "People";

    //Columns of table
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";

    public static final String DATABASE_CREATE = "Create Table " + TABLE_TITLE + "("
            + COLUMN_ID + "Integer Key ID" + COLUMN_NAME + "Text" + COLUMN_AGE + "Text" + ")";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Called when database is created
    public void onCreate(SQLiteDatabase sqldb){
        sqldb.execSQL(DATABASE_CREATE);
    }

    //Called when database is updated (modifying table structure, adding constraints, etc.)
    public void onUpgrade(SQLiteDatabase sqldb, int oldVersion, int newVersion){
        sqldb.execSQL("Drop table if exists " + TABLE_TITLE);
        onCreate(sqldb);
    }

    //Add person to database
    public void addPerson(Person p){
        SQLiteDatabase sqldb = this.getWritableDatabase();


    }
}
