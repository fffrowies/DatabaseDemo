package com.fffrowies.databasedemo;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "EMPLOYEES";

    // Table columns
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";

    // Database Information
    static final String DB_NAME = "myEmployees.DB";

    // Database Version
    static final int DB_VERSION = 1;

    private SQLiteDatabase database;

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL, " + ADDRESS
            + " CHAR(50));";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }

    public void close() {
        database.close();
    }
}
