package com.by.android.fishwater.mine;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.by.android.fishwater.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by by.huang on 2016/10/13.
 */

@ContentView(R.layout.page_mine)
public class MinePage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
