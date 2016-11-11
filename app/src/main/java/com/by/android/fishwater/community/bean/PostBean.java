package com.by.android.fishwater.community.bean;

import java.io.Serializable;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by by.huang on 2016/11/11.
 */

public class PostBean{

//    id	Int	是	唯一ID
//    parentid	Int	是	父级分类ID(可能有回帖)
//    userid	Int	是	Userid 唯一标识
//    title	String	是	标题
//    note	String	是	内容
//    classid	Int	是	所属圈子唯一ID
//    intime	Datetime	是	时间 YYYY-mm-dd HH:ii:ss
//    status	Int	是	1-已通过、0-已拒绝、2-待审核
//    nickname	String	是	昵称
//    gender	Int	是	0-女、1-男
//    avatar	String	是	头像地址
//    imagesUrl	Array<String>	是	缩略图地址

    public int id;
    public int parentid;
    public int userid;
    public String title;
    public String note;
    public int classid;
    public String intime;
    public int status;
    public String nickname;
    public int gender;
    public String avatar;
    public List<String> imagesUrl;
    public boolean isPraise;
}
