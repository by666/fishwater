package com.by.android.fishwater.account.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.login.presenter.LoginPresenter;
import com.by.android.fishwater.account.register.view.RegisterPage;
import com.by.android.fishwater.util.DeviceManager;
import com.by.android.fishwater.util.ToastUtil;
import com.by.android.fishwater.view.AlphaTextView;
import com.by.android.fishwater.view.AlphaImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by by.huang on 2016/10/9.
 */

@ContentView(R.layout.page_login)
public class LoginPage extends Fragment implements View.OnClickListener,ILoginInterface{

    @ViewInject(R.id.account_login_phone)
    EditText mPhoneEdit;

    @ViewInject(R.id.account_login_password)
    EditText mPasswordEdit;

    @ViewInject(R.id.account_login_phone_clear_button)
    AlphaImageView mPhoneClearBtn;

    @ViewInject(R.id.account_login_password_clear_button)
    AlphaImageView mPasswordClearBtn;

    @ViewInject(R.id.account_login_login)
    AlphaTextView mLoginBtn;

    @ViewInject(R.id.account_login_wechat)
    AlphaImageView mWechatBtn;

    @ViewInject(R.id.account_login_qq)
    AlphaImageView mQQBtn;

    @ViewInject(R.id.account_login_weibo)
    AlphaImageView mWeiboBtn;

    @ViewInject(R.id.account_login_forget_password)
    TextView mForgetPasswordBtn;

    @ViewInject(R.id.account_register_button)
    AlphaTextView mRegisterBtn;

    private LoginPresenter mLoginPresenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginPresenter = new LoginPresenter(this);
        initView();

    }

    private void initView()
    {
        mPhoneClearBtn.setOnClickListener(this);
        mPasswordClearBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mWechatBtn.setOnClickListener(this);
        mQQBtn.setOnClickListener(this);
        mWeiboBtn.setOnClickListener(this);
        mForgetPasswordBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);

        addTextChangeListener();

    }


    private void addTextChangeListener() {
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
                    mPhoneClearBtn.setVisibility(View.VISIBLE);
                } else {
                    mPhoneClearBtn.setVisibility(View.GONE);
                }
                if (mPhoneEdit.getText().toString().length() == 11 && mPasswordEdit.getText().toString().length() >= 6) {
                    mLoginBtn.setBackgroundResource(R.drawable.account_login_on);
                } else {
                    mLoginBtn.setBackgroundResource(R.drawable.account_login_normal);
                }
            }
        });

        mPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    mPasswordClearBtn.setVisibility(View.VISIBLE);
                } else {
                    mPasswordClearBtn.setVisibility(View.GONE);
                }
                if (mPhoneEdit.getText().toString().length() == 11 && mPasswordEdit.getText().toString().length() >= 6) {
                    mLoginBtn.setBackgroundResource(R.drawable.account_login_on);
                } else {
                    mLoginBtn.setBackgroundResource(R.drawable.account_login_normal);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_login_login://手机登录
                DeviceManager.getInstance().hideInputMethod();
                mLoginPresenter.login(mPhoneEdit.getText().toString(),mPasswordEdit.getText().toString());
                break;
            case R.id.account_login_forget_password:    //忘记密码
                DeviceManager.getInstance().hideInputMethod();
                mLoginPresenter.forgetPassword();
                break;
            case R.id.account_login_qq: //qq登录
                mLoginPresenter.loginWithQQ();
                ToastUtil.show("开发中...");
                break;
            case R.id.account_login_wechat: //微信登录
                mLoginPresenter.loginWithWechat();
                ToastUtil.show("开发中...");
                break;
            case R.id.account_login_weibo:  //新浪微博登录
                mLoginPresenter.loginWithWeibo();
                ToastUtil.show("开发中...");
                break;
            case R.id.account_login_phone_clear_button:  //清除电话编辑框
                mPhoneEdit.setText("");
                break;
            case R.id.account_login_password_clear_button:  //清除密码编辑框
                mPasswordEdit.setText("");
                break;
            case R.id.account_register_button:  //跳转注册
                DeviceManager.getInstance().hideInputMethod();
                mLoginPresenter.register();
                break;
        }
    }


    @Override
    public void GoRegisterPage() {
        RegisterPage page = new RegisterPage();
        FWPresenter.getInstance().replaceFragment(page);
    }

    @Override
    public void GoForgetPassWordPage() {

    }
}
