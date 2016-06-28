package com.sujian.finalandroid.activity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.net.LoginCallback;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.LoginCallBackEntiy;
import com.sujian.finalandroid.uitls.MyUitls;
import com.sujian.finalandroid.uitls.SetIconSizeUiutils;
import com.sujian.finalandroid.uitls.SharedPreferencesUitls;
import com.sujian.finalandroid.uitls.ToastUitls;
import com.zhy.http.okhttp.OkHttpUtils;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.refactor.library.SmoothCheckBox;
import okhttp3.Call;

/**
 * @author Sujian  121116111@QQ.COM
 * @ClassName: LoginActivity
 * @Description: TODO(登陆页)
 * @date 2016年4月13日 上午9:16:16
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    //登录按钮
    @ViewInject(R.id.bt_login)
    private Button bt_login;

    @ViewInject(R.id.tv_login_user)
    private TextView tv_login_user;

    //注册按钮
    @ViewInject(R.id.knock)
    private TextView knock;

    @ViewInject(R.id.bt_reg)
    private Button bt_reg;
    //用户账号
    @Required(order = 1, message = "用户账号不能为空")
    @TextRule(order = 2, minLength = 11, maxLength = 11, message = "用户账号为11位")
    @ViewInject(R.id.ed_user)
    private EditText ed_user;
    //用户密码
    @Required(order = 3, message = "密码不能为空")
    @TextRule(order = 4, minLength = 8, maxLength = 16, message = "密码8-16位")
    @ViewInject(R.id.ed_password)
    private EditText ed_password;
    //验证
    private Validator validator;

    //记住密码
    @ViewInject(R.id.scb_login_choose)
    private SmoothCheckBox scb_login_choose;

    //记住密码的标识
    private boolean flag;


    //登陆点击
    @Event(value = R.id.bt_login)
    private void goHome(View view) {
        validator.validate();
    }

    //注册点击
    @Event(value = R.id.bt_reg)
    private void goRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    protected void initsize() {
        SetIconSizeUiutils setIconSizeUiutils = new SetIconSizeUiutils(mContext, R.drawable.login_user, 60, 60) {
            @Override
            public void setLocation(Drawable drawable) {
                tv_login_user.setCompoundDrawables(drawable, null, null, null);
            }
        };
        setIconSizeUiutils = new SetIconSizeUiutils(mContext, R.drawable.knock, 80, 80) {
            @Override
            public void setLocation(Drawable drawable) {
                knock.setCompoundDrawables(null, drawable, null, null);
            }
        };
    }

    @Override
    protected void initData() {
        validator = new Validator(this);
        validator.setValidationListener(new LoninValidationListener());
        initCheckBox();
    }

    /**
     * 初始化checkbox
     */
    private void initCheckBox() {

        String user = SharedPreferencesUitls.getStringValue(Constants.USER_ACOUNT, "");
        ed_user.setText(user);


        boolean b = SharedPreferencesUitls.getBooleanValue(Constants.BOOLEANPASSWORD, false);
        if (b) {
            String password = SharedPreferencesUitls.getStringValue(Constants.USER_PASSWORD, "");
            ed_password.setText(password);
            scb_login_choose.setChecked(true, true);
        }

        scb_login_choose.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                flag = isChecked;
            }
        });
    }


    /**
     * 记住密码
     */
    private void remenberPassword() {

        SharedPreferencesUitls.setBooleanValue(Constants.BOOLEANPASSWORD, flag);
        SharedPreferencesUitls.setStringValue(Constants.USER_ACOUNT, ed_user.getText().toString().trim());

        if (flag) {
            SharedPreferencesUitls.setStringValue(Constants.USER_PASSWORD, ed_password.getText().toString().trim());
        }
    }

    /**
     * 登陆验证监听器
     */
    private class LoninValidationListener implements ValidationListener {
        @Override
        public void onValidationSucceeded() {

            remenberPassword();
            String url = Constants.SERVICEADDRESS + "user/user_login.cake";
            OkHttpUtils
                    .get()
                    .url(url)
                    .addParams("user_acount", ed_user.getText().toString())
                    .addParams("user_password", ed_password.getText().toString())
                    .build()
                    .execute(new LoginCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(LoginCallBackEntiy response, int id) {

                            if (response.isSuccess()) {
                                ToastUitls.show("登录成功");
                                MyUitls.setUid(response.getId());
                                SharedPreferencesUitls.setStringValue(Constants.USER_ACOUNT, ed_user.getText().toString());
                                finish();
                            } else {
                                switch (response.getResuleCode()) {
                                    case 10001:
                                        ToastUitls.show("账户不存在");
                                        break;
                                    case 10002:
                                        ToastUitls.show("密码错误");
                                        break;
                                }
                            }
                        }
                    });
        }

        @Override
        public void onValidationFailed(View failedView, Rule<?> failedRule) {
            String failureMessage = failedRule.getFailureMessage();
            ToastUitls.show("failureMessage");
        }
    }


}
