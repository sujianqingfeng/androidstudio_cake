package com.sujian.finalandroid.uitls;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

import java.io.File;

/**
 * 本地数据库工具类
 * Created by 12111 on 2016/5/12.
 */
public class DataBaseUitls {

    /**
     * 得到一个数据库 管理者
     *
     * @param databasename 数据库名
     * @param version      数据库版本
     * @return
     */
    public static DbManager getDB(String databasename, int version) {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName(databasename)
                .setDbVersion(version)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
                })
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbDir(new File("/sdcard/finalandroid/"))//数据库存放的位置 不写则存在应用中
                .setAllowTransaction(true)
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {
                        //db.getDatabase().
                    }
                });
        DbManager db = x.getDb(daoConfig);
        return db;
    }
}
