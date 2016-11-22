package com.by.android.fishwater.order.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.by.android.fishwater.FWApplication;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.AccountManage;
import com.by.android.fishwater.bean.RespondStrBean;
import com.by.android.fishwater.bean.RespondArrayBean;
import com.by.android.fishwater.buycar.bean.BuycarBean;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.order.bean.AddressBean;
import com.by.android.fishwater.order.bean.OrderBean;
import com.by.android.fishwater.order.bean.OrderDetailBean;
import com.by.android.fishwater.order.bean.respond.AddressRespondBean;
import com.by.android.fishwater.order.bean.respond.OrderDetailRespondBean;
import com.by.android.fishwater.order.bean.respond.OrderResondBean;
import com.by.android.fishwater.order.view.IOrderDetailInterface;
import com.by.android.fishwater.order.view.IOrderInterface;
import com.by.android.fishwater.util.Constant;
import com.by.android.fishwater.util.HardwareUtil;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.SystemUtil;
import com.by.android.fishwater.util.ToastUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.by.android.fishwater.net.ErrorCode.Code_Success;

/**
 * Created by by.huang on 2016/10/31.
 */

public class OrderPresenter {


    private IOrderInterface mOrderInterface;
    private IOrderDetailInterface mOrderDetailInterface;

    public OrderPresenter(IOrderInterface orderInterface) {
        this.mOrderInterface = orderInterface;
    }

    /**  订单详情  **/
    private int currentPosition = 0;
    private List<OrderDetailBean> currentDatas = new ArrayList<>();
    public OrderPresenter(IOrderDetailInterface orderDetailInterface) {
        this.mOrderDetailInterface = orderDetailInterface;
    }

    /**
     * 获取收获地址列表
     */
    public void getAddressList() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "addrList");
        map.put("sessionid", AccountManage.getInstance().getSessionId());
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
    public void saveAddress(AddressBean data) {
        if(TextUtils.isEmpty(data.name))
        {
            ToastUtil.show("请填写收件人");
            return;
        }
        if(TextUtils.isEmpty(data.phone))
        {
            ToastUtil.show("请填写手机号码");
            return;
        }
        if(TextUtils.isEmpty(data.address))
        {
            ToastUtil.show("请填写详细地址");
            return;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "addrSave");
        map.put("sessionid", AccountManage.getInstance().getSessionId());
        if (data.id != 0) {
            map.put("id", data.id);
        }
        map.put("name", data.name);
        map.put("phone", data.phone);
        map.put("address", data.address);
        map.put("province", data.province);
        map.put("city", data.city);
        map.put("area", data.area);
        map.put("isDefault", data.isDefault);
//        map.put("intime", data.intime);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<RespondStrBean>() {
            @Override
            public void onSuccess(RespondStrBean result) {
                super.onSuccess(result);
                if(result.status == Code_Success)
                {
                    mOrderInterface.OnSaveAddressSuccess();
                    ToastUtil.show("保存收获地址成功");
                    return;
                }
                mOrderInterface.OnSaveAddressFail();
                ToastUtil.show("保存收获地址失败");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mOrderInterface.OnSaveAddressFail();
                ToastUtil.show("保存收获地址失败");

            }
        });
    }

    /**
     * 删除收获地址(data错误返回为【】)
     */
    public void deleteAddress(final List<AddressBean> datas, final AddressBean data) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "addrDelete");
        map.put("sessionid", AccountManage.getInstance().getSessionId());
        map.put("id", data.id);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<RespondArrayBean>() {
            @Override
            public void onSuccess(RespondArrayBean result) {
                super.onSuccess(result);
                if(result.status == Code_Success)
                {
                    datas.remove(data);
                    mOrderInterface.OnDeleteAddressSuccess(datas);
                    ToastUtil.show("删除收获地址成功");
                    return;
                }
                mOrderInterface.OnDeleteAddressFail();
                ToastUtil.show("删除收获地址失败");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mOrderInterface.OnDeleteAddressFail();
                ToastUtil.show("删除收获地址失败");
            }
        });
    }

    /**
     * 支付
     */
    public void pay(AddressBean addressBean,List<BuycarBean> datas) {
//        province	Int	是	area.list中对应的唯一ID
//        city	Int	是	area.list中对应的唯一ID
//        area	Int	是	area.list中对应的唯一ID
//        address	String	是	收货地址的详细地址
//        name	String	是	收件人姓名
//        phone	String	是	收件人联系方式
//        paytype	Int	是	支付类型：1-支付宝、2-微信
//        goods	String	是	订单的商品数据，数据格式：goodsid,num,spec{竖线}goodsid,num,spec
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "order");
        map.put("sessionid", AccountManage.getInstance().getSessionId());
        map.put("province",addressBean.province);
        map.put("city",addressBean.city);
        map.put("area",addressBean.area);
        map.put("address",addressBean.address);
        map.put("name",addressBean.name);
        map.put("phone",addressBean.phone);
        map.put("paytype",1);
        String goodsStr = "";

        for (BuycarBean buycarBean : datas) {
            goodsStr += buycarBean.id+","+buycarBean.count+"|";
        }
        goodsStr = goodsStr.substring(0,goodsStr.length() - 1);
        map.put("goods",goodsStr);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<OrderResondBean>() {
            @Override
            public void onSuccess(OrderResondBean result) {
                super.onSuccess(result);
                OrderBean data = result.data;
                Log.i("by",data.paystr);
                mOrderInterface.OnOrderSuccess(data);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mOrderInterface.OnOrderFail();

            }
        });
    }



    public void showPcaView(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", HardwareUtil.screenHeight, HardwareUtil.screenHeight - ResourceHelper.getDimen(R.dimen.space_200) - SystemUtil.getStatusBarHeight(FWApplication.mApplication.getApplicationContext())),
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


    public void hidePcaView(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", HardwareUtil.screenHeight - ResourceHelper.getDimen(R.dimen.space_200) - SystemUtil.getStatusBarHeight(FWApplication.mApplication.getApplicationContext()), HardwareUtil.screenHeight),
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


    public void requestNewOrderDetail(int statu)
    {
        currentPosition = 0;
        currentDatas.removeAll(currentDatas);
        requestOrderDetail(statu,false);
    }

    public void requestOrderDetail(int statu,final boolean isLoadMore) {
//        a	orderList	是
//        status	String	否	排序字段：1-待付款、2-待发货、3-待收货、4-待评价、5-交易成功、6-交易关闭
//        begin	Int	否	数据开始位置，默认从0开始，翻页使用
//        limit	Int	否	数据获取条数，默认为10条

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "orderList");
        map.put("status", statu);
        map.put("begin", currentPosition);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<OrderDetailRespondBean>() {
            @Override
            public void onSuccess(OrderDetailRespondBean result) {
                super.onSuccess(result);
                List<OrderDetailBean> datas = result.data;
                Log.i("by666",datas.size()+"");
                currentPosition += datas.size();
                if (datas.size() == 0) {
                    mOrderDetailInterface.OnRequstOrderDetailSuccess(currentDatas, isLoadMore, true);
                } else {
                    currentDatas.addAll(datas);
                    mOrderDetailInterface.OnRequstOrderDetailSuccess(currentDatas, isLoadMore, false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mOrderDetailInterface.OnRequstOrderDetailFail();
            }
        });
    }
}
