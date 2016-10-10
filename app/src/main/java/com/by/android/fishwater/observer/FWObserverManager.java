package com.by.android.fishwater.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/10.
 */

public class FWObserverManager implements FWSubject {

    private List<FWObserver> observers = new ArrayList<FWObserver>();

    private static FWObserverManager mInstance;

    public static FWObserverManager getIntance()
    {
        if(mInstance == null)
        {
            synchronized (FWObserverManager.class)
            {
                if(mInstance == null)
                {
                    mInstance = new FWObserverManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void addObserver(FWObserver observer) {
        if (observer!=null) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(FWObserver observer) {
        if (observer!=null) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyUpdate(Object object) {
        if (observers!=null && observers.size() > 0) {
            for (FWObserver observer : observers) {
                observer.update(object);
            }
        }
    }

}