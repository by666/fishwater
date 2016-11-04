package com.by.android.fishwater.buycar.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.buycar.adapter.BuycarListAdapter;
import com.by.android.fishwater.buycar.bean.BuycarBean;
import com.by.android.fishwater.buycar.presenter.BuycarPresenter;
import com.by.android.fishwater.database.FWDatabaseManager;
import com.by.android.fishwater.order.view.GoodPage;
import com.by.android.fishwater.shopping.view.GoodsDetailPage;
import com.by.android.fishwater.util.ToastUtil;
import com.by.android.fishwater.view.LinearLayoutDecoration;
import com.by.android.fishwater.view.AlphaImageView;
import com.by.android.fishwater.widget.PriceView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by by.huang on 2016/10/13.
 */

public class BuycarPage extends FWActivity implements IBuycarPageInterface {


    @ViewInject(R.id.txt_title)
    TextView mTitleTxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.txt_edit)
    TextView mEditTxt;

    @ViewInject(R.id.recylerview_buycar)
    LRecyclerView mRecyclerView;

    @ViewInject(R.id.layout_select)
    View mSelectLayout;

    @ViewInject(R.id.img_select)
    SimpleDraweeView mSelectImg;

    @ViewInject(R.id.txt_price)
    PriceView mPricetxt;

    @ViewInject(R.id.btn_buy)
    Button mBuyBtn;

    private BuycarPresenter mBuycarPresenter;
    private BuycarListAdapter mListAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<BuycarBean> mCuurentDatas = new ArrayList<>();
    private boolean isSelectAll;
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_buycar);
        x.view().inject(this);
        mBuycarPresenter = new BuycarPresenter(this);
        initView();
    }

    private void initView() {
        initNavigationBar();
        initRecyclerView();
        initBottom();
        updatePrice();
    }

    private void initNavigationBar() {
        mTitleTxt.setText("购物车");
        mEditTxt.setVisibility(View.VISIBLE);
        mEditTxt.setText("编辑");
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEditTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCuurentDatas != null && mCuurentDatas.size() > 0) {
                    if (isEdit) {
                        mEditTxt.setText("编辑");
                        for (BuycarBean data : mCuurentDatas) {
                            data.isEdit = false;
                        }
                        mEditTxt.setTextColor(getResources().getColor(R.color.black));
                    } else {
                        mEditTxt.setText("完成");
                        for (BuycarBean data : mCuurentDatas) {
                            data.isEdit = true;
                        }
                        mEditTxt.setTextColor(getResources().getColor(R.color.order_detail_logistics_text));
                    }
                    mListAdapter.updateData(mCuurentDatas);
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    isEdit = !isEdit;
                }
                else {
                    ToastUtil.show("购物车还没有任何商品！");
                }
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mRecyclerView.setPullRefreshEnabled(true);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new LinearLayoutDecoration.Builder(this).setHeight(R.dimen.space_1).setColor(getResources().getColor(R.color.gray_bg)).build());
        mListAdapter = new BuycarListAdapter(this, mBuycarPresenter);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if(!mCuurentDatas.get(i).isEdit)
                {
                    BuycarBean data = mCuurentDatas.get(i);
                    Intent intent= new Intent(BuycarPage.this,GoodsDetailPage.class);
                    intent.putExtra("id",data.id);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.refreshComplete();
            }
        });

        mCuurentDatas = FWDatabaseManager.getInstance().findAll();

        if (mCuurentDatas != null) {
            mListAdapter.updateData(mCuurentDatas);
        }
        mLRecyclerViewAdapter.notifyDataSetChanged();
        mLRecyclerViewAdapter.getFooterView().setVisibility(View.GONE);
    }


    private void initBottom() {
        mSelectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelectAll) {
                    mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_uncheck));
                    for (BuycarBean data : mCuurentDatas) {
                        data.isSelect = false;
                    }
                } else {
                    mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_checked));
                    for (BuycarBean data : mCuurentDatas) {
                        data.isSelect = true;
                    }
                }
                if (mCuurentDatas != null) {
                    mListAdapter.updateData(mCuurentDatas);
                    updatePrice();
                }
                mLRecyclerViewAdapter.notifyDataSetChanged();
                isSelectAll = !isSelectAll;
            }
        });

        mPricetxt.setText("0");

        mBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuycarPage.this, GoodPage.class));
            }
        });
    }

    private void updatePrice() {
        mCuurentDatas = mListAdapter.getDatas();
        float price = 0;
        int count = 0;
        if (mCuurentDatas != null && mCuurentDatas.size() > 0) {
            for (BuycarBean data : mCuurentDatas) {
                if (data.isSelect) {
                    count++;
                    price += data.count * data.price;
                }
            }
        }
        mPricetxt.setText(price + "");
        if (count == 0) {
            mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_uncheck));
            mBuyBtn.setText("去结算");
            mBuyBtn.setEnabled(false);
            mBuyBtn.setBackgroundColor(getResources().getColor(R.color.home_text_default_color));
        } else {
            if (count == mCuurentDatas.size()) {
                mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_checked));
            }
            mBuyBtn.setText("去结算(" + count + ")");
            mBuyBtn.setEnabled(true);
            mBuyBtn.setBackgroundResource(R.drawable.buy_panel_buy_text);
        }
    }

    @Override
    public void OnUpdatePrice() {
        updatePrice();
    }
}
