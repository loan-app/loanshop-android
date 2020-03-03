package com.ailiangbao.provider.dal.db;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity;
import com.ailiangbao.provider.dal.net.http.entity.account.UserStatusEntity;
import com.ailiangbao.provider.bll.application.ProviderApplication;
import com.ailiangbao.provider.dal.net.http.entity.account.UserInfoEntity_RORM;
import com.ailiangbao.provider.dal.net.http.entity.account.UserStatusEntity_RORM;
import com.wangjie.rapidorm.core.config.TableConfig;
import com.wangjie.rapidorm.core.connection.DatabaseProcessor;
import com.wangjie.rapidorm.core.connection.RapidORMConnection;
import com.wangjie.rapidorm.core.delegate.openhelper.RapidORMDefaultSQLiteOpenHelperDelegate;

import java.util.HashMap;

/**
 * Created by cq on 2018/5/14
 */
public class HDatabaseFactory extends RapidORMConnection<RapidORMDefaultSQLiteOpenHelperDelegate> {
    public static final String TAG = HDatabaseFactory.class.getSimpleName();

    private static HDatabaseFactory instance;
    private String databaseName;

    public HDatabaseFactory() {
        super();
    }

    public synchronized static HDatabaseFactory getInstance() {
        if (null == instance) {
            instance = new HDatabaseFactory();
        }
        return instance;
    }

    @Override
    public boolean resetDatabase(@NonNull String databaseName) {
//        this.databaseName = databaseName;
//        return super.resetDatabase(databaseName);
        if (databaseName.equals(this.databaseName)) {
            return false;
        } else {
            this.databaseName = databaseName;
            DatabaseProcessor.getInstance().resetRapidORMDatabaseOpenHelper(this.getRapidORMDatabaseOpenHelper(databaseName));
            DatabaseProcessor.getInstance().getDb();
            Log.d(TAG, "数据库初始化成功");
            return true;
        }
    }

    @Override
    public boolean resetDatabaseIfCrash() {
        resetDatabase(databaseName);
        return true;
    }

    @Override
    protected RapidORMDefaultSQLiteOpenHelperDelegate getRapidORMDatabaseOpenHelper(@NonNull String databaseName) {
        return new RapidORMDefaultSQLiteOpenHelperDelegate(new HDatabaseOpenHelper(ProviderApplication.getInstance().getApplication(), databaseName));
    }

    @Override
    protected void registerTableConfigMapper(HashMap<Class, TableConfig> tableConfigMapper) {
        tableConfigMapper.put(UserInfoEntity.class, new UserInfoEntity_RORM());
        tableConfigMapper.put(UserStatusEntity.class, new UserStatusEntity_RORM());
//        tableConfigMapper.put(LiveRoomEntity.class,new LiveRoomEntity_RORM());
//        tableConfigMapper.put(WelfareTaskInfo.class,new WelfareTaskInfo_RORM());
        // register all table config here...
    }
}
