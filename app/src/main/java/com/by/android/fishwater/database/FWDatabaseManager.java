package com.by.android.fishwater.database;

import android.os.Environment;

import com.by.android.fishwater.buycar.bean.BuycarBean;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * Created by by.huang on 2016/10/27.
 */

public class FWDatabaseManager {

    private static FWDatabaseManager mInstance;
    private DbManager mDbManager;
    private final static String DB_NAME = "fishwater";
    private final static int DB_VERSION = 1;
    private final static String DB_DIR = Environment.getExternalStorageDirectory().getPath() + "/fishwater/db";


    public static FWDatabaseManager getInstance() {
        if (mInstance == null) {
            synchronized (FWDatabaseManager.class) {
                if (mInstance == null) {
                    mInstance = new FWDatabaseManager();
                }
            }
        }
        return mInstance;
    }

    public void init() {
        DbManager.DaoConfig config = new DbManager.DaoConfig()
                .setDbName(DB_NAME)
                .setAllowTransaction(true)
                .setDbDir(new File(DB_DIR))
                .setDbVersion(DB_VERSION);
        mDbManager = x.getDb(config);
    }

    public DbManager getDbManager() {
        return mDbManager;
    }

    public void add(BuycarBean data) {
        if (mDbManager != null) {
            try {
                mDbManager.save(data);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    public List<BuycarBean> delete(BuycarBean data) {
        if (mDbManager != null) {
            try {
                List<BuycarBean> datas = mDbManager.findAll(BuycarBean.class);
                if (datas == null || datas.size() == 0) {
                    return null;
                }
                WhereBuilder whereBuilder = WhereBuilder.b();
                whereBuilder.and("id", "=", data.id);
                mDbManager.delete(BuycarBean.class, whereBuilder);
                datas = mDbManager.findAll(BuycarBean.class);
                return datas;
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public List<BuycarBean> findAll() {
        try {
            List<BuycarBean> datas = mDbManager.findAll(BuycarBean.class);
            return datas;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<BuycarBean> findById(int id) {
        try {
            List<BuycarBean> datas = mDbManager.selector(BuycarBean.class)
                    .where("id", "=", id)
                    .findAll();
            return datas;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(int id, int count) {
        try {
            List<BuycarBean> users = mDbManager.findAll(BuycarBean.class);
            if (users == null || users.size() == 0) {
                return;
            }
            WhereBuilder whereBuilder = WhereBuilder.b();
            whereBuilder.and("id", "=", id);
            mDbManager.update(BuycarBean.class, whereBuilder,
                    new KeyValue("count", count));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


}
