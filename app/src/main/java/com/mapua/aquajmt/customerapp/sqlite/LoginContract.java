package com.mapua.aquajmt.customerapp.sqlite;

import android.provider.BaseColumns;

/**
 * Created by Bryan on 6/14/2017.
 */

public final class LoginContract {

    private LoginContract() { }

    public static class LoginEntry implements BaseColumns {
        public static final String TABLE_NAME = "login_table";
        public static final String COLUMN_NAME_CUST_ID = "cust_id";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_CELLPHONE_NO = "cellphone_no";
    }
}
