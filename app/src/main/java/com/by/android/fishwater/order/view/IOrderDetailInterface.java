package com.by.android.fishwater.order.view;

import com.by.android.fishwater.order.bean.OrderDetailBean;

import java.util.List;

/**
 * Created by by.huang on 2016/11/8.
 */

public interface IOrderDetailInterface {

    void OnRequstOrderDetailSuccess (List<OrderDetailBean> datas, boolean isLoadMore, boolean theEnd);
    void OnRequstOrderDetailFail();
}
