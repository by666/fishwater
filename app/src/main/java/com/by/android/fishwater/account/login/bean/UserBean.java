package com.by.android.fishwater.account.login.bean;

import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by by.huang on 2016/10/9.
 * 用户资料
 */
@HttpResponse(parser = JsonResponseParser.class)
public class UserBean {
    //用户id
    public String id;
    //昵称
    public String nickname;
    //性别
    public String gender;
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
}
