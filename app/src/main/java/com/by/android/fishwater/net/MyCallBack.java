package com.by.android.fishwater.net;

import android.util.Log;

import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.util.ToastUtil;

import org.xutils.common.Callback;

/**
 * Created by by.huang on 2016/10/10.
 */
public class MyCallBack<ResultType> implements Callback.CommonCallback<ResultType> {

    @Override
    public void onSuccess(ResultType result) {
        //请求成功的逻辑处理
        BaseBean baseBean = (BaseBean)result;
        int code = baseBean.status;
        String msg = baseBean.msg;
        Log.i("by","返回码 = "+code + " 信息 = "+msg);
        checkCode(code,msg);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //请求网络失败的逻辑处理
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }


    private void checkCode(int code,String msg) {
        if(code == ErrorCode.Code_Success) {
            //请求成功
        }
        else {
            ToastUtil.show(msg);
        }
    }
}