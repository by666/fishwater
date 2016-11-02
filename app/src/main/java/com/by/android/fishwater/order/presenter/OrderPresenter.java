package com.by.android.fishwater.order.presenter;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.account.AccountManage;
import com.by.android.fishwater.net.BaseBean;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.order.bean.AddressBean;
import com.by.android.fishwater.order.bean.respond.AddressRespondBean;
import com.by.android.fishwater.order.view.AddressPage;
import com.by.android.fishwater.order.view.IOrderInterface;
import com.by.android.fishwater.util.Constant;

import java.util.HashMap;
import java.util.List;

/**
 * Created by by.huang on 2016/10/31.
 */

public class OrderPresenter {


    private IOrderInterface mOrderInterface;

    public OrderPresenter(IOrderInterface orderInterface)
    {
        this.mOrderInterface = orderInterface;
    }

    /**
     * 获取收获地址列表
     */
    public void getAddressList()
    {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a","addrList");
        map.put("sessionid",AccountManage.getInstance().getSessionId());
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<AddressRespondBean>() {
            @Override
            public void onSuccess(AddressRespondBean result) {
                super.onSuccess(result);
                List<AddressBean> datas = result.data;
                mOrderInterface.OnGetAddressListSuccess(datas);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mOrderInterface.OnGetAddressListFail();
            }
        });
    }

    /**
     * 保存收获地址
     */
    public void saveAddress(AddressBean data)
    {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a","addrSave");
        map.put("sessionid",AccountManage.getInstance().getSessionId());
        map.put("id",data.id);
        map.put("name",data.name);
        map.put("phone",data.phone);
        map.put("address",data.address);
        map.put("province",data.province);
        map.put("city",data.city);
        map.put("area",data.area);
        map.put("isDefault",data.isDefault);
        map.put("intime",data.intime);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean result) {
                super.onSuccess(result);
                mOrderInterface.OnSaveAddressSuccess();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mOrderInterface.OnSaveAddressFail();
            }
        });
    }

    /**
     * 删除收获地址
     */
    public void deleteAddress(AddressBean data)
    {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a","addrDelete");
        map.put("sessionid",AccountManage.getInstance().getSessionId());
        map.put("id",data.id);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<BaseBean>() {
            @Override
            public void onSuccess(BaseBean result) {
                super.onSuccess(result);
                mOrderInterface.OnDeleteAddressSuccess();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mOrderInterface.OnDeleteAddressFail();
            }
        });
    }

    public void pay()
    {
//        a	order	是
//        province	Int	是	area.list中对应的唯一ID
//        city	Int	是	area.list中对应的唯一ID
//        area	Int	是	area.list中对应的唯一ID
//        address	String	是	收货地址的详细地址
//        name	String	是	收件人姓名
//        phone	String	是	收件人联系方式
//        paytype	Int	是	支付类型：1-支付宝、2-微信
//        goods	String	是	订单的商品数据，数据格式：goodsid,num,spec{竖线}goodsid,num,spec

    }

    public void goAddressPage()
    {
        AddressPage page = new AddressPage();
        FWPresenter.getInstance().addFragment(page);
    }


}
