package com.sujian.finalandroid.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.UpdateUserCallBackEntity;
import com.sujian.finalandroid.entity.User;
import com.sujian.finalandroid.net.UpdateUserCallBack;
import com.sujian.finalandroid.ui.ChangeAddressDialog;
import com.sujian.finalandroid.ui.ChangeAddressDialog.OnAddressCListener;
import com.sujian.finalandroid.ui.ChangeBirthDialog;
import com.sujian.finalandroid.ui.ChangeBirthDialog.OnBirthListener;
import com.sujian.finalandroid.ui.CircleImageView;
import com.sujian.finalandroid.ui.ProfessionDialog;
import com.sujian.finalandroid.ui.ProfessionDialog.OnProfessionListener;
import com.sujian.finalandroid.ui.PromptDialog;
import com.sujian.finalandroid.ui.TitleBuilder;
import com.sujian.finalandroid.uitls.ImagesUitls;
import com.sujian.finalandroid.uitls.MyUitls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Call;

/**
 * 修改资料页面
 *
 * @author 12111
 */

@ContentView(R.layout.activity_updata)
public class UpdataActivity extends BaseActivity {

    // 照相机的请求码
    private static final int CRE_CODE = 1;
    // 图库的请求码
    private static final int TUKU = 2;
    // 裁剪的请求码
    private static final int ZOOM_CODE = 3;
    private String imguri;
    private Uri uriCreFile;
    //头像的对话框
    private PromptDialog picDialog;
    //性别的对话框
    private PromptDialog sexDialog;
    // 头像
    @ViewInject(R.id.civ_pic)
    private CircleImageView civ_pic;
    // 资料展示的listview
    @ViewInject(R.id.lv_updata)
    private ListView lv_updata;

    private SimpleAdapter simpleAdapter;
    private Map<String, Object> map;


