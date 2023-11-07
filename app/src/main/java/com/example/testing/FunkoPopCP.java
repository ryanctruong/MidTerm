package com.example.testing;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FunkoPopCP extends ContentProvider {
    public static final String DB_NAME = "FunkoDB";
    public static final String TABLE_NAME = "FunkoPOPs";
    public static final String COL1_NAME = "Name";
    public static final String COL2_NAME = "Number";
    public static final String COL3_NAME = "Type";
    public static final String COL4_NAME = "Fandom";
    public static final String COL5_NAME = "b_On";
    public static final String COL6_NAME = "Ultimate";
    public static final String COL7_NAME = "Price";
    public static final String AUTHORITY = "com.testing.FunkoPopCP";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DB_NAME);

    private static final String CREATE_URI_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "( _id INTEGER PRIMARY KEY, " +
            COL1_NAME + " TEXT, " +
            COL2_NAME + " INTEGER, " +
            COL3_NAME + " TEXT, " +
            COL4_NAME + " TEXT, " +
            COL5_NAME + " BOOLEAN, " +
            COL6_NAME + " TEXT, " +
            COL7_NAME + " DOUBLE )"
            ;

    protected static final class MainDBHelper extends SQLiteOpenHelper {

        public MainDBHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_URI_QUERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //
        }
    }

    private MainDBHelper SQLHelper;

    public FunkoPopCP() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        return SQLHelper.getWritableDatabase().delete(TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = SQLHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
        return Uri.withAppendedPath(CONTENT_URI, "" + id);
    }

    @Override
    public boolean onCreate() {
        SQLHelper = new MainDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return SQLHelper.getWritableDatabase().query(TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return SQLHelper.getWritableDatabase().update(TABLE_NAME, values, selection, selectionArgs);
    }
}