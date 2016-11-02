package com.by.android.fishwater.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.order.bean.PayBean;
import com.by.android.fishwater.order.bean.PriceBean;
import com.by.android.fishwater.widget.PriceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/31.
 */

public class OrderPriceAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<PriceBean> mDatas = new ArrayList<>();
    private int size = 0;

    public OrderPriceAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<PriceBean> datas) {
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderPriceAdapter.ItemViewHolder(mLayoutInflater.inflate(R.layout.item_order_price, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final OrderPriceAdapter.ItemViewHolder itemViewHolder = (OrderPriceAdapter.ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            final PriceBean data = mDatas.get(position);
            itemViewHolder.mNameTxt.setText(data.name);
            itemViewHolder.mPriceTxt.setText(data.price+"");

        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public List<PriceBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mNameTxt;
        PriceView mPriceTxt;

        public ItemViewHolder(View view) {
            super(view);
            mPriceTxt = (PriceView) view.findViewById(R.id.txt_price);
            mNameTxt = (TextView) view.findViewById(R.id.txt_name);

        }
    }
}

