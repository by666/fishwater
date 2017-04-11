package com.by.android.fishwater.order.adapter;

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
import com.by.android.fishwater.observer.FWObserver;
import com.by.android.fishwater.observer.FWObserverManager;
import com.by.android.fishwater.observer.ObserverData;
import com.by.android.fishwater.order.bean.AddressBean;
import com.by.android.fishwater.order.presenter.OrderPresenter;
import com.by.android.fishwater.order.view.AddressEditPage;
import com.by.android.fishwater.util.ResourceHelper;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/31.
 */

public class AddressListAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<AddressBean> mDatas = new ArrayList<>();
    private int size = 0;
    private AddressBean mSelectData;
    private int mSelectPosition;
    private OrderPresenter mOrderPrensent;
    private FWActivity mActivity;

    public AddressListAdapter(FWActivity activity, OrderPresenter presenter) {
        this.mActivity = activity;
        mLayoutInflater = LayoutInflater.from(activity);
        this.mOrderPrensent = presenter;
    }

    public void updateData(List<AddressBean> datas) {
        this.mDatas = datas;
        size = mDatas.size();
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddressListAdapter.ItemViewHolder(mLayoutInflater.inflate(R.layout.item_address, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final AddressListAdapter.ItemViewHolder itemViewHolder = (AddressListAdapter.ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            final AddressBean data = mDatas.get(position);
            itemViewHolder.mNameTxt.setText(data.name);
            String address = AddressBean.getAddress(data.province, data.city, data.area) + data.address;
            itemViewHolder.mAddressTxt.setText(address);
            itemViewHolder.mPhoneTxt.setText(data.phone);
            if (data.isDefault == 0) {
                itemViewHolder.mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_uncheck));
                itemViewHolder.mSelectTxt.setTextColor(ResourceHelper.getColor(R.color.color_unselect));
            } else {
                mSelectData = data;
                mSelectPosition = position;
                itemViewHolder.mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_checked));
                itemViewHolder.mSelectTxt.setTextColor(ResourceHelper.getColor(R.color.color_select));
            }
            itemViewHolder.mSelectImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.isDefault == 0) {
                        if (mSelectData == null) {
                            data.isDefault = 1;
                            notifyDataSetChanged();
                        }else {
                            mSelectData.isDefault = 0;
                            notifyItemChanged(mSelectPosition);
                            data.isDefault = 1;
                            itemViewHolder.mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_checked));
                            itemViewHolder.mSelectTxt.setTextColor(ResourceHelper.getColor(R.color.color_select));
                            mSelectData = data;
                            mSelectPosition = position;
                        }
                        mOrderPrensent.saveAddress(data);
                    }
                }
            });

            itemViewHolder.mEditTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity,AddressEditPage.class);
                    intent.putExtra("addressbean",data);
                    mActivity.startActivity(intent);
                }
            });

            itemViewHolder.mDeleteTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOrderPrensent.deleteAddress(mDatas, data);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }


    public List<AddressBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mNameTxt;
        TextView mAddressTxt;
        TextView mPhoneTxt;
        TextView mSelectTxt;
        SimpleDraweeView mSelectImg;
        TextView mEditTxt;
        TextView mDeleteTxt;

        public ItemViewHolder(View view) {
            super(view);
            mNameTxt = (TextView) view.findViewById(R.id.txt_name);
            mAddressTxt = (TextView) view.findViewById(R.id.txt_address);
            mPhoneTxt = (TextView) view.findViewById(R.id.txt_phone);
            mSelectImg = (SimpleDraweeView) view.findViewById(R.id.img_select);
            mSelectTxt = (TextView) view.findViewById(R.id.title_select);
            mEditTxt = (TextView) view.findViewById(R.id.txt_edit);
            mDeleteTxt = (TextView) view.findViewById(R.id.txt_delete);

        }
    }
}
