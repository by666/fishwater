package com.by.android.fishwater.order.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.buycar.bean.BuycarBean;
import com.by.android.fishwater.database.FWDatabaseManager;
import com.by.android.fishwater.order.adapter.OrderGoodsListAdapter;
import com.by.android.fishwater.order.adapter.OrderPaylistAdapter;
import com.by.android.fishwater.order.adapter.OrderPriceAdapter;
import com.by.android.fishwater.order.bean.AddressBean;
import com.by.android.fishwater.order.bean.PayBean;
import com.by.android.fishwater.order.bean.PriceBean;
import com.by.android.fishwater.order.presenter.OrderPresenter;
import com.by.android.fishwater.view.LinearLayoutDecoration;
import com.by.android.fishwater.view.AlphaImageView;
import com.by.android.fishwater.widget.PriceView;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/29.
 */

@ContentView(R.layout.page_order)
public class GoodPage extends Fragment implements IOrderInterface{

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
    float total = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOrderPresenter = new OrderPresenter(this);
        FWPresenter.getInstance().showTabLayout(View.GONE);
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
    }

    private void initNavigationBar() {
        mTitletxt.setText("确认订单");
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FWPresenter.getInstance().backLastFragment();
            }
        });
    }

    private void initAddressView() {
//        mNameTxt.setText("");
//        mAddressTxt.setText("");
        mAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderPresenter.goAddressPage();
            }
        });
    }

    private void initGoodList() {
        mGoodReclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mGoodReclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mGoodReclerView.setPullRefreshEnabled(false);


        mGoodReclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mGoodReclerView.addItemDecoration(new LinearLayoutDecoration.Builder(getContext()).setHeight(R.dimen.space_1).setColor(getResources().getColor(R.color.gray_bg)).build());
        mGoodsListAdapter = new OrderGoodsListAdapter(getActivity());
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


        mPayReclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPayReclerView.addItemDecoration(new LinearLayoutDecoration.Builder(getContext()).setHeight(R.dimen.space_1).setColor(getResources().getColor(R.color.gray_bg)).build());
        mPayListAdapter = new OrderPaylistAdapter(getActivity());
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

    private void initPriceList()
    {
        List<PriceBean> datas = new ArrayList<>();
        datas.add(PriceBean.createPriceBean("商品总价：",total));
        datas.add(PriceBean.createPriceBean("运费：",8));
        datas.add(PriceBean.createPriceBean("运费减免：",-8));
        datas.add(PriceBean.createPriceBean("优惠减免：",0));

        mPriceReclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mPriceReclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mPriceReclerView.setPullRefreshEnabled(false);


        mPriceReclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPriceReclerView.addItemDecoration(new LinearLayoutDecoration.Builder(getContext()).setHeight(R.dimen.space_1).setColor(getResources().getColor(R.color.gray_bg)).build());
        mPriceAdapter = new OrderPriceAdapter(getActivity());
        mPriceLRecyclerViewAdapter = new LRecyclerViewAdapter(mPriceAdapter);
        mPriceReclerView.setAdapter(mPriceLRecyclerViewAdapter);

        if (mDatas != null) {
            mPriceAdapter.updateData(datas);
        }
        mPriceLRecyclerViewAdapter.notifyDataSetChanged();
        mPriceLRecyclerViewAdapter.removeFooterView(mPriceLRecyclerViewAdapter.getFooterView());
    }

    private void initBottom()
    {
        mPricetxt.setText(total+"");
        mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void OnGetAddressListSuccess(List<AddressBean> datas) {

        if(datas!=null && datas.size() > 0){
            for (AddressBean data : datas) {

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
    public void OnDeleteAddressSuccess() {

    }

    @Override
    public void OnDeleteAddressFail() {

    }
}
