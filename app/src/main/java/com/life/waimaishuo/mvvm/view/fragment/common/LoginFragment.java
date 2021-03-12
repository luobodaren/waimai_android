package com.life.waimaishuo.mvvm.view.fragment.common;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.R;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentLoginBinding;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.LoginViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.TextUtil;
import com.life.waimaishuo.util.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

@Page(name = "登录页", anim = CoreAnim.fade)
public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding mBinding;
    private LoginViewModel mViewModel;

    private Intent resultIntent;

    private FragmentManager mFragmentManager;

    private LoginTypeFragment loginByMachineFragment;
    private String loginByMachineTag = "loginByMachineTag";
    private LoginTypeFragment loginByPhoneFragment;
    private String loginByPhoneTag = "loginByPhoneTag";

    private static String currentRequestVerificationPhone = "";

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new LoginViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentLoginBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        LogUtil.d("初始化登录界面----------------------------");

        setMachinePhone();

        if (mFragmentManager == null) {
            mFragmentManager = getChildFragmentManager();
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        initLoginTypeFragment(ft);

        ft.show(loginByMachineFragment);
        ft.hide(loginByPhoneFragment);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        addCallBack();
    }

    @Override
    public void popToBack() {
        LogUtil.d("尝试推出登录界面");
        super.popToBack();
    }

    private void addCallBack(){
        MyDataBindingUtil.addCallBack(this, mViewModel.loginObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if(mViewModel.loginObservable.get() == BaseModel.NotifyChangeRequestCallBack.REQUEST_SUCCESS){
                        Toast.makeText(requireContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                        setFragmentResult(Constant.RESULT_CODE_SUCCESS,null);
                        popToBack();
                    }else{
                        Toast.makeText(requireContext(),"登录失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.requestVerificationObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if(mViewModel.requestVerificationObservable.get() == BaseModel.NotifyChangeRequestCallBack.REQUEST_SUCCESS){
                        Toast.makeText(requireContext(), "发送成功！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(requireContext(),"发送失败T.T",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initLoginTypeFragment(FragmentTransaction ft) {
        loginByMachineFragment = new LoginTypeFragment(R.layout.fragment_login_machine);
        loginByMachineFragment.onClickListener = new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                switch (view.getId()){
                    case R.id.iv_close:
                        setFragmentResult(Constant.RESULT_CODE_FALSE,null);
                        popToBack();
                        break;
                    case R.id.bt_login_by_other:
                        FragmentTransaction ft = mFragmentManager.beginTransaction();
                        ft.show(loginByPhoneFragment);
                        ft.hide(loginByMachineFragment);
                        ft.commit();
                        break;
                    case R.id.bt_login_by_machine:
                        loginWithOneKey();
                        break;
                }
            }
        };

        loginByPhoneFragment = new LoginTypeFragment(R.layout.fragment_login_by_phone);
        loginByPhoneFragment.onClickListener = new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                switch (view.getId()){
                    case R.id.iv_close:
                        FragmentTransaction ft = mFragmentManager.beginTransaction();
                        ft.show(loginByMachineFragment);
                        ft.hide(loginByPhoneFragment);
                        ft.commit();
                        break;
                    case R.id.bt_get_verification:
                        getVerification();
                        break;
                    case R.id.bt_login:
                        loginWithVerification(loginByPhoneFragment.loginPhone,loginByPhoneFragment.loginVerification);
                        break;
                }
            }
        };

        ft.add(R.id.fl_login_type, loginByMachineFragment, loginByMachineTag);
        ft.add(R.id.fl_login_type, loginByPhoneFragment, loginByPhoneTag);
        ft.commit();
    }

    private void setMachinePhone() {
        if(Global.getMachine_number() == null || "".equals(Global.getMachine_number())){
            TelephonyManager mTelephonyMgr;
            mTelephonyMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            if (!Utils.checkPermission(requireContext(),Manifest.permission.READ_SMS)
                    && !Utils.checkPermission(requireContext(),Manifest.permission.READ_PHONE_NUMBERS)
                    && !Utils.checkPermission(requireContext(), Manifest.permission.READ_PHONE_STATE)) {
                return;
            }
            Global.setMachine_number(mTelephonyMgr.getLine1Number());
        }
    }


    /**
     * 本机号码一键登录
     */
    private void loginWithOneKey(){
        LogUtil.d("一键登录");
    }

    /**
     * 其他手机号码、验证码登录
     */
    private void loginWithVerification(String phoneNumber,String verification){
        mViewModel.loginWithVerification(phoneNumber,verification);
    }

    /**
     * 获取验证码
     */
    private void getVerification(){
        mViewModel.requestVerification(currentRequestVerificationPhone);
    }


    public static class LoginTypeFragment extends Fragment{
        private int  mContentLayoutId;

        private BaseActivity.OnSingleClickListener onClickListener;

        //loginByMachine
        private TextView phoneTv;

        //loginByOther
        private boolean hasGetVerification = false;
        private EditText etPhoneNumber;
        private EditText etVerification;
        private Button loginButton;
        private String loginPhone;  //登录所使用的账号和验证码
        private String loginVerification;

        public LoginTypeFragment(int contentLayoutId) {
            super(contentLayoutId);
            mContentLayoutId = contentLayoutId;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            if(mContentLayoutId == R.layout.fragment_login_machine){
                initLoginByMachineFragment(view);
            }else if(mContentLayoutId == R.layout.fragment_login_by_phone){
                initLoginByPhoneFragment(view);
            }
        }

        private void initLoginByMachineFragment(View view){
            view.findViewById(R.id.iv_close).setOnClickListener(onClickListener);
            view.findViewById(R.id.bt_login_by_machine).setOnClickListener(onClickListener);
            view.findViewById(R.id.bt_login_by_other).setOnClickListener(onClickListener);

            phoneTv = view.findViewById(R.id.tv_login_phoneNumber);
            if(Global.getUser() != null && Global.getUser().getPhoneNumber() != null
                    && !"".equals(Global.getUser().getPhoneNumber())){
                phoneTv.setText(Global.getUser().getPhoneNumber());
            }else if(Global.getMachine_number() != null && !"".equals(Global.getMachine_number())){
                phoneTv.setText(Global.getMachine_number());
            }

            String loginTip = getString(R.string.login_tip);
            String agreement = getString(R.string.agreement);
            SpannableString agreementSS = TextUtil.getClickableSpan(agreement, new BaseActivity.OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {

                }
            },0,agreement.length());

            String policy = getString(R.string.policy);
            SpannableString policySS = TextUtil.getClickableSpan(policy, new BaseActivity.OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {

                }
            },0,policy.length());

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append(loginTip).append(agreementSS).append("、").append(policySS);
            spannableStringBuilder.setSpan(
                    new ForegroundColorSpan(
                            getResources().getColor(R.color.colorAssist_3)),
                    loginTip.length(),
                    spannableStringBuilder.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((TextView)view.findViewById(R.id.tv_tip)).setText(spannableStringBuilder);
        }

        private void initLoginByPhoneFragment(View view){
            view.findViewById(R.id.iv_close).setOnClickListener(onClickListener);
            loginButton = view.findViewById(R.id.bt_login);
            loginButton.setOnClickListener(new BaseActivity.OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    loginPhone = etPhoneNumber.getText().toString();
                    loginVerification = etVerification.getText().toString();
                    onClickListener.onSingleClick(view);
                }
            });
            etPhoneNumber = view.findViewById(R.id.et_phoneNumber);
            etPhoneNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
                @Override
                public void afterTextChanged(Editable s) {
                    checkCanLogin();
                }
            });
            etVerification = view.findViewById(R.id.et_verification);
            etVerification.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
                @Override
                public void afterTextChanged(Editable s) {
                    checkCanLogin();
                }
            });

            SuperButton getVerification = view.findViewById(R.id.bt_get_verification);
            CountDownButtonHelper mCountDownHelper1 = new CountDownButtonHelper(getVerification, 60);
            getVerification.setOnClickListener(new BaseActivity.OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    String phone = etPhoneNumber.getText().toString();
                    if(phone.length() != 11 || !TextUtil.isPhoneLegal(phone)){
                        Toast.makeText(requireContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mCountDownHelper1.start();
                    hasGetVerification = true;
                    checkCanLogin();
                    currentRequestVerificationPhone = phone;    //保存请求验证码的手机号的备份
                    onClickListener.onSingleClick(view);
                }
            });

        }

        private void checkCanLogin(){
            if(etPhoneNumber.getText().length() == 11 && etVerification.getText().length() > 0
                    && hasGetVerification){
                loginButton.setEnabled(true);
            }else{
                loginButton.setEnabled(false);
            }
        }

    }


}
