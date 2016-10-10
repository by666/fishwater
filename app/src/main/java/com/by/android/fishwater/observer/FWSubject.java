package com.by.android.fishwater.observer;

/**
 * Created by by.huang on 2016/10/10.
 */
public interface FWSubject {

    /**
     * 增加订阅者
     * @param observer
     */
    public void addObserver(FWObserver observer);

    /**
     * 删除订阅者
     * @param observer
     */
    public void removeObserver(FWObserver observer);

    /**
     * 通知订阅者更新消息
     */
    public void notifyUpdate(Object object);
}
