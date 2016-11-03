package com.by.android.fishwater.shopping.presenter;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.buycar.view.BuycarPage;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.order.view.GoodPage;
import com.by.android.fishwater.shopping.bean.GoodsDetailBean;
import com.by.android.fishwater.shopping.bean.respond.GoodsDetailResondBean;
import com.by.android.fishwater.shopping.view.IGoodsDetailInterface;
import com.by.android.fishwater.util.Constant;
import com.by.android.fishwater.util.HardwareUtil;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.SystemUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.HashMap;

/**
 * Created by by.huang on 2016/10/24.
 */

public class GoodsDetailPresenter {

    private IGoodsDetailInterface mShoppingDetailInterface;
    private Context mContext;

    public GoodsDetailPresenter(IGoodsDetailInterface shoppingDetailInterface, Context context) {
        this.mShoppingDetailInterface = shoppingDetailInterface;
        this.mContext = context;
    }


    /**
     * 商品详情
     *
     * @param id
     */
    public void getGoodsDetail(int id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "goodsView");
        map.put("id", id);
        HttpRequest.Post(Constant.GoodsUrl, map, new MyCallBack<GoodsDetailResondBean>() {
            @Override
            public void onSuccess(GoodsDetailResondBean result) {
                super.onSuccess(result);
                GoodsDetailBean datas = result.data;
                mShoppingDetailInterface.OnRequstDetailSuccess(datas);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mShoppingDetailInterface.OnRequestDetailFail();
            }
        });
    }

    public void goBuycar() {
        BuycarPage page = new BuycarPage();
        FWPresenter.getInstance().addFragment(page);
    }

    public void showSelectCount(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", HardwareUtil.screenHeight, HardwareUtil.screenHeight - ResourceHelper.getDimen(R.dimen.space_400) - SystemUtil.getStatusBarHeight(mContext)),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1)
        );
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    public void hideSelectCount(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", HardwareUtil.screenHeight - ResourceHelper.getDimen(R.dimen.space_400) - SystemUtil.getStatusBarHeight(mContext), HardwareUtil.screenHeight),
                ObjectAnimator.ofFloat(view, "alpha", 1, 0)
        );
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    public void goOrderPage() {
        GoodPage page = new GoodPage();
        FWPresenter.getInstance().addFragment(page);
    }
}
