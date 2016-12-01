package com.chris.illinibus.Services;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.chris.illinibus.Models.Stop;
import com.chris.illinibus.Models.StopEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Database service for Stop table to retrieve stop information
 * Created by Chris on 11/14/16.
 */

public class StopDBService {
    /**
     * Columns for the stops table
     */
    private final static String[] PROJECTION = {
            StopEntry.COLUMN_ID,
            StopEntry.COLUMN_CODE,
            StopEntry.COLUMN_NAME,
            StopEntry.COLUMN_LATITUDE,
            StopEntry.COLUMN_LONGITUDE
    };
    private SQLiteDatabase mDatabase;
    private DBService mDBHelper;

    public StopDBService(Context context) {
        mDBHelper = new DBService(context);
    }

    public void open() throws SQLException {
        mDatabase = mDBHelper.getReadableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    /**
     * Get a list of stops from the database
     *
     * @return
     */
    public List<Stop> getStops() {
        String sortOrder = StopEntry.COLUMN_NAME + " ASC";
        Cursor c = mDatabase.query(StopEntry.TABLE_NAME, PROJECTION, null, null, null, null, sortOrder);

        List<Stop> stopList = new ArrayList<>();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Stop stop = cursorToStop(c);
            stopList.add(stop);
            c.moveToNext();
        }
        c.close();
        return stopList;
    }

    /**
     * Parse data from cursor to a stop object
     * @param c
     * @return
     */
    private Stop cursorToStop(Cursor c) {
        Stop stop = new Stop();
        stop.setId(c.getString(0));
        stop.setCode(c.getString(1));
        stop.setName(c.getString(2));
        stop.setLatitude(c.getDouble(3));
        stop.setLongitude(c.getDouble(4));
        return stop;
    }
}
