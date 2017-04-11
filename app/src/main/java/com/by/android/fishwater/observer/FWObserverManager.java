package com.by.android.fishwater.observer;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by by.huang on 2016/10/10.
 */

public class FWObserverManager implements FWSubject {

    private List<ObserverData> datas = new ArrayList<>();

    private static FWObserverManager mInstance;

    public static FWObserverManager getIntance() {
        if (mInstance == null) {
            synchronized (FWObserverManager.class) {
                if (mInstance == null) {
                    mInstance = new FWObserverManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void addObserver(ObserverData data) {
        if (data != null) {
            datas.add(data);
        }
    }

    @Override
    public void removeObserver(String key) {
        if (datas != null && datas.size() > 0) {
            for (ObserverData data : datas) {
                if (key.equalsIgnoreCase(data.key)) {
                    datas.remove(data);
                    break;
                }
            }
        }
    }

    @Override
    public void notifyUpdate(String key, Object object) {
        if (datas != null && datas.size() > 0) {
            for (ObserverData data : datas) {
                if (key.equalsIgnoreCase(data.key)) {
                    data.observer.update(object);
                    break;
                }
            }
        }
    }

}