package com.by.android.fishwater.order.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.buycar.bean.BuycarBean;
import com.by.android.fishwater.buycar.presenter.BuycarPresenter;
import com.by.android.fishwater.util.StringUtils;
import com.by.android.fishwater.widget.PriceView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/27.
 */

public class OrderGoodsListAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<BuycarBean> mDatas = new ArrayList<>();
    private int size = 0;

    public OrderGoodsListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<BuycarBean> datas) {
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.item_order_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            final BuycarBean data = mDatas.get(position);
            itemViewHolder.mNameTxt.setText(data.name);
            itemViewHolder.mPriceView.setText(data.price + "");
            itemViewHolder.mCountTxt.setText("数量：" + data.count);

            if (StringUtils.isNotEmpty(data.imgUrl)) {
                Uri uri = Uri.parse(data.imgUrl);
                itemViewHolder.mShowImg.setAspectRatio(1 / 0.58f);
                itemViewHolder.mShowImg.setImageURI(uri);
            }

        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public List<BuycarBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mShowImg;
        TextView mNameTxt;
        TextView mCountTxt;
        PriceView mPriceView;

        public ItemViewHolder(View view) {
            super(view);
            mShowImg = (SimpleDraweeView) view.findViewById(R.id.img_show);
            mNameTxt = (TextView) view.findViewById(R.id.txt_name);
            mCountTxt = (TextView) view.findViewById(R.id.txt_count);
            mPriceView = (PriceView) view.findViewById(R.id.txt_price);

        }
    }
}