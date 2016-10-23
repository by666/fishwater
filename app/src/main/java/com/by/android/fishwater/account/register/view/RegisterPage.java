package com.by.android.fishwater.account.register.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.login.view.LoginPage;
import com.by.android.fishwater.account.register.presenter.RegisterPresenter;
import com.by.android.fishwater.util.CommonUtils;
import com.by.android.fishwater.util.DeviceManager;
import com.by.android.fishwater.view.AlphaTextView;
import com.by.android.fishwater.widget.AlphaImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.regex.Matcher;

/**
 * Created by by.huang on 2016/10/9.
 */

@ContentView(R.layout.page_register)

public class RegisterPage extends Fragment implements View.OnClickListener,IRegisterInterface,TextWatcher{

    private String mPhoneText = "";
    private String mNicknameText = "";
    private String mPasswordText = "";
    private boolean SingUpAble;

    @ViewInject(R.id.account_signup_nickname)
    EditText mNicknameEdit;

    @ViewInject(R.id.account_signup_nickname_clear_button)
    AlphaImageView mNicknameClearBtn;

    @ViewInject(R.id.account_signup_phone)
    EditText mPhoneEdit;

    @ViewInject(R.id.account_signup_phone_clear_button)
    AlphaImageView mPhoneClearBtn;

    @ViewInject(R.id.account_signup_password)
    EditText mPasswordEdit;

    @ViewInject(R.id.account_signup_password_clear_button)
    AlphaImageView mPasswordClearBtn;

    @ViewInject(R.id.sig_up)
    AlphaTextView mSignupBtn;

    @ViewInject(R.id.account_login_button_in_register)
    AlphaTextView mLoginBtn;



    private RegisterPresenter mRegisterPresenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRegisterPresenter = new RegisterPresenter(this);
        initView();

    }

    private void initView()
    {
        mNicknameClearBtn.setOnClickListener(this);
        mPhoneClearBtn.setOnClickListener(this);
        mPasswordClearBtn.setOnClickListener(this);
        mSignupBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);

        mPhoneEdit.addTextChangedListener(this);

        mNicknameEdit.addTextChangedListener(this);
        //昵称不能使用系统表情和空格
        mNicknameEdit.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = CommonUtils.emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                if (source.equals(" ")) {
                    return "";
                }
                return null;
            }
        }, new InputFilter.LengthFilter(8)});

        mPasswordEdit.addTextChangedListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_signup_phone_clear_button:
                mPhoneEdit.setText("");
                break;
            case R.id.account_signup_nickname_clear_button:
                mNicknameEdit.setText("");
                break;
            case R.id.account_signup_password_clear_button:
                mPasswordEdit.setText("");
                break;
            case R.id.sig_up:
                DeviceManager.getInstance().hideInputMethod();
                mRegisterPresenter.register(mPhoneEdit.getText().toString(),mPasswordEdit.getText().toString(),mNicknameEdit.getText().toString());
                break;
            case R.id.account_login_button_in_register:
                DeviceManager.getInstance().hideInputMethod();
                mRegisterPresenter.GoLoginPage();
                break;
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mPhoneText = mPhoneEdit.getText().toString();
        if (mPhoneText.length() > 0) {
            mPhoneClearBtn.setVisibility(View.VISIBLE);
        } else {
            mPhoneClearBtn.setVisibility(View.GONE);
        }
        mNicknameText = mNicknameEdit.getText().toString();
        if (mNicknameText.length() > 0) {
            mNicknameClearBtn.setVisibility(View.VISIBLE);
        } else {
            mNicknameClearBtn.setVisibility(View.GONE);
        }
        mPasswordText = mPasswordEdit.getText().toString();
        if (mPasswordText.length() > 0) {
            mPasswordClearBtn.setVisibility(View.VISIBLE);
        } else {
            mPasswordClearBtn.setVisibility(View.GONE);
        }
        if (mPhoneText.length() == 11 && mNicknameText.length() > 0 && mPasswordText.length() > 5 && mPasswordText.length() < 19) {
            mSignupBtn.setBackgroundResource(R.drawable.account_login_on);
            SingUpAble = true;
        } else {
            mSignupBtn.setBackgroundResource(R.drawable.account_login_normal);
            SingUpAble = false;
        }
    }


    @Override
    public void GoLoginPage() {
        LoginPage page = new LoginPage();
        FWPresenter.getInstance().replaceFragment(page);
    }
}
