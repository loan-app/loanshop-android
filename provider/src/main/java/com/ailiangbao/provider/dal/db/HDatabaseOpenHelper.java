package com.ailiangbao.provider.dal.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wangjie.rapidorm.core.connection.DatabaseProcessor;
import com.wangjie.rapidorm.core.delegate.database.RapidORMDefaultSQLiteDatabaseDelegate;
import com.wangjie.rapidorm.core.delegate.database.RapidORMSQLiteDatabaseDelegate;

/**
 * Created by cq on 2018/5/14
 */
public class HDatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String TAG = HDatabaseOpenHelper.class.getSimpleName();

    private static final int VERSION_V1_0_0 = 1;
    private static final int VERSION_V1_0_3 = 3;
    private static final int VERSION_V1_1=4;

    private static final int VERSION_CURRENT = VERSION_V1_1;

    private RapidORMSQLiteDatabaseDelegate dbDelegate;

    public HDatabaseOpenHelper(Context context, String name) {
        this(context, name, VERSION_CURRENT);
    }

    public HDatabaseOpenHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public HDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public HDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbDelegate = new RapidORMDefaultSQLiteDatabaseDelegate(db);
        DatabaseProcessor databaseProcessor = DatabaseProcessor.getInstance();
        databaseProcessor.initializeDatabase(dbDelegate);

        DatabaseProcessor.getInstance().createAllTable(dbDelegate, true);
        Log.d(TAG, "数据库表创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dbDelegate = new RapidORMDefaultSQLiteDatabaseDelegate(db);
        DatabaseProcessor databaseProcessor = DatabaseProcessor.getInstance();
        databaseProcessor.initializeDatabase(dbDelegate);
//
//        if (newVersion >= VERSION_V1_0_2) {
//            try {
//                dbDelegate.execSQL("ALTER table `userInfo` add column `" + UserInfoEntity_RORM.ACCOUNTNO + "` long");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        if (newVersion>=VERSION_V1_0_3){
//            try {
//                databaseProcessor.dropAllTable(dbDelegate);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }

        onCreate(db);
    }


}
