package com.by.android.fishwater.homepage.view;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.by.android.fishwater.R;
import com.by.android.fishwater.observer.FWObserverManager;
import com.by.android.fishwater.observer.FWObserver;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by by.huang on 2016/10/9.
 */

@ContentView(R.layout.page_home)
public class HomePage extends Fragment implements IHomePageInterface{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
