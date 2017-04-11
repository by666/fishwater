package com.by.android.fishwater.order.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.alipay.PayResult;
import com.by.android.fishwater.buycar.bean.BuycarBean;
import com.by.android.fishwater.database.FWDatabaseManager;
import com.by.android.fishwater.observer.FWObserver;
import com.by.android.fishwater.observer.FWObserverManager;
import com.by.android.fishwater.observer.ObserverData;
import com.by.android.fishwater.order.adapter.OrderGoodsListAdapter;
import com.by.android.fishwater.order.adapter.OrderPaylistAdapter;
import com.by.android.fishwater.order.adapter.OrderPriceAdapter;
import com.by.android.fishwater.order.bean.AddressBean;
import com.by.android.fishwater.order.bean.OrderBean;
import com.by.android.fishwater.order.bean.PayBean;
import com.by.android.fishwater.order.bean.PriceBean;
import com.by.android.fishwater.order.presenter.OrderPresenter;
import com.by.android.fishwater.view.AlphaImageView;
import com.by.android.fishwater.view.LinearLayoutDecoration;
import com.by.android.fishwater.widget.PriceView;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.by.android.fishwater.observer.ObserverData.Update_Address_Good;

/**
 * Created by by.huang on 2016/10/29.
 */

public class GoodPage extends FWActivity implements IOrderInterface, FWObserver {

