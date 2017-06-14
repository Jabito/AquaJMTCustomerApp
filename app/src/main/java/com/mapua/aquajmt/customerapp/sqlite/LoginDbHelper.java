package com.mapua.aquajmt.customerapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mapua.aquajmt.customerapp.api.models.CustomerInfo;
import com.mapua.aquajmt.customerapp.sqlite.LoginContract.LoginEntry;

/**
 * Created by Bryan on 6/14/2017.
 */

public class LoginDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Login.db";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LoginEntry.TABLE_NAME + " (" +
                    LoginEntry._ID + " INTEGER PRIMARY KEY, " +
                    LoginEntry.COLUMN_NAME_CUST_ID + " TEXT," +
                    LoginEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    LoginEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    LoginEntry.COLUMN_NAME_FIRST_NAME + " TEXT," +
                    LoginEntry.COLUMN_NAME_LAST_NAME + " TEXT," +
                    LoginEntry.COLUMN_NAME_CELLPHONE_NO + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LoginEntry.TABLE_NAME;

    public LoginDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static long save(Context context, CustomerInfo customerInfo) {
        LoginDbHelper dbHelper = new LoginDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LoginEntry.COLUMN_NAME_CUST_ID, customerInfo.getId());
        values.put(LoginEntry.COLUMN_NAME_USERNAME, customerInfo.getUsername());
        values.put(LoginEntry.COLUMN_NAME_EMAIL, customerInfo.getEmail());
        values.put(LoginEntry.COLUMN_NAME_FIRST_NAME, customerInfo.getFirstName());
        values.put(LoginEntry.COLUMN_NAME_LAST_NAME, customerInfo.getLastName());
        values.put(LoginEntry.COLUMN_NAME_CELLPHONE_NO, customerInfo.getCellphoneNo());

        return db.insert(LoginEntry.TABLE_NAME, null, values);
    }

    public static CustomerInfo getTop1(Context context) {
        LoginDbHelper dbHelper = new LoginDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                LoginEntry.COLUMN_NAME_CUST_ID,
                LoginEntry.COLUMN_NAME_USERNAME,
                LoginEntry.COLUMN_NAME_EMAIL,
                LoginEntry.COLUMN_NAME_FIRST_NAME,
                LoginEntry.COLUMN_NAME_LAST_NAME,
                LoginEntry.COLUMN_NAME_CELLPHONE_NO
        };

        CustomerInfo customerInfo;
        Cursor cursor = db.query(LoginEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (cursor.moveToNext()) {
            customerInfo = new CustomerInfo();
            customerInfo.setId(cursor.getString(
                    cursor.getColumnIndexOrThrow(LoginEntry.COLUMN_NAME_CUST_ID)));
            customerInfo.setUsername(cursor.getString(
                    cursor.getColumnIndexOrThrow(LoginEntry.COLUMN_NAME_USERNAME)));
            customerInfo.setEmail(cursor.getString(
                    cursor.getColumnIndexOrThrow(LoginEntry.COLUMN_NAME_EMAIL)));
            customerInfo.setFirstName(cursor.getString(
                    cursor.getColumnIndexOrThrow(LoginEntry.COLUMN_NAME_FIRST_NAME)));
            customerInfo.setLastName(cursor.getString(
                    cursor.getColumnIndexOrThrow(LoginEntry.COLUMN_NAME_LAST_NAME)));
            customerInfo.setCellphoneNo(cursor.getString(
                    cursor.getColumnIndexOrThrow(LoginEntry.COLUMN_NAME_CELLPHONE_NO)));
        } else {
            customerInfo = null;
        }

        cursor.close();
        return customerInfo;
    }

    public static void removeAll(Context context) {
        LoginDbHelper dbHelper = new LoginDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(LoginEntry.TABLE_NAME, null, null);
    }
}
