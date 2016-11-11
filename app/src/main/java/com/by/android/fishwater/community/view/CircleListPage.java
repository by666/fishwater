package com.by.android.fishwater.community.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.community.adapter.CircleAdapter;
import com.by.android.fishwater.community.bean.CircleBean;
import com.by.android.fishwater.community.bean.ForumBean;
import com.by.android.fishwater.view.AlphaImageView;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by by.huang on 2016/11/11.
 */

public class CircleListPage extends FWActivity{

    @ViewInject(R.id.txt_title)
    TextView mTitletxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.recyclerview_circle)
    LRecyclerView mCircleRecyclerView;

    private static String ExtraList = "extra_list";
    private static String ExtraTitle = "extra_title";

    private CircleAdapter mCircleAdapter;
    private LRecyclerViewAdapter mCircleLRecyclerViewAdapter;
    private List<CircleBean> mDatas;
    private String title;


    public static void show(FWActivity activity,List<CircleBean> datas,String title)
    {
        Intent intent = new Intent(activity,CircleListPage.class);
        intent.putExtra(ExtraList, (Serializable) datas);
        intent.putExtra(ExtraTitle,title);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_circlelist);
        x.view().inject(this);
        mDatas = (List<CircleBean>) getIntent().getSerializableExtra(ExtraList);
        title= getIntent().getStringExtra(ExtraTitle);
        initView();
    }

    private void initView()
    {
        initNavigationBar();
        initRecycleView();
    }

    private void initNavigationBar() {
        mTitletxt.setText(title);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecycleView()
    {
        mCircleRecyclerView.setPullRefreshEnabled(false);
        mCircleRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCircleAdapter = new CircleAdapter(this);
        mCircleLRecyclerViewAdapter = new LRecyclerViewAdapter(mCircleAdapter);
        mCircleRecyclerView.setAdapter(mCircleLRecyclerViewAdapter);

        mCircleLRecyclerViewAdapter.removeFooterView(mCircleLRecyclerViewAdapter.getFooterView());
        mCircleLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });
        mCircleAdapter.updateData(mDatas);
        mCircleLRecyclerViewAdapter.notifyDataSetChanged();
    }
}
