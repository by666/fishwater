package com.by.android.fishwater.shopping.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.shopping.bean.CategoryBean;
import com.by.android.fishwater.shopping.presenter.ShoppingPresenter;
import com.by.android.fishwater.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/24.
 */

public class GoodsTagsAdapter extends RecyclerView.Adapter {

    private List<String> mDatas = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private int size = 0;

    public GoodsTagsAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateDatas(List<String> datas) {
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.shopping_item_category, parent, false);
        return new GoodsTagsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsTagsAdapter.ItemViewHolder itemViewHolder = (GoodsTagsAdapter.ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            itemViewHolder.itemView.setTag(position);
            String data = mDatas.get(position);
            itemViewHolder.mTitleTxt.setText(data);
            Uri uri = Uri.parse("res:///" + R.drawable.record_window_list_items_ms_selected);
            itemViewHolder.mShowImg.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public List<String> getDatas() {
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
