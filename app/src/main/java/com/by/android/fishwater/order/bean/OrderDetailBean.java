package com.by.android.fishwater.order.bean;


import com.by.android.fishwater.shopping.bean.GoodsBean;

import java.util.List;

/**
 * Created by by.huang on 2016/11/8.
 */

public class OrderDetailBean {

//    id		Int	是	订单唯一ID
//    orderid		String	是	订单号
//    province		Float	是	省唯一ID
//    city		String	是	市唯一ID
//    area		Int	是	区唯一ID
//    address		String	是	收件人详细地址
//    name		String	是	收件人姓名
//    phone		String	是	收件人手机号
//    paytype		String	是	支付方式：1-支付宝 2-微信
//    goodsTotal		String	是	商品总价
//    fare		String	是	运费
//    fareRelief		String	是	运费减免
//    offerRelief		String	是	订单活动减免
//    total		String	是	订单总金额
//    status		String	是	交易状态：1-待付款、2-待发货、3-待收货、4-待评价、5-交易成功、6-交易关闭
//    intime		String	是	入库时间：YYYY-mm-dd HH:ii:ss

    public int id;
    public String orderid;
    public int province;
    public int city;
    public int area;
    public String address;
    public String name;
    public String phone;
    public int paytype;
    public float goodsTotal;
    public float fare;
    public float fareRelief;
    public float total;
    public int status;
    public String intime;
    public List<GoodsBean> goods;


}
