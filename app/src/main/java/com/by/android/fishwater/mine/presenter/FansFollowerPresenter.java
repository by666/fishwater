package com.by.android.fishwater.mine.presenter;

import com.by.android.fishwater.account.AccountManage;
import com.by.android.fishwater.mine.bean.FansFollowerBean;
import com.by.android.fishwater.mine.bean.respond.FansFollowerRespondBean;
import com.by.android.fishwater.mine.view.IFansFollwerInterface;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by by.huang on 2016/11/7.
 */

public class FansFollowerPresenter {

    private IFansFollwerInterface mFansFollwerInterface;
    private int currentPosition = 0;
    private List<FansFollowerBean> currentDatas = new ArrayList<>();
    private boolean mIsFans;

    public FansFollowerPresenter(IFansFollwerInterface fansFollwerInterface, boolean isFans) {
        this.mFansFollwerInterface = fansFollwerInterface;
        this.mIsFans = isFans;
    }

    public void getNewListData() {
        currentPosition = 0;
        currentDatas.removeAll(currentDatas);
        getListData(false);
    }

    public void getListData(final boolean isLoadMore) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (mIsFans) {
            map.put("a", "fansList");
        } else {
            map.put("a", "followList");
        }
        map.put("userid", AccountManage.getInstance().getUserId());
        map.put("begin", currentPosition);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<FansFollowerRespondBean>() {
            @Override
            public void onSuccess(FansFollowerRespondBean result) {
                super.onSuccess(result);
                List<FansFollowerBean> datas = result.data;
                currentPosition += datas.size();
                if (datas.size() == 0) {
                    mFansFollwerInterface.OnGetFansFollwerSuccess(currentDatas, isLoadMore, true);
                } else {
                    currentDatas.addAll(datas);
                    mFansFollwerInterface.OnGetFansFollwerSuccess(currentDatas, isLoadMore, false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mFansFollwerInterface.OnGetFansFollwerFail();
            }
        });
    }
}
