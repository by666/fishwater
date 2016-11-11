package com.by.android.fishwater.community.bean;

import java.util.List;

/**
 * Created by by.huang on 2016/11/11.
 */

public class ForumBean {

//    id	Int	是	唯一ID
//    pid	Int	是	父级分类ID
//    title	String	是	标题
//    state	Int	是	跳转的目标唯一ID
//    sub	Array	是	子集

    public int id;
    public int pid;
    public String title;
    public int state;
    public String url;
    public List<CircleBean> sub;
}
