package com.by.android.fishwater.account.login.bean;

import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by by.huang on 2016/10/9.
 * 用户资料
 */
@HttpResponse(parser = JsonResponseParser.class)
public class UserBean {

    public static final int SEX_WOMEN = 0;
    public static final int SEX_MAN = 1;
    //用户id
    public int id;
    //昵称
    public String nickname;
    //性别
    public int gender;
    //年龄
    public int age;
    //生日
    public String birthday;
    //性取向
    public int sexuality;
    //婚姻状态
    public int marital;
    //等级
    public int level;
    //头像
    public String avatar;
    //粉丝数
    public int fansNum;
    //关注数
    public int concernedNum;

    public static String getSexuality(int sexuality)
    {
        switch (sexuality)
        {
            case 1:
                return "爱好男";
            case 2:
                return "爱好女";
            case 3:
                return "双性恋";
            case 4:
                return "无性恋";
        }
        return "";
    }

    public static String getMarital(int marital)
    {
        switch (marital)
        {
            case 1:
                return "单身";
            case 2:
                return "恋爱中";
            case 3:
                return "结婚";
            case 4:
                return "离异";
            case 5:
                return "丧偶";
        }
        return "";
    }
}
