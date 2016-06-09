package com.sujian.finalandroid.activity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.net.RegisterCallback;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.RegisterCallBackEntity;
import com.zhy.http.okhttp.OkHttpUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.Call;

/**
 * @author Sujian  121116111@QQ.COM
 * @ClassName: RegisterActivity
 * @Description: TODO(注册页)
 * @date 2016年4月15日 下午10:11:06
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.iv_return)
    private Button iv_return;

    @ViewInject(R.id.bt_reg)
    private Button bt_reg;
    //验证
    private Validator validator;

    @Required(order = 1, message = "用户账号不能为空")
    @TextRule(order = 2, minLength = 11, maxLength = 11, message = "用户账号位数为11")
    @ViewInject(R.id.ed_user)
    private EditText ed_user;

    @Password(order = 3, message = "密码不能为空")
    @TextRule(order = 4, minLength = 8, maxLength = 16, message = "密码位数8-16")
    @ViewInject(R.id.ed_password)
    private EditText ed_password;

    @Required(order = 5, message = "确认密码不能为空")
    @ConfirmPassword(order = 6, message = "密码不一致")
    @ViewInject(R.id.ed_repassword)
    private EditText ed_repassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        validator = new Validator(this);
        validator.setValidationListener(new RegisterValidationListener());
    }

    //返回点击
    @Event(value = R.id.iv_return)
    private void clickReturn(View view) {
        finish();
    }


    //注册被点击
    @Event(value = R.id.bt_reg)
    private void clickRegister(View view) {
        validator.validate();
    }


    /**
     * 注册验证监听
     */
    private class RegisterValidationListener implements Validator.ValidationListener {

        @Override
        public void onValidationSucceeded() {

            String url = Constants.SERVICEADDRESS + "user/user_register.cake";
            OkHttpUtils
                    .get()
                    .url(url)
                    .addParams("user_acount", ed_user.getText().toString())
                    .addParams("user_password", ed_password.getText().toString())
                    .build()
                    .execute(new RegisterCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(RegisterCallBackEntity response, int id) {
                            if (response.getSuccess()) {
                                Toast.makeText(x.app(), "注册成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                if (response.getResuleCode() == 10001) {
                                    Toast.makeText(x.app(), "注册失败，用户存在", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(x.app(), "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

        }

        @Override
        public void onValidationFailed(View failedView, Rule<?> failedRule) {
            String failureMessage = failedRule.getFailureMessage();
            Toast.makeText(x.app(), failureMessage, Toast.LENGTH_SHORT).show();
        }
    }


}
