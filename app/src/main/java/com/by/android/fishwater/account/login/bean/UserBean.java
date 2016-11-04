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
    public String id;
    //昵称
    public String nickname;
    //性别
    public int gender;
    //年龄
    public String age;
    //生日
    public String birthday;
    //性取向
    public String sexuality;
    //婚姻状态
    public String marital;
    //等级
    public String level;
    //头像
    public String avatar;
    //粉丝数
    public int fansNum;
    //关注数
    public int concernedNum;
}
