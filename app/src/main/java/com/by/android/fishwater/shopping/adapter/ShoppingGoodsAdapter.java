package com.by.android.fishwater.shopping.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.shopping.bean.GoodsBean;
import com.by.android.fishwater.util.HardwareUtil;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.StringUtils;
import com.by.android.fishwater.widget.PriceView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/13.
 */

public class ShoppingGoodsAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<GoodsBean> mDatas = new ArrayList<>();
    private int size = 0;

    public ShoppingGoodsAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<GoodsBean> datas)
    {
        this.mDatas = datas;
        if(mDatas!=null && mDatas.size()> 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShoppingGoodsAdapter.ItemViewHolder(mLayoutInflater.inflate(R.layout.shopping_item_good, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ShoppingGoodsAdapter.ItemViewHolder itemViewHolder = (ShoppingGoodsAdapter.ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            GoodsBean data = mDatas.get(position);
            itemViewHolder.mTitleTxt.setText(data.name);
            itemViewHolder.mPriceTxt.setText(data.price + "");
            itemViewHolder.mSaleCountTxt.setText("销量:"+data.sales);

            if (StringUtils.isNotEmpty(data.thumbnails)) {
                Uri uri = Uri.parse(data.thumbnails);
                itemViewHolder.mShowImg.setImageURI(uri);

            }
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public List<GoodsBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mShowImg;
        TextView mTitleTxt;
        PriceView mPriceTxt;
        TextView mSaleCountTxt;

        public ItemViewHolder(View view) {
            super(view);
            int width = (HardwareUtil.screenWidth - ResourceHelper.getDimen(R.dimen.space_15))/2;
            mShowImg = (SimpleDraweeView) view.findViewById(R.id.img_show);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,width);
            mShowImg.setLayoutParams(params);
            mTitleTxt = (TextView) view.findViewById(R.id.txt_title);
            mPriceTxt = (PriceView) view.findViewById(R.id.txt_price);
            mSaleCountTxt = (TextView) view.findViewById(R.id.txt_salecount);
        }
    }
}

