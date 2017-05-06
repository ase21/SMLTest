package ru.startandroid.smltest;

/**
 * Created by ase911 on 05.05.2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DB {

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "mytab";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PERCENT = "img";
    public static final String COLUMN_TXT = "txt";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_PERCENT + " text, " +
                    COLUMN_TXT + " text" +
                    ");";
    final String LOG_TAG = "mylogs";

    private final Context mCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    // получить из таблицы DB_TABLE выборку по id
    public Cursor getOneString(String position) {
        String selection = COLUMN_ID + " = " +position;
        return mDB.query(DB_TABLE, null, selection, null, null, null, null);
    }

    public Cursor getModifyString() {
        String selection = COLUMN_TXT + " > 0";
        String sort = COLUMN_TXT+" DESC";
        return mDB.query(DB_TABLE, null, selection, null, null, null, sort);
    }

    //вносим изменения в строку
    public void upDate(String str, String id){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddhhmm");
        String strTime = simpleDateFormat.format(new Date());
        Log.d(LOG_TAG, "--- Update in mytable: ---");
        String selection = COLUMN_ID+" = "+id;
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PERCENT,str);
        cv.put(COLUMN_TXT,strTime);
        int updCount = mDB.update(DB_TABLE, cv, selection,null);
        Log.d(LOG_TAG, "String "+updCount+"updated");
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);

            ContentValues cv = new ContentValues();
            for (int i = 1; i < 101; i++) {
                cv.put(COLUMN_TXT, 0);
                cv.put(COLUMN_PERCENT, "0%");
                db.insert(DB_TABLE, null, cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
}
