package com.by.android.fishwater.shopping.bean;

import java.io.Serializable;

import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by by.huang on 2016/10/13.
 */

public class GoodsBean implements Serializable{

//    id	Int	是	商品的唯一ID
//    thumbnails	String	是	缩略图URL
//    price	Float	是	商品价格
//    name	String	是	商品名称
//    sales	Int	是	销售量

//    num	Int	是	购买数量
//    spec	String	是	规格id
//    specName	String	是	规格名字
//    oprice	Float	是	商品原价

    public int id;
    public String thumbnails;
    public float price;
    public String name;
    public int sales;
    public int num;
    public String spec;
    public String specName;
    public float oprice;

}
