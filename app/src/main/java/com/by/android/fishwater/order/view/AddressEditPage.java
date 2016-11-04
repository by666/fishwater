package com.by.android.fishwater.order.view;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.FWApplication;
import com.by.android.fishwater.R;
import com.by.android.fishwater.observer.FWObserver;
import com.by.android.fishwater.observer.FWObserverManager;
import com.by.android.fishwater.observer.ObserverData;
import com.by.android.fishwater.order.bean.AddressBean;
import com.by.android.fishwater.order.bean.address.AddressBaseBean;
import com.by.android.fishwater.order.bean.address.AreaBean;
import com.by.android.fishwater.order.bean.address.CityBean;
import com.by.android.fishwater.order.bean.address.ProvinceBean;
import com.by.android.fishwater.order.presenter.OrderPresenter;
import com.by.android.fishwater.util.DeviceManager;
import com.by.android.fishwater.util.StringUtils;
import com.by.android.fishwater.view.AlphaImageView;
import com.by.android.fishwater.view.AlphaTextView;
import com.by.android.fishwater.widget.WheelView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/11/3.
 */

public class AddressEditPage extends FWActivity implements WheelView.OnWheelViewListener, View.OnClickListener, IOrderInterface, View.OnFocusChangeListener {

    @ViewInject(R.id.txt_title)
    TextView mTitleTxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.txt_edit)
    TextView mEditTxt;

    @ViewInject(R.id.et_name)
    EditText mNameEdit;

    @ViewInject(R.id.et_phone)
    EditText mPhoneEdit;

    @ViewInject(R.id.tv_area)
    TextView mAreaBtn;

    @ViewInject(R.id.et_address)
    EditText mAddressEdit;

    @ViewInject(R.id.iv_name_del)
    AlphaImageView mNameDel;

    @ViewInject(R.id.iv_phone_del)
    AlphaImageView mPhoneDel;

    @ViewInject(R.id.layout_pca)
    View mPcaLayout;

    @ViewInject(R.id.wv_province)
    WheelView mProvinceWheel;

    @ViewInject(R.id.wv_city)
    WheelView mCityWheel;

    @ViewInject(R.id.wv_district)
    WheelView mAreaWheel;

    @ViewInject(R.id.area_btn_ok)
    AlphaTextView mOkBtn;

    @ViewInject(R.id.layout_select)
    View mSelectLayout;

    @ViewInject(R.id.img_select)
    SimpleDraweeView mSelectImg;

    private OrderPresenter mOrderPresenter;
    private AddressBean mAddressBean;
    private List<ProvinceBean> mProvinceList = new ArrayList<>();
    private List<CityBean> mCitiesList = new ArrayList<>();
    private List<AreaBean> mAreaList = new ArrayList<>();

    protected String mCurrentProvinceName = "";
    protected int mCurrentProvinceId;
    protected String mCurrentCityName = "";
    protected int mCurrentCityId;
    protected String mCurrentDistrictName = "";
    protected int mCurrentDistrictId;
    protected int mDefault = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_address_edit);
        x.view().inject(this);
        mOrderPresenter = new OrderPresenter(this);
        try {
            mAddressBean = (AddressBean) getIntent().getSerializableExtra("addressbean");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mAddressBean == null) {
            mAddressBean = AddressBean.getDefaultAddressBean();
        }
        mProvinceList = FWApplication.mApplication.getProvinceDatas();
        initView();

    }

    private void initView() {
        initNavigationBar();
        initContent();
        initWheel();
    }

    private void initNavigationBar() {
        mTitleTxt.setText("收获地址");
        mEditTxt.setVisibility(View.VISIBLE);
        mEditTxt.setText("保存");
        mBackBtn.setOnClickListener(this);
        mEditTxt.setOnClickListener(this);
    }

    private void initContent() {

        if (mAddressBean != null) {
            mNameEdit.setText(mAddressBean.name);
            mPhoneEdit.setText(mAddressBean.phone);
            mAreaBtn.setText(AddressBean.getAddress(mAddressBean.province, mAddressBean.city, mAddressBean.area));
            mAddressEdit.setText(mAddressBean.address);
            mDefault = mAddressBean.isDefault;
        }
        mNameEdit.setOnFocusChangeListener(this);
        mPhoneEdit.setOnFocusChangeListener(this);
        mAddressEdit.setOnClickListener(this);
        mSelectLayout.setOnClickListener(this);
        if (mDefault == 0) {
            mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_uncheck));
        } else {
            mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_checked));

        }
        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    mNameDel.setVisibility(View.VISIBLE);
                } else {
                    mNameDel.setVisibility(View.GONE);
                }
            }
        });

        mPhoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    mPhoneDel.setVisibility(View.VISIBLE);
                } else {
                    mPhoneDel.setVisibility(View.GONE);
                }
            }
        });

        mNameDel.setOnClickListener(this);
        mPhoneDel.setOnClickListener(this);
        mAreaBtn.setOnClickListener(this);
    }

    private void initWheel() {
        mProvinceWheel.setOnWheelViewListener(this);
        mCityWheel.setOnWheelViewListener(this);
        mAreaWheel.setOnWheelViewListener(this);

        List<String> provinceList = new ArrayList<>();
        for (ProvinceBean data : mProvinceList) {
            provinceList.add(data.name);
        }
        mProvinceWheel.setItemList(provinceList);

        mProvinceWheel.setCurrentPosition(getPositionByList(mAddressBean.province + "", mProvinceList));
        mCityWheel.setCurrentPosition(getPositionByList(mAddressBean.city + "", mCitiesList));
        mAreaWheel.setCurrentPosition(getPositionByList(mAddressBean.area + "", mAreaList));

        mOkBtn.setOnClickListener(this);
    }

    private <T> int getPositionByList(String provinceId, List<T> list) {
        int pos = 0;
        for (T model : list) {
            if (StringUtils.parseInt(provinceId) == ((AddressBaseBean) model).id || StringUtils.parseInt(provinceId) == 0) {
                break;
            }
            pos++;
        }
        return pos;
    }


    @Override
    public void onSelected(WheelView wView, int selectedIndex, String item) {
        if (wView == mProvinceWheel) {
            updateProvince(selectedIndex);
            mCityWheel.setCurrentPosition(0);
        } else if (wView == mCityWheel) {
            updateCities(selectedIndex);
            mAreaWheel.setCurrentPosition(0);
        } else if (wView == mAreaWheel) {
            updateDistrict(selectedIndex);
        }
        String address = mCurrentProvinceName + mCurrentCityName + mCurrentDistrictName;
        mAreaBtn.setText(address);
    }

    private void updateProvince(int pos) {
        ProvinceBean provinceModel = mProvinceList.get(pos);
        mCitiesList.clear();
        mCitiesList.addAll(provinceModel.cityBeens);
        List<String> citiesList = new ArrayList<>();
        for (CityBean cityModel : mCitiesList) {
            citiesList.add(cityModel.name);
        }
        mCityWheel.setItemList(citiesList);
        mCurrentProvinceName = provinceModel.name;
        mCurrentProvinceId = provinceModel.id;
    }

    private void updateCities(int pos) {
        CityBean cityModel = mCitiesList.get(pos);
        mAreaList.clear();
        mAreaList.addAll(cityModel.areaBeens);
        List<String> districtList = new ArrayList<>();
        for (AreaBean districtModel : mAreaList) {
            districtList.add(districtModel.name);
        }
        mAreaWheel.setItemList(districtList);
        mCurrentCityName = cityModel.name;
        mCurrentCityId = cityModel.id;
    }

    private void updateDistrict(int pos) {
        if (mAreaList.size() == 0) {
            return;
        }
        AreaBean districtModel = mAreaList.get(pos);
        mCurrentDistrictName = districtModel.name;
        mCurrentDistrictId = districtModel.id;
    }

    @Override
    public void onClick(View v) {

        if (v == mNameDel) {
            mNameEdit.setText("");
        } else if (v == mPhoneDel) {
            mPhoneEdit.setText("");
        } else if (v == mBackBtn) {
            finish();
        } else if (v == mEditTxt) {
            AddressBean data = new AddressBean();
            data.name = mNameEdit.getText().toString();
            data.address = mAddressEdit.getText().toString();
            data.phone = mPhoneEdit.getText().toString();
            data.province = mCurrentProvinceId;
            data.city = mCurrentCityId;
            data.area = mCurrentDistrictId;
            data.isDefault = mDefault;
            data.id = mAddressBean.id;
            mOrderPresenter.saveAddress(data);
        } else if (v == mAreaBtn) {
            mOrderPresenter.showPcaView(mPcaLayout);
            DeviceManager.getInstance().hideInputMethod();
        } else if (v == mOkBtn) {
            mOrderPresenter.hidePcaView(mPcaLayout);
        } else if (v == mSelectLayout) {

            if (mDefault == 0) {
                mDefault = 1;
                mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_checked));
            } else {
                mDefault = 0;
                mSelectImg.setImageURI(Uri.parse("res:///" + R.drawable.checkbox_green_uncheck));
            }
        }
    }

    @Override
    public void OnGetAddressListSuccess(List<AddressBean> datas) {

    }

    @Override
    public void OnGetAddressListFail() {

    }

    @Override
    public void OnSaveAddressSuccess() {
        if (mPcaLayout.getVisibility() == View.VISIBLE) {
            mOrderPresenter.hidePcaView(mPcaLayout);
        }
        FWObserverManager.getIntance().notifyUpdate(ObserverData.Update_AddressList,null);
        DeviceManager.getInstance().hideInputMethod();
        finish();
    }

    @Override
    public void OnSaveAddressFail() {

    }

    @Override
    public void OnDeleteAddressSuccess(List<AddressBean> datas) {

    }

    @Override
    public void OnDeleteAddressFail() {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (hasFocus && mPcaLayout.getVisibility() == View.VISIBLE) {
            mOrderPresenter.hidePcaView(mPcaLayout);
        }
    }
}
