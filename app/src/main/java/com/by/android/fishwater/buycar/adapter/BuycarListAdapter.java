package com.by.android.fishwater.buycar.adapter;

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
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.util.StringUtils;
import com.by.android.fishwater.widget.PriceView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/27.
 */

public class BuycarListAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<BuycarBean> mDatas = new ArrayList<>();
    private int size = 0;
    private BuycarPresenter mBuycarPresenter;

    public BuycarListAdapter(Context context, BuycarPresenter buycarPresenter) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mBuycarPresenter = buycarPresenter;
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
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.item_buycar, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            final BuycarBean data = mDatas.get(position);
            itemViewHolder.mNameTxt.setText(data.name);
            itemViewHolder.mPriceView.setText(data.price + "");
            itemViewHolder.mCountTxt.setText("数量：" + data.count);
            if (data.isSelect) {
                itemViewHolder.mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_checked));
            } else {
                itemViewHolder.mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_uncheck));
            }
            itemViewHolder.mSelectLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.isSelect) {
                        itemViewHolder.mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_uncheck));
                    } else {
                        itemViewHolder.mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_checked));
                    }
                    data.isSelect = !data.isSelect;
                    mBuycarPresenter.updatePrice();
                }
            });

            if (StringUtils.isNotEmpty(data.imgUrl)) {
                Uri uri = Uri.parse(data.imgUrl);
                itemViewHolder.mShowImg.setAspectRatio(1 / 0.58f);
                itemViewHolder.mShowImg.setImageURI(uri);
            }

            itemViewHolder.mNumTxt.setText(data.count+"");
            if (data.isEdit) {
                itemViewHolder.mCountLayout.setVisibility(View.VISIBLE);
                itemViewHolder.mCountTxt.setVisibility(View.GONE);
            } else {
                itemViewHolder.mCountLayout.setVisibility(View.GONE);
                itemViewHolder.mCountTxt.setVisibility(View.VISIBLE);
            }

            itemViewHolder.mAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.count++;
                    itemViewHolder.mCountTxt.setText(data.count+"");
                    itemViewHolder.mNumTxt.setText(data.count+"");
                    mBuycarPresenter.updatePrice();
                }
            });

            itemViewHolder.mReduceBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(data.count == 1) {
                        return;
                    }
                    data.count--;
                    itemViewHolder.mCountTxt.setText(data.count+"");
                    itemViewHolder.mNumTxt.setText(data.count+"");
                    mBuycarPresenter.updatePrice();
                }
            });
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

        View mSelectLayout;
        SimpleDraweeView mSelectImg;
        SimpleDraweeView mShowImg;
        TextView mNameTxt;
        TextView mCountTxt;
        PriceView mPriceView;

        View mCountLayout;
        TextView mNumTxt;
        Button mAddBtn;
        Button mReduceBtn;


        public ItemViewHolder(View view) {
            super(view);
            mSelectLayout = view.findViewById(R.id.layout_select);
            mSelectImg = (SimpleDraweeView) view.findViewById(R.id.img_select);
            mShowImg = (SimpleDraweeView) view.findViewById(R.id.img_show);
            mNameTxt = (TextView) view.findViewById(R.id.txt_name);
            mCountTxt = (TextView) view.findViewById(R.id.txt_count);
            mPriceView = (PriceView) view.findViewById(R.id.txt_price);
            mCountLayout = view.findViewById(R.id.layout_count);
            mNumTxt = (TextView) view.findViewById(R.id.txt_num);
            mAddBtn = (Button) view.findViewById(R.id.btn_add);
            mReduceBtn = (Button) view.findViewById(R.id.btn_reduce);

        }
    }
}