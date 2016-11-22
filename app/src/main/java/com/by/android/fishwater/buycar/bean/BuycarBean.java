package com.by.android.fishwater.buycar.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by by.huang on 2016/10/27.
 */

@Table(name = "buycar")
public class BuycarBean {

    @Column(name="ID",isId=true,autoGen=false)
    public int id;

    @Column(name = "COUNT")
    public int count;

    @Column(name = "NAME")
    public String name;

    @Column(name = "IMGURL")
    public String imgUrl;

    @Column(name = "SELECT")
    public boolean isSelect;

    @Column(name = "PRICE")
    public float price;

    @Column(name = "Edit")
    public boolean isEdit;

}
