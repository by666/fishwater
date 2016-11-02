package com.by.android.fishwater.order.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.order.adapter.AddressListAdapter;
import com.by.android.fishwater.order.adapter.OrderPriceAdapter;
import com.by.android.fishwater.order.bean.AddressBean;
import com.by.android.fishwater.view.AlphaImageView;
import com.by.android.fishwater.view.LinearLayoutDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by by.huang on 2016/10/31.
 */

@ContentView(R.layout.page_address)
public class AddressPage extends Fragment{

    @ViewInject(R.id.txt_title)
    TextView mTitletxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.recyclerview_address)
    LRecyclerView mAddressRecyclerView;


    private List<AddressBean> datas;
    private AddressListAdapter mListAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FWPresenter.getInstance().showTabLayout(View.GONE);
        initView();
    }

    private void initView()
    {
        initNavigationBar();
    }

    private void initNavigationBar()
    {
        mTitletxt.setText("选择收获地址");
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FWPresenter.getInstance().backLastFragment();
            }
        });
    }

    private void initAddressList(List<AddressBean> datas)
    {
        mAddressRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mAddressRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mAddressRecyclerView.setPullRefreshEnabled(false);


        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAddressRecyclerView.addItemDecoration(new LinearLayoutDecoration.Builder(getContext()).setHeight(R.dimen.space_1).setColor(getResources().getColor(R.color.gray_bg)).build());
        mListAdapter = new AddressListAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);
        mAddressRecyclerView.setAdapter(mLRecyclerViewAdapter);

        if (datas != null) {
            mListAdapter.updateData(datas);
        }
        mLRecyclerViewAdapter.notifyDataSetChanged();
        mLRecyclerViewAdapter.removeFooterView(mLRecyclerViewAdapter.getFooterView());
    }
}
