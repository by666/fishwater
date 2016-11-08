package com.by.android.fishwater.order.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.user.view.UserInfoPage;
import com.by.android.fishwater.mine.adapter.FansFollowAdapter;
import com.by.android.fishwater.mine.bean.FansFollowerBean;
import com.by.android.fishwater.order.adapter.OrderDetailAdapter;
import com.by.android.fishwater.order.bean.OrderDetailBean;
import com.by.android.fishwater.order.presenter.OrderPresenter;
import com.by.android.fishwater.view.AlphaImageView;
import com.by.android.fishwater.view.LinearLayoutDecoration;
import com.by.android.fishwater.view.TabView;
import com.by.android.fishwater.view.TabViewListener;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/11/8.
 */

public class OrderDetailPage extends FWActivity implements View.OnClickListener, IOrderDetailInterface {


    @ViewInject(R.id.txt_title)
    TextView mTitletxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.tabview)
    TabView mTabView;

    @ViewInject(R.id.recyclerview_orderdetail)
    LRecyclerView mRecyclerView;

    @ViewInject(R.id.layout_nodata)
    View mNoDataLayout;

    private OrderDetailAdapter mListAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<OrderDetailBean> mCuurentDatas = new ArrayList<>();
    private boolean isRequest = false;

    private OrderPresenter mOrderPresenter;
    private int mPosition = 0;


    public static void show(FWActivity activity) {
        Intent intent = new Intent(activity, OrderDetailPage.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_orderdetail);
        x.view().inject(this);
        mOrderPresenter = new OrderPresenter(this);
        initView();
    }

    private void initView() {
        initNavigationBar();
        initTabView();
        initRecyclerView();
        mOrderPresenter.requestNewOrderDetail(mPosition + 1);
    }

    private void initNavigationBar() {
        mTitletxt.setText("订单详情");
        mBackBtn.setOnClickListener(this);
    }

    private void initTabView() {
        List<String> titles = new ArrayList<>();
        titles.add("待付款");
        titles.add("待发货");
        titles.add("待收货");
        titles.add("待评价");
        mTabView.setDatas(titles);
        mTabView.setListener(new TabViewListener() {
            @Override
            public void OnTabClicker(int position) {
                mPosition = position;
                mNoDataLayout.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                mOrderPresenter.requestNewOrderDetail(mPosition + 1);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);  //设置下拉刷新箭头
        mRecyclerView.setPullRefreshEnabled(true);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new LinearLayoutDecoration.Builder(this).setHeight(R.dimen.space_1).setColor(getResources().getColor(R.color.gray_bg)).build());

        mListAdapter = new OrderDetailAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mLRecyclerViewAdapter.getFooterView().setVisibility(View.VISIBLE);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {

            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCuurentDatas.removeAll(mCuurentDatas);
                mOrderPresenter.requestNewOrderDetail(mPosition + 1);
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isRequest) {
                    RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Loading);
                    mOrderPresenter.requestOrderDetail(mPosition + 1, true);
                }
                isRequest = true;
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == mBackBtn) {
            finish();
        }
    }

    @Override
    public void OnRequstOrderDetailSuccess(List<OrderDetailBean> datas, boolean isLoadMore, boolean theEnd) {
        isRequest = false;
        mCuurentDatas = datas;
        if (mCuurentDatas == null || mCuurentDatas.size() == 0) {
            mNoDataLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            return;
        }
        mNoDataLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        if (theEnd) {
            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
        }
        mListAdapter.updateData(datas);
        mRecyclerView.refreshComplete();
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnRequstOrderDetailFail() {

    }
}
