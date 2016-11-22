package com.by.android.fishwater.setting.presenter;

import android.os.AsyncTask;
import android.view.View;

import com.by.android.fishwater.setting.view.ISettingPageInterface;
import com.by.android.fishwater.setting.view.SettingPage;
import com.by.android.fishwater.util.ToastUtil;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;

import java.text.DecimalFormat;

/**
 * Created by by.huang on 2016/11/13.
 */

public class SettingPresenter {


    private ISettingPageInterface mSettingPageInterface;

    public SettingPresenter(ISettingPageInterface settingPageInterface)
    {
        this.mSettingPageInterface = settingPageInterface;
    }

    public String getCacheSize()
    {
        String cacheStr = "0 MB";
        long cacheSize = Fresco.getImagePipelineFactory().getMainDiskStorageCache().getSize();
        if(cacheSize<=0){
            cacheStr = "0 MB";
        }else{
            float cacheSizeTemp1 = (float)(Math.round(Math.round(cacheSize/1024))*10/10);
            float cacheSizeTemp2 = (float)(Math.round(Math.round(cacheSize/1024/1024))*10/10);
            if(cacheSizeTemp1<1){
                cacheStr = cacheSize+" B";
            }else if(((cacheSizeTemp1>=1)&&(cacheSizeTemp2<1))){
                cacheStr = cacheSizeTemp1+" KB";
            }else if(cacheSizeTemp2>=1){
                cacheStr = cacheSizeTemp2+" MB";
            }
        }
        return cacheStr;
    }

    public void clearCache()
    {
        Fresco.getImagePipeline().clearCaches();
        mSettingPageInterface.OnClearSuccess();
    }
}
