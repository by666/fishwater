package com.by.android.fishwater.homepage.bean;

import java.util.List;

import static android.R.attr.id;

/**
 * Created by by.huang on 2016/11/6.
 */

public class CommentBean {

//    id	Int	是	评论的唯一ID
//    parentid	Int	是	父级ID
//    userid	Int	是	USERID
//    typeid	Int	是	1-文章、2-商品、3-帖子
//    targetid	Int	是	被评论的目标内容ID
//    replay	Int	是	回复对象的USERID
//    note	String	是	内容
//    intime	Datetime	是	时间 YYYY-mm-dd HH:ii:ss
//    nickname	String	是	昵称
//    gender	Int	是	0-女、1-男
//    avatar	String	是	用户头像
//    subNum	Int	是	子评论的数量
//    sub	Array	是	子评论数组

    public int id;
    public int parentid;
    public int userid;
    public int typeid;
    public int targetid;
    public int replay;
    public String note;
    public String intime;
    public String nickname;
    public int gender;
    public String avatar;
    public int subNum;
}