    @ViewInject(R.id.txt_title)
    TextView mTitletxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.layout_address)
    View mAddressLayout;

    @ViewInject(R.id.txt_name)
    TextView mNameTxt;

    @ViewInject(R.id.txt_address)
    TextView mAddressTxt;

    @ViewInject(R.id.recyclerview_goods)
    LRecyclerView mGoodReclerView;

    @ViewInject(R.id.recyclerview_pay)
    LRecyclerView mPayReclerView;

    @ViewInject(R.id.recyclerview_price)
    LRecyclerView mPriceReclerView;

    @ViewInject(R.id.txt_price)
    PriceView mPricetxt;

    @ViewInject(R.id.btn_order)
    Button mOrderBtn;

    private OrderPresenter mOrderPresenter;

    private OrderGoodsListAdapter mGoodsListAdapter;
    private LRecyclerViewAdapter mGoodsLRecyclerViewAdapter;

    private OrderPaylistAdapter mPayListAdapter;
    private LRecyclerViewAdapter mPayLRecyclerViewAdapter;

    private OrderPriceAdapter mPriceAdapter;
    private LRecyclerViewAdapter mPriceLRecyclerViewAdapter;

    private List<BuycarBean> mDatas;
    private AddressBean addressBean;
    float total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_order);
        x.view().inject(this);
        mOrderPresenter = new OrderPresenter(this);
        initView();
    }

    private void initView() {
        mDatas = FWDatabaseManager.getInstance().findAll();
        for (BuycarBean data : mDatas) {
            total += data.count * data.price;
        }
        initNavigationBar();
        initAddressView();
        initGoodList();
        initPayList();
        initPriceList();
        initBottom();
        mOrderPresenter.getAddressList();
        FWObserverManager.getIntance().addObserver(ObserverData.build(Update_Address_Good, this));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FWObserverManager.getIntance().removeObserver(Update_Address_Good);
    }

    private void initNavigationBar() {
        mTitletxt.setText("确认订单");
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initAddressView() {
        mAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoodPage.this, AddressPage.class));
            }
        });
    }

    private void initGoodList() {
        mGoodReclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mGoodReclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mGoodReclerView.setPullRefreshEnabled(false);


        mGoodReclerView.setLayoutManager(new LinearLayoutManager(this));
        mGoodReclerView.addItemDecoration(new LinearLayoutDecoration.Builder(this).setHeight(R.dimen.space_1).setColor(getResources().getColor(R.color.gray_bg)).build());
        mGoodsListAdapter = new OrderGoodsListAdapter(this);
        mGoodsLRecyclerViewAdapter = new LRecyclerViewAdapter(mGoodsListAdapter);
        mGoodReclerView.setAdapter(mGoodsLRecyclerViewAdapter);

        if (mDatas != null) {
            mGoodsListAdapter.updateData(mDatas);
        }
        mGoodsLRecyclerViewAdapter.notifyDataSetChanged();
        mGoodsLRecyclerViewAdapter.removeFooterView(mGoodsLRecyclerViewAdapter.getFooterView());
    }

    private void initPayList() {
        final List<PayBean> datas = PayBean.getDatas();
        mPayReclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mPayReclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mPayReclerView.setPullRefreshEnabled(false);


        mPayReclerView.setLayoutManager(new LinearLayoutManager(this));
        mPayReclerView.addItemDecoration(new LinearLayoutDecoration.Builder(this).setHeight(R.dimen.space_1).setColor(getResources().getColor(R.color.gray_bg)).build());
        mPayListAdapter = new OrderPaylistAdapter(this);
        mPayLRecyclerViewAdapter = new LRecyclerViewAdapter(mPayListAdapter);
        mPayReclerView.setAdapter(mPayLRecyclerViewAdapter);

        if (datas != null) {
            mPayListAdapter.updateData(datas);
        }
        mPayLRecyclerViewAdapter.notifyDataSetChanged();
        mPayLRecyclerViewAdapter.removeFooterView(mPayLRecyclerViewAdapter.getFooterView());

        mPayLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if (i == 0) {
                    datas.get(0).isSelect = true;
                    datas.get(1).isSelect = false;
                } else {
                    datas.get(0).isSelect = false;
                    datas.get(1).isSelect = true;
                }
                mPayListAdapter.updateData(datas);
                mPayLRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });
    }

    private void initPriceList() {
        List<PriceBean> datas = new ArrayList<>();
        datas.add(PriceBean.createPriceBean("商品总价：", total));
        datas.add(PriceBean.createPriceBean("运费：", 8));
        datas.add(PriceBean.createPriceBean("运费减免：", -8));
        datas.add(PriceBean.createPriceBean("优惠减免：", 0));

        mPriceReclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mPriceReclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mPriceReclerView.setPullRefreshEnabled(false);


        mPriceReclerView.setLayoutManager(new LinearLayoutManager(this));
        mPriceReclerView.addItemDecoration(new LinearLayoutDecoration.Builder(this).setHeight(R.dimen.space_1).setColor(getResources().getColor(R.color.gray_bg)).build());
        mPriceAdapter = new OrderPriceAdapter(this);
        mPriceLRecyclerViewAdapter = new LRecyclerViewAdapter(mPriceAdapter);
        mPriceReclerView.setAdapter(mPriceLRecyclerViewAdapter);

        if (mDatas != null) {
            mPriceAdapter.updateData(datas);
        }
        mPriceLRecyclerViewAdapter.notifyDataSetChanged();
        mPriceLRecyclerViewAdapter.removeFooterView(mPriceLRecyclerViewAdapter.getFooterView());
    }

    private void initBottom() {
        mPricetxt.setText(total + "");
        mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOrderPresenter.pay(addressBean, mDatas);
            }
        });
    }


    @Override
    public void OnGetAddressListSuccess(List<AddressBean> datas) {

        if (datas != null && datas.size() > 0) {
            for (AddressBean data : datas) {
                if (data.isDefault == 1) {
                    addressBean = data;
                    mNameTxt.setText(data.name);
                    String address = AddressBean.getAddress(data.province, data.city, data.area) + data.address;
                    mAddressTxt.setText(address);
                    break;
                } else {
                    mNameTxt.setText("");
                    mAddressTxt.setText("");
                }
            }
        }
    }

    @Override
    public void OnGetAddressListFail() {

    }

    @Override
    public void OnSaveAddressSuccess() {

    }

    @Override
    public void OnSaveAddressFail() {

    }

    @Override
    public void OnDeleteAddressSuccess(List<AddressBean> datas) {

    }

    @Override
    public void OnDeleteAddressFail() {

    }

    @Override
    public void OnOrderSuccess(OrderBean data) {
        final String orderInfo = data.paystr;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(GoodPage.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void OnOrderFail() {

    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(GoodPage.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(GoodPage.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void update(Object object) {
        mOrderPresenter.getAddressList();
    }
}
