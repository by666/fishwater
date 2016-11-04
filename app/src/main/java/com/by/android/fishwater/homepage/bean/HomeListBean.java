package com.by.android.fishwater.homepage.bean;

import java.io.Serializable;

import static android.R.attr.author;
import static android.R.attr.id;

/**
 * Created by by.huang on 2016/10/11.
 */

public class HomeListBean implements Serializable{

//    id	Int	是	内容的唯一ID
//    category	Int	是	栏目分类ID
//    title	String	是	标题
//    url	String	是	图片的URL地址
//    typeID	Int	是	目标类型1-文章、2-商品、3-帖子
//    targetID	Int	是	跳转的目标唯一ID
//    author	String	是	作者
//    intime	Datetime	是	时间 YYYY-mm-dd HH:ii:ss
//    begin	Int	否	数据开始位置，默认从0开始，翻页使用
//    limit	Int	否	数据获取条数，默认为20条


    public int id;

    public int category;

    public String title;

    public String url;

    public int typeID;

    public int targetID;

    public String author;

    public String intime;


}
