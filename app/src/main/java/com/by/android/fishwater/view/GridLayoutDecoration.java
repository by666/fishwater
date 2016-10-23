package com.by.android.fishwater.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by by.huang on 2016/10/24.
 */

public class GridLayoutDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int count;

    public GridLayoutDecoration(int space,int count ) {
        this.space = space;
        this.count = count;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        int position = parent.getChildLayoutPosition(view);
        if(position == 0 || position == 1) {
            return;
        }
        outRect.left = space;
        outRect.top = space;
        if ((position+1) %count==0) {
            outRect.right = space;
        }
    }

}