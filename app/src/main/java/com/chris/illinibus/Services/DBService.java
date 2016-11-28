package com.chris.illinibus.Services;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Connect to SQLite android local database
 * Created by Chris on 11/5/16.
 */

public class DBService extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public DBService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
