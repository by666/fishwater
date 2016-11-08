package com.by.android.fishwater.order.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.order.bean.OrderDetailBean;
import com.by.android.fishwater.shopping.bean.GoodsBean;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/31.
 */

public class OrderDetailItemAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private OrderDetailBean mOrderDetailBean;
    private List<GoodsBean> mDatas = new ArrayList<>();
    private int size = 0;
    private final static int WECHAT = 2;
    private final static int ALIPAY = 1;

    public OrderDetailItemAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateData(OrderDetailBean orderDetailBean) {
        this.mOrderDetailBean = orderDetailBean;
        List<GoodsBean> datas = mOrderDetailBean.goods;
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderDetailItemAdapter.ItemViewHolder(mLayoutInflater.inflate(R.layout.item_order_detail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final OrderDetailItemAdapter.ItemViewHolder itemViewHolder = (OrderDetailItemAdapter.ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            final GoodsBean data = mDatas.get(position);
            String payType = "未支付";
            if (mOrderDetailBean.paytype == WECHAT) {
                payType = ResourceHelper.getString(R.string.order_pay_wx_text);
            } else if (mOrderDetailBean.paytype == ALIPAY) {
                payType = ResourceHelper.getString(R.string.order_pay_zfb_text);
            }

            if (StringUtils.isNotEmpty(data.thumbnails)) {
                itemViewHolder.mShowImg.setImageURI(Uri.parse(data.thumbnails));
            }
            itemViewHolder.mTitleTxt.setText(data.name);
            itemViewHolder.mCountTxt.setText(String.format(ResourceHelper.getString(R.string.order_goods_count), data.num));
            itemViewHolder.mOtherTxt.setText(Html.fromHtml(String.format(ResourceHelper.getString(R.string.order_list_items_other), data.price, payType)));

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
        TextView mCountTxt;
        TextView mOtherTxt;

        public ItemViewHolder(View view) {
            super(view);
            mShowImg = (SimpleDraweeView) view.findViewById(R.id.items_order_img);
            mTitleTxt = (TextView) view.findViewById(R.id.items_order_title);
            mCountTxt = (TextView) view.findViewById(R.id.items_order_count);
            mOtherTxt = (TextView) view.findViewById(R.id.items_order_other);

        }
    }
}

