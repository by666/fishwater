package com.by.android.fishwater.observer;

/**
 * Created by by.huang on 2016/10/10.
 */
public interface FWSubject {

    /**
     * 增加订阅者
     * @param data
     */
    public void addObserver(ObserverData data);

    /**
     * 删除订阅者
     * @param key
     */
    public void removeObserver(String key);

    /**
     * 通知订阅者更新消息
     */
    public void notifyUpdate(String key,Object object);
}
