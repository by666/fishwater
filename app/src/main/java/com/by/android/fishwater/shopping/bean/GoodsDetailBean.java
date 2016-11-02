package com.by.android.fishwater.shopping.bean;

import com.by.android.fishwater.bean.BannerBean;

import java.util.List;

import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by by.huang on 2016/10/24.
 */

public class GoodsDetailBean {
//    id	Int	是	商品的唯一ID
//    name	String	是	商品名称
//    oprice	Float	是	商品原价
//    price	Float	是	商品价格
//    praise	Int	是	点赞数量
//    sales	Int	是	销售量
//    thumbnails	String	是	缩略图URL
//    comment_num	Int	是	评论数量
//    bigimg	Array<String>	是	预览大图URL

    public int id;
    public String name;
    public float oprice;
    public float price;
    public int praise;
    public int sales;
    public String thumbnails;
    public int comment_num;
    public List<BannerBean> bigimg;

}
