package com.by.android.fishwater.shopping.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.buycar.bean.BuycarBean;
import com.by.android.fishwater.buycar.view.BuycarPage;
import com.by.android.fishwater.shopping.adapter.ShoppingGoodsAdapter;
import com.by.android.fishwater.shopping.bean.CategoryBean;
import com.by.android.fishwater.shopping.bean.GoodsBean;
import com.by.android.fishwater.shopping.presenter.ShoppingPresenter;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.view.GridLayoutDecoration;
import com.by.android.fishwater.view.AlphaImageView;
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

import static com.by.android.fishwater.R.string.finish;

/**
 * Created by by.huang on 2016/10/24.
 */

public class ShoppingSearchPage extends FWActivity implements IShoppingPageInterface {
    private ShoppingPresenter mShoppingPresenter;

    private ShoppingGoodsAdapter mListAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<GoodsBean> mCuurentDatas = new ArrayList<>();
    private boolean isRequest = false;
    private int mCategory = -1;
    private String mTitle;

    @ViewInject(R.id.recyclerview)
    LRecyclerView mRecyclerView;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.txt_title)
    TextView mTitleTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_shopping_search);
        x.view().inject(this);
        mCategory = getIntent().getIntExtra("category", -1);
        mTitle = getIntent().getStringExtra("title");
        mTitleTxt.setText(mTitle);
        mShoppingPresenter = new ShoppingPresenter(this);
        initView();
        mShoppingPresenter.getGoodListData(true, -1, mCategory);
    }

    private void initView() {
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);  //设置下拉刷新箭头
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        GridLayoutDecoration decoration = new GridLayoutDecoration(ResourceHelper.getDimen(R.dimen.space_5), 2);
        mRecyclerView.addItemDecoration(decoration);

        mListAdapter = new ShoppingGoodsAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mLRecyclerViewAdapter.getFooterView().setVisibility(View.VISIBLE);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                GoodsBean data = mCuurentDatas.get(i);
                Intent intent= new Intent(ShoppingSearchPage.this,GoodsDetailPage.class);
                intent.putExtra("id",data.id);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCuurentDatas.removeAll(mCuurentDatas);
                mShoppingPresenter.getNewGoodsListData(-1, mCategory);
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isRequest) {
                    RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Loading);
                    mShoppingPresenter.getGoodListData(true, -1, mCategory);
                }
                isRequest = true;
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void OnBannerSuccess(List<BannerBean> datas) {

    }

    @Override
    public void OnBannerFail() {

    }

    @Override
    public void OnCategorySuccess(List<CategoryBean> datas) {

    }

    @Override
    public void OnCategoryFail() {

    }

    @Override
    public void OnGoodsSuccess(List<GoodsBean> datas, boolean isLoadMore, boolean theEnd) {
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
    public void OnGoodsFail() {

    }
}
