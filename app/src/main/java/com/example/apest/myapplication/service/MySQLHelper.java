package com.example.apest.myapplication.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by apest on 09/12/2016.
 */

public class MySQLHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "mydb.db";
    private static final int DB_VERSION = 2;

    public static final String PET_TYPE_TABLE_NAME = "PetType";
    public static final String PET_TYPE_ID = "id";
    public static final String PET_TYPE_NAME = "name";
    public static final String CREATE_PET_TYPE = "" +
            "CREATE TABLE " + PET_TYPE_TABLE_NAME + " ("+
            PET_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PET_TYPE_NAME + " TEXT NOT NULL)";

    public static final String INIT_PET_TYPE = "" +
            "INSERT INTO " + PET_TYPE_TABLE_NAME +
            "(" + PET_TYPE_NAME + ") VALUES " +
            "('parrot'),('dog'),('cat'),('unknown')";



    public static final String PET_TABLE_NAME = "Pet";
    public static final String PET_ID = "petId";
    public static final String PET_NAME = "petName";
    public static final String PET_AGE = "petAge";
    public static final String FK_PET_TYPE = "fkPetType";

    public static final String CREATE_PET = ""+
            "CREATE TABLE " + PET_TABLE_NAME + " (" +
            PET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PET_NAME + " TEXT NOT NULL, " +
            PET_AGE + " INTEGER NOT NULL, " +
            FK_PET_TYPE + " INTEGER NOT NULL)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public MySQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PET_TYPE);
        db.execSQL(INIT_PET_TYPE);
        db.execSQL(CREATE_PET);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE + PET_TYPE_TABLE_NAME);
        db.execSQL(DROP_TABLE + PET_TABLE_NAME);
        onCreate(db);
    }
}
