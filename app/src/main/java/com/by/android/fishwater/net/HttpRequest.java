package com.by.android.fishwater.net;

import com.by.android.fishwater.account.AccountManage;
import com.by.android.fishwater.util.DeviceInfo;
import com.by.android.fishwater.util.SystemHelper;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * Created by by.huang on 2016/10/10.
 */
public class HttpRequest {


    /**
     * 发送get请求
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable Get(String url, Map<String, String> map, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (null != map) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        Callback.Cancelable Cancelable = x.http().get(params, callback);
        return Cancelable;
    }

    /**
     * 发送post请求
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable Post(String url, Map<String, Object> map, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        DeviceInfo deviceInfo = SystemHelper.getDeviceInfo();
        String sessionId = AccountManage.getInstance().getSessionId();
        if (sessionId == null) {
            sessionId = "";
        }
        map.put("deviceid",deviceInfo.deviceId);
        map.put("uuid",deviceInfo.uuid);
        map.put("sessionid",sessionId);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        Callback.Cancelable Cancelable = x.http().post(params, callback);
        return Cancelable;
    }


    /**
     * 上传文件
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable UpLoadFile(String url, Map<String, Object> map, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        params.setMultipart(true);
        Callback.Cancelable Cancelable = x.http().get(params, callback);
        return Cancelable;
    }

    /**
     * 下载文件
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable DownLoadFile(String url, String filepath, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        Callback.Cancelable Cancelable = x.http().get(params, callback);
        return Cancelable;
    }
}



