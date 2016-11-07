package com.by.android.fishwater.order.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.observer.FWObserver;
import com.by.android.fishwater.observer.FWObserverManager;
import com.by.android.fishwater.observer.ObserverData;
import com.by.android.fishwater.order.adapter.AddressListAdapter;
import com.by.android.fishwater.order.bean.AddressBean;
import com.by.android.fishwater.order.bean.OrderBean;
import com.by.android.fishwater.order.presenter.OrderPresenter;
import com.by.android.fishwater.view.AlphaImageView;
import com.by.android.fishwater.view.LinearLayoutDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by by.huang on 2016/10/31.
 */

public class AddressPage extends FWActivity implements IOrderInterface ,FWObserver{

    @ViewInject(R.id.txt_title)
    TextView mTitletxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.recyclerview_address)
    LRecyclerView mAddressRecyclerView;

    @ViewInject(R.id.btn_addaddress)
    Button mAddAddressBtn;

    private AddressListAdapter mListAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private OrderPresenter mOrderPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_address);
        x.view().inject(this);
        mOrderPresenter = new OrderPresenter(this);
        FWObserverManager.getIntance().addObserver(ObserverData.build(ObserverData.Update_AddressList,this));
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FWObserverManager.getIntance().removeObserver(ObserverData.build(ObserverData.Update_AddressList,this));
    }

    private void initView() {
        initNavigationBar();
        mOrderPresenter.getAddressList();

        mAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressPage.this, AddressEditPage.class);
                startActivity(intent);
            }
        });
    }

    private void initNavigationBar() {
        mTitletxt.setText("选择收获地址");
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initAddressList(List<AddressBean> datas) {
        mAddressRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mAddressRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mAddressRecyclerView.setPullRefreshEnabled(false);


        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddressRecyclerView.addItemDecoration(new LinearLayoutDecoration.Builder(this).setHeight(R.dimen.space_4).setColor(getResources().getColor(R.color.gray_bg)).build());
        mListAdapter = new AddressListAdapter(this, mOrderPresenter);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);
        mAddressRecyclerView.setAdapter(mLRecyclerViewAdapter);

        if (datas != null && datas.size() > 0) {
            mListAdapter.updateData(datas);
        }
        mLRecyclerViewAdapter.notifyDataSetChanged();
        mLRecyclerViewAdapter.removeFooterView(mLRecyclerViewAdapter.getFooterView());
    }

    @Override
    public void OnGetAddressListSuccess(List<AddressBean> datas) {
        initAddressList(datas);
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

        if (datas != null && datas.size() > 0) {
            mListAdapter.updateData(datas);
        }
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnDeleteAddressFail() {

    }

    @Override
    public void OnOrderSuccess(OrderBean data) {

    }

    @Override
    public void OnOrderFail() {

    }

    @Override
    public void update(Object object) {
        mOrderPresenter.getAddressList();
    }
}
