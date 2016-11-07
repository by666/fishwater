package com.by.android.fishwater.order.view;

import com.by.android.fishwater.order.bean.AddressBean;
import com.by.android.fishwater.order.bean.OrderBean;

import java.util.List;

/**
 * Created by by.huang on 2016/10/31.
 */

public interface IOrderInterface {

    void OnGetAddressListSuccess(List<AddressBean> datas);
    void OnGetAddressListFail();
    void OnSaveAddressSuccess();
    void OnSaveAddressFail();
    void OnDeleteAddressSuccess(List<AddressBean> datas);
    void OnDeleteAddressFail();
    void OnOrderSuccess(OrderBean data);
    void OnOrderFail();
}
