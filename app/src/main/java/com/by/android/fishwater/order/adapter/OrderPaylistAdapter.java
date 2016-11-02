package com.by.android.fishwater.order.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.buycar.bean.BuycarBean;
import com.by.android.fishwater.order.bean.PayBean;
import com.by.android.fishwater.util.StringUtils;
import com.by.android.fishwater.widget.PriceView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by by.huang on 2016/10/31.
 */

public class OrderPaylistAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<PayBean> mDatas = new ArrayList<>();
    private int size = 0;

    public OrderPaylistAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<PayBean> datas) {
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderPaylistAdapter.ItemViewHolder(mLayoutInflater.inflate(R.layout.item_order_pay, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final OrderPaylistAdapter.ItemViewHolder itemViewHolder = (OrderPaylistAdapter.ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            final PayBean data = mDatas.get(position);
            itemViewHolder.mNameTxt.setText(data.name);

            if (StringUtils.isNotEmpty(data.url)) {
                Uri uri = Uri.parse(data.url);
                itemViewHolder.mShowImg.setAspectRatio(1 / 0.58f);
                itemViewHolder.mShowImg.setImageURI(uri);
            }

            if (data.isSelect) {
                itemViewHolder.mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_checked));
            } else {
                itemViewHolder.mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_uncheck));
            }

        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public List<PayBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mShowImg;
        TextView mNameTxt;
        SimpleDraweeView mSelectImg;

        public ItemViewHolder(View view) {
            super(view);
            mShowImg = (SimpleDraweeView) view.findViewById(R.id.img_show);
            mNameTxt = (TextView) view.findViewById(R.id.txt_name);
            mSelectImg = (SimpleDraweeView) view.findViewById(R.id.img_select);

        }
    }
}
