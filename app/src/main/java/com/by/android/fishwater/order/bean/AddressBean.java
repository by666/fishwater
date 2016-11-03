package com.by.android.fishwater.order.bean;

import com.by.android.fishwater.FWApplication;
import com.by.android.fishwater.order.bean.address.AreaBean;
import com.by.android.fishwater.order.bean.address.CityBean;
import com.by.android.fishwater.order.bean.address.ProvinceBean;
import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import static com.by.android.fishwater.util.Constant.APP_PATH;

/**
 * Created by by.huang on 2016/10/31.
 */

public class AddressBean implements Serializable {

//    id	Int	是	地址唯一ID
//    name	String	是	收货人姓名
//    phone	String	是	收货人手机号
//    address	String	是	收货详细地址
//    province	Int	是	省ID
//    city	Int	是	市ID
//    area	Int	是	区ID
//    isDefault	Int	是	是否默认地址 0-不是 1-是
//    intime	Datetime	是	YYYY-mm-dd HH:ii:ss

    public int id;
    public String name;
    public String phone;
    public String address;
    public int province;
    public int city;
    public int area;
    public int isDefault;
    public String intime;


    public static AddressBean getDefaultAddressBean() {
        AddressBean addressBean = new AddressBean();
        addressBean.province = 2;
        addressBean.city = 36;
        addressBean.area = 0;
        return addressBean;
    }

    public static String getAddress(int province, int city, int area) {
        String result = "";
        List<ProvinceBean> provinceBeanList = FWApplication.mApplication.getProvinceDatas();
        for (ProvinceBean provinceBean : provinceBeanList) {
            if (provinceBean.id == province) {
                result += provinceBean.name;
                List<CityBean> cityBeanList = provinceBean.cityBeens;
                for (CityBean cityBean : cityBeanList) {
                    if (cityBean.id == city) {
                        result += cityBean.name;
                        List<AreaBean> addressBeanList = cityBean.areaBeens;
                        if (addressBeanList != null && addressBeanList.size() > 0) {
                            for (AreaBean areaBean : addressBeanList) {
                                if (areaBean.id == area) {
                                    result += areaBean.name;
                                    return result;
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

}

