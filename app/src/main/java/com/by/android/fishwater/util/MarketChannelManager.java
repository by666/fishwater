/*
 *  * Copyright (C) 2015-2015 SJY.JIANGSU Corporation. All rights reserved
 */

package com.by.android.fishwater.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import java.util.ArrayList;

/**
 * Created by linhz on 2015/11/16.
 * Email: linhaizhong@ta2she.com
 */
public class MarketChannelManager {
    private static final String PREFERENCE_NAME = "market_channel";
    private static final String KEY_MARKET_CHANNEL = "market_channel";
    private static final int MARKET_CHANNEL_AUTDITED = 1;

    private static final String VERSION = "version";
    private static final String IS_AUDITED = "isAudited";

    public static final String CHANNEL_XIAOMI = "xiaomi";

    private static MarketChannelManager sInstance;
    private Context mContext;
    private ArrayList<String> mNeedAuditChannelList = new ArrayList<String>();
    private String mCurrentChannel;

    private MarketChannelManager(Context context) {
        mContext = context;
        try {
            PackageManager pm = context.getPackageManager();
            mCurrentChannel = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData.getString("UMENG_CHANNEL");
        } catch (Exception e) {
            e.printStackTrace();
            mCurrentChannel = "unknow";
        }
        initAuditChannelList();
    }

    private void initAuditChannelList() {
        mNeedAuditChannelList.add(CHANNEL_XIAOMI);
    }

    public static void init(Context context) {
        sInstance = new MarketChannelManager(context);
    }

    public static MarketChannelManager getInstance() {
        return sInstance;
    }

    public String getMarketChannel() {
        return mCurrentChannel;
    }

    public boolean isMarketAudited() {
        if (!isMarketChannelNeedAudit()) {
            return true;
        }
        int defaultValue = -1;
        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        int value = sp.getInt(KEY_MARKET_CHANNEL, defaultValue);
        if (value == MARKET_CHANNEL_AUTDITED) {
            return true;
        }
        return false;
    }



    private void saveMarketAuditValue(int value) {
        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(KEY_MARKET_CHANNEL, value).commit();
    }

    private boolean isMarketChannelNeedAudit() {
        if (mNeedAuditChannelList.contains(mCurrentChannel)) {
            return true;
        }

        return false;
    }
}
