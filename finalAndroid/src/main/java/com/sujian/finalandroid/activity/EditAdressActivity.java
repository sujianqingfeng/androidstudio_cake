package com.sujian.finalandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.BooleabEntity;
import com.sujian.finalandroid.net.BooleanCallback;
import com.sujian.finalandroid.ui.ChangeAddressDialog;
import com.sujian.finalandroid.ui.TitleBuilder;
import com.sujian.finalandroid.uitls.MyUitls;
import com.sujian.finalandroid.uitls.ToastUitls;
import com.zhy.http.okhttp.OkHttpUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.refactor.library.SmoothCheckBox;
import okhttp3.Call;

/**
 * 修改收货地址页面
 */
@ContentView(R.layout.activity_edit_adress)
public class EditAdressActivity extends BaseActivity {

    //所在城市
    @ViewInject(R.id.tv_edit_address_city)
    private TextView tv_edit_address_city;
    //所在地址
    @ViewInject(R.id.tv_edit_address_detailed)
    private TextView tv_edit_address_detailed;
    //百度地图的标识码
    private static final int BAIDUMAP = 1;
    //判断是修改还是添加的标识
    private boolean isAdd;
    //按钮
    @ViewInject(R.id.bt_address)
    private Button bt_address;
    //验证
    private Validator validator;
    //联系人
    @Required(order = 1, message = "联系人不能为空")
    @ViewInject(R.id.ed_personText)
    private EditText ed_personText;
    //电话
    @Required(order = 2, message = "电话不能为空")
    @TextRule(order = 3, minLength = 11, maxLength = 11, message = "电话为11位")
    @ViewInject(R.id.ed_phoneText)
    private EditText ed_phoneText;
    //选择容器
    @ViewInject(R.id.rg_edit_adress)
    private RadioGroup rg_edit_adress;
    //选择的性别  默认为男  0 代表男  1代表女
    private int sex;
    //默认地址选择
    @ViewInject(R.id.scb_address_choose)
    private SmoothCheckBox scb_address_choose;
    //是否默认  0不是  1是默认地址
    private int check = 0;


    @Override
    protected void initData() {
        super.initData();
        getFlag();
        initTitle();
        initValidator();
        initView();
        initCheck();
    }

    /**
     * 得到选中的性别 和 选择的默认地址
     */
    private void initCheck() {
        rg_edit_adress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_edit_adress_male:
                        sex = 0;
                        break;
                    case R.id.rb_edit_adress_female:
                        sex = 1;
                        break;
                }
            }
        });

        //是否默认地址
        scb_address_choose.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    check = 1;
                } else {
                    check = 0;
                }
            }
        });

    }

    /**
     * 初始化验证
     */
    private void initValidator() {
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {

                //utf-8 编码 解决乱码
                String c = null;
                String content = null;
                String name = null;
                try {
                    c = URLEncoder.encode(tv_edit_address_city.getText().toString(), "UTF-8");
                    content = URLEncoder.encode(tv_edit_address_detailed.getText().toString(), "UTF-8");
                    name = URLEncoder.encode(ed_personText.getText().toString().trim(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //判断是否是添加
                if (isAdd) {
                    String url = Constants.SERVICEADDRESS + "address/address_addAddress.cake";
                    OkHttpUtils.get()
                            .url(url)
                            .addParams("user_id", MyUitls.getUid() + "")
                            .addParams("address_sex", sex + "")
                            .addParams("address_phone", ed_phoneText.getText().toString().trim())
                            .addParams("address_name", name)
                            .addParams("address_city", c)
                            .addParams("address_content", content)
                            .addParams("ischeck", check + "")
                            .build()
                            .execute(new BooleanCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(BooleabEntity response, int id) {
                                    if (response.isSuccess()) {
                                        ToastUitls.show("添加成功！");
                                    }
                                }
                            });
                } else {

                }
            }

            @Override
            public void onValidationFailed(View failedView, Rule<?> failedRule) {
                String failureMessage = failedRule.getFailureMessage();
                ToastUitls.show(failureMessage);
            }
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        if (isAdd) {


        } else {

        }
    }


    /**
     * 点击按钮
     *
     * @param v
     */
    @Event(R.id.bt_address)
    private void clickButton(View v) {
        //开始验证规则
        validator.validate();
    }


    /**
     * 得到标识
     */
    private void getFlag() {
        Bundle extras = getIntent().getExtras();
        isAdd = extras.getBoolean("flag", true);
    }


    //点击所在城市
    @Event(R.id.tv_edit_address_city)
    private void clickCity(final View v) {
        ChangeAddressDialog mChangeAddressDialog = new ChangeAddressDialog(EditAdressActivity.this);
        mChangeAddressDialog.setAddress("四川", "自贡");
        mChangeAddressDialog.show();
        mChangeAddressDialog.setAddresskListener(new ChangeAddressDialog.OnAddressCListener() {

            @Override
            public void onClick(String province, String city) {
                TextView tv = (TextView) v;
                tv.setText(province + "--" + city);
            }
        });
    }

    /**
     * 点击地址
     */
    @Event(R.id.tv_edit_address_detailed)
    private void clickDetail(View view) {
        Intent intent = new Intent(EditAdressActivity.this, LocationActivity.class);
        startActivityForResult(intent, BAIDUMAP);

    }


    /**
     * 百度地图返回的数据处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case BAIDUMAP:
                String address = data.getExtras().getString("address", "哈哈哈哈,迷路了");
                tv_edit_address_detailed.setText(address);
                break;
        }
    }

    /**
     * 初始化标题
     */
    private void initTitle() {

        new TitleBuilder(this)
                .setLeftImageRes(R.drawable.head_top_title_left_icon)
                .setMiddleTitleText("收货地址")
                .setLeftTextOrImageListener(titleListener)
                .initTitle(this);
    }


    /**
     * 标题栏的监听
     */
    private View.OnClickListener titleListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_left_imageview:
                    finish();
                    break;
            }

        }
    };
}
