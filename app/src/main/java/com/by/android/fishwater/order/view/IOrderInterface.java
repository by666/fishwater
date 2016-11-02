package com.by.android.fishwater.order.view;

import com.by.android.fishwater.order.bean.AddressBean;

import java.util.List;

/**
 * Created by by.huang on 2016/10/31.
 */

public interface IOrderInterface {

    void OnGetAddressListSuccess(List<AddressBean> datas);
    void OnGetAddressListFail();
    void OnSaveAddressSuccess();
    void OnSaveAddressFail();
    void OnDeleteAddressSuccess();
    void OnDeleteAddressFail();
}
