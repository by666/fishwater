package com.by.android.fishwater;

import android.app.Application;

import com.by.android.fishwater.database.FWDatabaseManager;
import com.by.android.fishwater.emoji.EmoticonsUtils;
import com.by.android.fishwater.order.bean.address.AreaBean;
import com.by.android.fishwater.order.bean.address.CityBean;
import com.by.android.fishwater.order.bean.address.ProvinceBean;
import com.by.android.fishwater.util.HardwareUtil;
import com.by.android.fishwater.util.MarketChannelManager;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.SettingFlags;
import com.by.android.fishwater.util.StringUtils;
import com.by.android.fishwater.util.SystemHelper;
import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.analytics.MobclickAgent;

import org.xml.sax.SAXException;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import static com.by.android.fishwater.util.Constant.APP_PATH;
import static com.umeng.analytics.MobclickAgent.EScenarioType.E_UM_NORMAL;

/**
 * Created by by.huang on 2016/10/9.
 */
public class FWApplication extends Application {

    public static FWApplication mApplication;
    private List<ProvinceBean> mProvinceDatas = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        mApplication = this;
        x.Ext.init(this);
        x.Ext.setDebug(true);
        Fresco.initialize(this);
        FWDatabaseManager.getInstance().init();
        initUtils();
        initAddressList();
        EmoticonsUtils.initEmoticonsDB(this);
        /**umeng普通统计模式**/
        MobclickAgent.setScenarioType(this, E_UM_NORMAL);

    }

    private void initUtils()
    {
        ResourceHelper.init(this);
        MarketChannelManager.init(this);
        SettingFlags.init(this);
        HardwareUtil.initialize(this);
        SystemHelper.init(this);
        SystemHelper.init(this);
    }


    private void initAddressList() {
        final File file = new File(APP_PATH + "/AreaList.plist");
        if (!file.exists()) {
            InputStream is;
            try {
                is = FWApplication.mApplication.getBaseContext().getAssets().open("AreaList.plist");
                FileOutputStream fos = new FileOutputStream(APP_PATH + "/AreaList.plist");
                byte[] buffer = new byte[400000];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(file);
                        NSArray provinceArray = (NSArray) rootDict.objectForKey("area");
                        int pLength = provinceArray.count();
                        for (int i = 0; i < pLength; i++) {
                            ProvinceBean data = new ProvinceBean();
                            NSDictionary subDic = (NSDictionary) provinceArray.objectAtIndex(i);
                            data.id = StringUtils.parseInt(subDic.objectForKey("id").toString());
                            data.name = subDic.objectForKey("name").toString();
                            NSArray cityArray = (NSArray) subDic.objectForKey("sub");
                            int cLength = cityArray.count();
                            List<CityBean> cityBeens = new ArrayList<>();
                            for (int m = 0; m < cLength; m++) {
                                CityBean cityBean = new CityBean();
                                NSDictionary sub2Dic = (NSDictionary) cityArray.objectAtIndex(m);
                                cityBean.id = StringUtils.parseInt(sub2Dic.objectForKey("id").toString());
                                cityBean.name = sub2Dic.objectForKey("name").toString();
                                NSArray areaArray = (NSArray) sub2Dic.objectForKey("sub");
                                int aLength = areaArray.count();
                                List<AreaBean> areaBeens = new ArrayList<>();
                                for (int n = 0; n < aLength; n++) {
                                    AreaBean areaBean = new AreaBean();
                                    NSDictionary sub3Dic = (NSDictionary) areaArray.objectAtIndex(n);
                                    areaBean.id = StringUtils.parseInt(sub3Dic.objectForKey("id").toString());
                                    areaBean.name = sub3Dic.objectForKey("name").toString();
                                    areaBeens.add(areaBean);
                                }
                                cityBean.areaBeens = areaBeens;
                                cityBeens.add(cityBean);
                            }
                            data.cityBeens = cityBeens;
                            mProvinceDatas.add(data);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (PropertyListFormatException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }
    }

    public List<ProvinceBean> getProvinceDatas()
    {
        return mProvinceDatas;
    }

}
