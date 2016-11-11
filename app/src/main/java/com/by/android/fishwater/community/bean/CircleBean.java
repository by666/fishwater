package com.by.android.fishwater.community.bean;

import java.io.Serializable;

import static android.R.attr.id;

/**
 * Created by by.huang on 2016/11/11.
 */

public class CircleBean implements Serializable {

//    id	Int	是	圈子唯一ID
//    title	Int	是	标题
//    subTitle	String	是	子标题
//    url	Int	是	图片URL
//    status	Int	是	状态为1，因为是自己已关注的圈子

    public int id;
    public String title;
    public String subTitle;
    public String url;
    public int status;
}
