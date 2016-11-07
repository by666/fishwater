package com.by.android.fishwater.mine.presenter;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.account.AccountManage;
import com.by.android.fishwater.buycar.view.BuycarPage;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.mine.bean.CollectBean;
import com.by.android.fishwater.mine.bean.respond.CollectRespondBean;
import com.by.android.fishwater.mine.view.CollectPage;
import com.by.android.fishwater.mine.view.FansPage;
import com.by.android.fishwater.mine.view.FollowPage;
import com.by.android.fishwater.mine.view.ICollectPageInterface;
import com.by.android.fishwater.mine.view.IMinePageInterface;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.order.view.AddressPage;
import com.by.android.fishwater.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by by.huang on 2016/11/4.
 */

public class MinePresenter {

    private IMinePageInterface mMinePageInterface;
    private ICollectPageInterface mCollectPageInterface;

    private int currentPosition = 0;
    private List<HomeListBean> currentDatas = new ArrayList<>();

    public MinePresenter(IMinePageInterface minePageInterface)
    {
        this.mMinePageInterface = minePageInterface;
    }

    public MinePresenter(ICollectPageInterface collectPageInterface)
    {
        this.mCollectPageInterface = collectPageInterface;
    }


    /**
     * 获取我收藏的列表
     */
    public void requestCollectDatas(int typeid)
    {
//        a	collectList
//        sessionid	String	是	登陆后的唯一标识
//        typeid	1/2/3/4	是	1-文章、2-商品、3-帖子、4-圈子
//        begin	Int	否	数据开始位置，默认从0开始，翻页使用
//        limit	Int	否	数据获取条数，默认为10条
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "collectList");
        map.put("sessionid", AccountManage.getInstance().getSessionId());
        map.put("typeid",typeid);
        map.put("begin",currentPosition);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<CollectRespondBean>() {
            @Override
            public void onSuccess(CollectRespondBean result) {
                super.onSuccess(result);
                List<CollectBean> datas = result.data;
                mCollectPageInterface.OnGetCollectListSuccess(datas);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mCollectPageInterface.OnGetCollectListFail();
            }
        });
    }


    public void requestFansList()
    {
//        a	fansList
//        userid	Int	是	USERID 用户唯一标识
//        begin	Int	否	开始位置，分页使用，默认为0
//        limit	Int	否	数据条数，分页使用，默认为10

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "fansList");
        map.put("userid", AccountManage.getInstance().getUserId());
//        map.put("begin",currentPosition);
//        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<CollectRespondBean>() {
//            @Override
//            public void onSuccess(CollectRespondBean result) {
//                super.onSuccess(result);
//                List<CollectBean> datas = result.data;
//                mCollectPageInterface.OnGetCollectListSuccess(datas);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                super.onError(ex, isOnCallback);
//                mCollectPageInterface.OnGetCollectListFail();
//            }
//        });
    }
}
