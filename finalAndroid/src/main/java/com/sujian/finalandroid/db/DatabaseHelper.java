package com.sujian.finalandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Sujian  121116111@QQ.COM
 * @ClassName: DatabaseHelper
 * @Description: TODO(数据库帮助类)
 * @date 2016年3月23日 下午9:21:28
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * 数据库版本
     */
    private static final int VERSION = 1;
    /**
     * 数据库名字
     */
    private static final String DATANAME = "convenient.db";

    public DatabaseHelper(Context context) {
        super(context, DATANAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE \"user\" (\"user_id\"  integer PRIMARY KEY AUTOINCREMENT NOT NULL,\"account\"  varchar(20) NOT NULL,\"username\"  varchar(20) ,\"imguri\" varchar(40) ,\"password\"  varchar(20) NOT NULL)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

}
