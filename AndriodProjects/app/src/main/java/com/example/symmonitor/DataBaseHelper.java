package com.example.symmonitor;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String COLUMN_USER_NAME = "USER_NAME";
//    public static final String COLUMN_READINGS_KEY = "READINGS_KEY";
//    public static final String COLUMN_READINGS_VALUE = "READINGS_VALUE";
    public static final String DIARRHEA = "DIARRHEA";
    public static final String SOAR_THROAT = "SOAR_THROAT";
    public static final String COUGH = "COUGH";
    public static final String USER_TABLE = "READINGS_TABLE";
    public static final String RESP_RATE = "RESPIRATION_RATE";
    public static final String HEART_RATE = "HEART_RATE";
    public static final String FEVER = "FEVER";
    public static final String MUSCLE_ACHE = "MUSCLE_ACHE";
    public static final String NO_SMELL_TASTE = "NO_SMELL_TASTE";
    public static final String NAUSEA = "NAUSEA";
    public static final String HEAD_ACHE = "HEAD_ACHE";
    public static final String SHORT_BREATH = "SHORT_BREATH";
    public static final String FEEL_TIRED = "FEEL_TIRED";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "symreadings.db", null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + HEART_RATE + " INTEGER, " + RESP_RATE + " INTEGER, "+ NAUSEA + " INTEGER, "+ HEAD_ACHE + " INTEGER, "+ DIARRHEA + " INTEGER, " + SOAR_THROAT + " INTEGER, " + FEVER + " INTEGER, "+ MUSCLE_ACHE + " INTEGER, "+ NO_SMELL_TASTE + " INTEGER, " + COUGH + " INTEGER, "+ SHORT_BREATH + " INTEGER, "+ FEEL_TIRED + " INTEGER" + " )";
        db.execSQL(createTableStatement);
    }

    public boolean onInsert(Readings readings){
        SQLiteDatabase database =this.getWritableDatabase();
        ContentValues CVR = new ContentValues();
        CVR.put(COLUMN_USER_NAME, "NIKHIL");
//        CVR.put(COLUMN_READINGS_KEY, readings.reading_key);
//        CVR.put(COLUMN_READINGS_VALUE, readings.reading_value);
        CVR.put(HEART_RATE, readings.HEART_RATE);
        CVR.put(RESP_RATE, readings.RESP_RATE);
        CVR.put(NAUSEA, readings.NAUSEA);
        CVR.put(HEAD_ACHE, readings.HEAD_ACHE);
        CVR.put(DIARRHEA, readings.DIARRHEA);
        CVR.put(SOAR_THROAT, readings.SOAR_THROAT);
        CVR.put(FEVER, readings.FEVER);
        CVR.put(MUSCLE_ACHE, readings.MUSCLE_ACHE);
        CVR.put(NO_SMELL_TASTE, readings.NO_SMELL_TASTE);
        CVR.put(COUGH, readings.COUGH);
        CVR.put(SHORT_BREATH, readings.SHORT_BREATH);
        CVR.put(FEEL_TIRED, readings.FEEL_TIRED);
        long insert = database.insert(USER_TABLE, null, CVR);
        if(insert== -1){
            return false;
        }
        else{
            return true;
        }
    }
}
