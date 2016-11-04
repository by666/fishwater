package com.by.android.fishwater.mine.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.view.AlphaImageView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by by.huang on 2016/11/5.
 */

public class FansPage extends FWActivity {

    @ViewInject(R.id.txt_title)
    TextView mTitletxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_fans);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        initNavigationBar();
    }

    private void initNavigationBar() {
        mTitletxt.setText("我的粉丝");
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
