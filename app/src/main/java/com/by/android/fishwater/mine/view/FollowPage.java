package com.by.android.fishwater.mine.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.user.view.UserInfoPage;
import com.by.android.fishwater.mine.adapter.FansFollowAdapter;
import com.by.android.fishwater.mine.bean.FansFollowerBean;
import com.by.android.fishwater.mine.presenter.FansFollowerPresenter;
import com.by.android.fishwater.view.AlphaImageView;
import com.by.android.fishwater.view.LinearLayoutDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by by.huang on 2016/11/5.
 */

public class FollowPage extends FWActivity implements IFansFollwerInterface{

    @ViewInject(R.id.txt_title)
    TextView mTitletxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.recyclerview_follow)
    LRecyclerView mRecyclerView;

    private FansFollowerPresenter mPresenter;
    private FansFollowAdapter mListAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<FansFollowerBean> mCuurentDatas = new ArrayList<>();
    private boolean isRequest = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_follow);
        x.view().inject(this);
        mPresenter = new FansFollowerPresenter(this,false);
        initView();
    }

    private void initView() {
        initNavigationBar();
        initRecyclerView();
        mPresenter.getNewListData();
    }

    private void initNavigationBar() {
        mTitletxt.setText("我关注的人");
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView()
    {

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);  //设置下拉刷新箭头
        mRecyclerView.setPullRefreshEnabled(true);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new LinearLayoutDecoration.Builder(this).setHeight(R.dimen.space_1).setColor(getResources().getColor(R.color.gray_bg)).build());

        mListAdapter = new FansFollowAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mLRecyclerViewAdapter.getFooterView().setVisibility(View.VISIBLE);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                FansFollowerBean data = mCuurentDatas.get(i);
                UserInfoPage.show(FollowPage.this,data.userid);
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCuurentDatas.removeAll(mCuurentDatas);
                mPresenter.getNewListData();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isRequest) {
                    RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Loading);
                    mPresenter.getListData(true);
                }
                isRequest = true;
            }
        });

    }

    @Override
    public void OnGetFansFollwerSuccess(List<FansFollowerBean> datas, boolean isLoadMore, boolean theEnd) {
        isRequest = false;
        mCuurentDatas = datas;
        if (theEnd) {
            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
        }
        mListAdapter.updateData(datas);
        mRecyclerView.refreshComplete();
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnGetFansFollwerFail() {

    }
}
