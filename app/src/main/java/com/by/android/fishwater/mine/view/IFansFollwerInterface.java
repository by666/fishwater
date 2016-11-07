package com.by.android.fishwater.mine.view;

import com.by.android.fishwater.mine.bean.FansFollowerBean;

import java.util.List;

/**
 * Created by by.huang on 2016/11/7.
 */

public interface IFansFollwerInterface {

    void OnGetFansFollwerSuccess(List<FansFollowerBean> datas,boolean isLoadMore,boolean theEnd);
    void OnGetFansFollwerFail();
}
