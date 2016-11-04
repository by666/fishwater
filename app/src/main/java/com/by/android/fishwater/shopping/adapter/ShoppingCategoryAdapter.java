package com.by.android.fishwater.shopping.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.shopping.bean.CategoryBean;
import com.by.android.fishwater.shopping.presenter.ShoppingPresenter;
import com.by.android.fishwater.shopping.view.ShoppingSearchPage;
import com.by.android.fishwater.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/24.
 */

public class ShoppingCategoryAdapter extends RecyclerView.Adapter {

    private List<CategoryBean> mDatas = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private ShoppingPresenter mShoppingPresenter;
    private int size = 0;
    private FWActivity mActivity;

    public ShoppingCategoryAdapter(FWActivity context, ShoppingPresenter shoppingPresenter) {
        this.mActivity = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mShoppingPresenter = shoppingPresenter;
    }

    public void updateDatas(List<CategoryBean> datas) {
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.shopping_item_category, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                CategoryBean data = mDatas.get(tag);
                Intent intent = new Intent(mActivity, ShoppingSearchPage.class);
                intent.putExtra("category", data.id);
                intent.putExtra("title", data.title);
                mActivity.startActivity(intent);
            }
        });
        return new ShoppingCategoryAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShoppingCategoryAdapter.ItemViewHolder itemViewHolder = (ShoppingCategoryAdapter.ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            itemViewHolder.itemView.setTag(position);
            CategoryBean data = mDatas.get(position);
            itemViewHolder.mTitleTxt.setText(data.title);

            if (StringUtils.isNotEmpty(data.url)) {
                Uri uri = Uri.parse("res:///" + R.drawable.record_window_list_items_ms_selected);
                itemViewHolder.mShowImg.setImageURI(uri);
            }
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public List<CategoryBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleTxt;
        SimpleDraweeView mShowImg;

        public ItemViewHolder(View view) {
            super(view);
            mTitleTxt = (TextView) view.findViewById(R.id.txt_show);
            mShowImg = (SimpleDraweeView) view.findViewById(R.id.img_show);
        }
    }
}
