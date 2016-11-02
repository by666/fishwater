package com.by.android.fishwater.util;

import android.os.Environment;

import com.by.android.fishwater.FWApplication;

/**
 * Created by by.huang on 2016/10/10.
 */
public class Constant {

    public static String RootUrl = "http://api.mg27.com";
    public static String UserUrl = RootUrl + "/user.php";
    public static String HomeUrl = RootUrl + "/home.php";
    public static String ForumUrl = RootUrl + "/forum.php";
    public static String GoodsUrl = RootUrl + "/goods.php";
    public static String DictUrl = RootUrl + "/dict.php";




    public static String PREFERENCE_ACCOUNT = "pre_account";
    public static String PREFERENCE_SESSIONID = "pre_sessionid";
    public static String PREFERENCE_USERID = "pre_userid";


    public static String APP_PATH = Environment.getExternalStorageDirectory().getPath()+"/fishwater";

}