    private List<Map<String, Object>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        initTitle();
        initListView();
    }


    private void getDateFromService() {
        String url = Constants.SERVICEADDRESS + "user/user_select.cake";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("user_id", String.valueOf(MyUitls.getUid()))
                .build()
                .execute(new UpdateUserCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(UpdateUserCallBackEntity response, int id) {
                        User u = response.getUser();
                        LogUtil.e(u.toString());
                        String content[] = new String[7];
                        content[0] = u.getUser_acount() + "";
                        content[1] = u.getUser_sex();
                        content[2] = u.getUser_birthday();
                        content[3] = u.getUser_hometown();
                        content[4] = u.getUser_seat();
                        content[5] = u.getUser_occupation();

                        String lables[] = {"账号:", "性别:", "生日:", "家乡:", "所在地:", "职业:", "设置收货地址"};
                        dataList.clear();
                        for (int i = 0; i < content.length; i++) {
                            map = new HashMap<String, Object>();
                            map.put("lable", lables[i]);
                            map.put("content", content[i]);
                            dataList.add(map);
                        }

                        simpleAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 初始化listview的展示页面
     */
    private void initListView() {
        getDateFromService();
        dataList = new ArrayList<Map<String, Object>>();
        String lables[] = {"账号:", "性别:", "生日:", "家乡:", "所在地:", "职业:", "设置收货地址"};
        String[] content = new String[]{"121116111", "男", "1995年08月15日", "四川遂宁", "四川成都", "it", ""};

        for (int i = 0; i < lables.length; i++) {
            map = new HashMap<String, Object>();
            map.put("lable", lables[i]);
            map.put("content", content[i]);
            dataList.add(map);
        }
        simpleAdapter = new SimpleAdapter(this, dataList, R.layout.item_update_listview,
                new String[]{"lable", "content"}, new int[]{R.id.tv_lable, R.id.tv_content});
        lv_updata.setAdapter(simpleAdapter);
        // 条目点击事件
        lv_updata.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
                switch (position) {
                    case 1:

                        sexDialog = new PromptDialog.Builder(UpdataActivity.this).setMessage("男", new SexLinstener(parent, view, position))
                                .setMessage2("女", new SexLinstener(parent, view, position)).setTitle("设置性别").setCanceledOnTouchOutside(true).show();

                        break;

                    case 2:
                        ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(UpdataActivity.this);
                        Calendar calendar = Calendar.getInstance();
                        mChangeBirthDialog.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                                calendar.get(Calendar.DAY_OF_MONTH));
                        mChangeBirthDialog.show();
                        mChangeBirthDialog.setBirthdayListener(new OnBirthListener() {

                            @Override
                            public void onClick(String year, String month, String day) {

                                View view2 = simpleAdapter.getView(position, view, parent);
                                TextView tv = (TextView) view2.findViewById(R.id.tv_content);
                                tv.setText(year + "年" + month + "月" + day + "日");

                            }
                        });
                        break;

                    case 3:
                        ChangeAddressDialog mChangeAddressDialog = new ChangeAddressDialog(UpdataActivity.this);
                        mChangeAddressDialog.setAddress("四川", "自贡");
                        mChangeAddressDialog.show();
                        mChangeAddressDialog.setAddresskListener(new OnAddressCListener() {

                            @Override
                            public void onClick(String province, String city) {
                                View view2 = simpleAdapter.getView(position, view, parent);
                                TextView tv = (TextView) view2.findViewById(R.id.tv_content);
                                tv.setText(province + "--" + city);
                            }
                        });
                        break;

                    case 4:
                        ChangeAddressDialog mChangeAddressDialog2 = new ChangeAddressDialog(UpdataActivity.this);
                        mChangeAddressDialog2.setAddress("四川", "自贡");
                        mChangeAddressDialog2.show();
                        mChangeAddressDialog2.setAddresskListener(new OnAddressCListener() {

                            @Override
                            public void onClick(String province, String city) {
                                View view2 = simpleAdapter.getView(position, view, parent);
                                TextView tv = (TextView) view2.findViewById(R.id.tv_content);
                                tv.setText(province + "--" + city);
                            }
                        });
                        break;

                    case 5:
                        ProfessionDialog professionDialog = new ProfessionDialog(UpdataActivity.this);
                        professionDialog.setProfession("教师");
                        professionDialog.show();
                        professionDialog.setOnProfessionListener(new OnProfessionListener() {

                            @Override
                            public void onClick(String profession) {
                                View view2 = simpleAdapter.getView(position, view, parent);
                                TextView tv = (TextView) view2.findViewById(R.id.tv_content);
                                tv.setText(profession);
                            }
                        });
                        break;
                    case 6:
                        startActivity(new Intent(UpdataActivity.this, ChangeAdressActivity.class));
                        break;
                }
            }
        });
    }

    /**
     * 点击图片的事件
     *
     * @param view
     */
    @Event(value = R.id.civ_pic)
    private void clickPic(View view) {
        picDialog = new PromptDialog.Builder(this).setMessage("照相", new PicLinstener())
                .setMessage2("从相册中取", new PicLinstener()).setTitle("设置头像").setCanceledOnTouchOutside(true).show();

    }


    /**
     * 头像对话框选择条目的监听类
     *
     * @author 12111
     */
    class PicLinstener implements PromptDialog.OnLinearClickListener {

        @Override
        public void onClick(int which) {
            switch (which) {
                case 0:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CRE_CODE);
                    break;

                case 1:
                    Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                    // 文件的类型
                    intent1.setType("image/*");
                    startActivityForResult(intent1, TUKU);
                    break;
            }
        }

    }

    /**
     * 性别条目的点击事件
     *
     * @author 12111
     */
    class SexLinstener implements PromptDialog.OnLinearClickListener {

        private AdapterView<?> parent;
        private View view;
        private int position;

        public SexLinstener(AdapterView<?> parent, View view, int position) {
            this.parent = parent;
            this.view = view;
            this.position = position;
        }

        @Override
        public void onClick(int which) {
            sexDialog.dismiss();
            switch (which) {
                case 0:
                    View view2 = simpleAdapter.getView(position, view, parent);
                    TextView tv = (TextView) view2.findViewById(R.id.tv_content);
                    tv.setText("男");
                    break;

                case 1:
                    View view3 = simpleAdapter.getView(position, view, parent);
                    TextView t = (TextView) view3.findViewById(R.id.tv_content);
                    t.setText("女");
                    break;
            }
        }

    }

    /**
     * 界面返回数据处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picDialog.dismiss();
        if (requestCode == CRE_CODE) {
            // 点击取消执行 意思相当于没有数据
            if (data == null) {
                return;
            } else {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap bitmap = bundle.getParcelable("data");
                    uriCreFile = ImagesUitls.saveBitmap(bitmap);
                    ImagesUitls.stratImageZoom(this, uriCreFile, ZOOM_CODE);
                }

            }
        } else if (requestCode == TUKU) {
            // 没有选择图片
            if (data == null) {
                return;
            } else {
                Uri uri = data.getData();
                System.out.println("tututtutuutuutututututtu");
                uriCreFile = ImagesUitls.converUri(getApplication(), uri);
                ImagesUitls.stratImageZoom(this, uriCreFile, ZOOM_CODE);
            }
        }
        if (requestCode == ZOOM_CODE) {
            Bitmap imgBitmap = ImagesUitls.getBitmapFromUri(uriCreFile, this);
            civ_pic.setImageBitmap(imgBitmap);
            imguri = uriCreFile.toString();
            LogUtil.e(imguri);
            startUp();
        }
    }

    /**
     * 上传文件
     */
    private void startUp() {
        LogUtil.e("开始上传！");
        String url = Constants.SERVICEADDRESS + "user/user_uploadImg.cake";
        Map<String, String> param = new HashMap<>();
        param.put("user_id", String.valueOf(MyUitls.getUid()));
        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");
        LogUtil.e(uriCreFile.getPath());

        OkHttpUtils.post()
                .addFile("uploadImage", "headimg.png", new File(uriCreFile.getPath()))
                .url(url)
                .params(param)
                .headers(headers)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtil.e(response);
                    }
                });
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        /**
         * 1.设置左边的图片按钮显示，以及事件 2.设置中间TextView显示的文字 3.设置右边的图片按钮显示，并设置事件
         */
        new TitleBuilder(this).setLeftImageRes(R.drawable.head_top_title_left_icon).setMiddleTitleText("修改资料")
                .setRightText("修改").setLeftTextOrImageListener(titleListener)
                .setRightTextOrImageListener(titleListener).initTitle(this);

    }

    /**
     * 标题栏的监听
     */
    private View.OnClickListener titleListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_left_imageview:
                    finish();
                    break;

                case R.id.title_right_textview:

                    break;
            }

        }
    };


}
