package com.by.android.fishwater.emoji;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Copyright 2015 Jacks Blog(blog.dreamtobe.cn).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by Jacksgong on 15/6/29.
 * <p/>
 * 处理 panel -> keybord
 */
public class BasePanelRootLayout extends LinearLayout {

    private boolean mIsHide = false;

    public BasePanelRootLayout(Context context) {
        super(context);
    }

    public BasePanelRootLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePanelRootLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mIsHide) {
            setVisibility(View.GONE);
            if (hideListener != null) {
                hideListener.onHide();
            }
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void setIsHide(final boolean isHide) {
        this.mIsHide = isHide;
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.VISIBLE) {
            this.mIsHide = false;
        }
        super.setVisibility(visibility);
    }

    public boolean isIsHide() {
        return this.mIsHide;
    }

    private HideListener hideListener;

    public HideListener getHideListener() {
        return hideListener;
    }

    public void setHideListener(HideListener hideListener) {
        this.hideListener = hideListener;
    }

    public interface HideListener {
        public void onHide();
    }
}
