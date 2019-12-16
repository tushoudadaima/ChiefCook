package com.example.administrator.shixun.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shixun.AboutActivity;
import com.example.administrator.shixun.CountActivity;
import com.example.administrator.shixun.LoginAndSignup.LoginActivity;
import com.example.administrator.shixun.MyApplication;
import com.example.administrator.shixun.R;
import com.example.administrator.shixun.SettingActivity;

import java.io.File;

import static com.mob.tools.utils.DeviceHelper.getApplication;


public class FourthFragment extends Fragment {
    private CustomeClickListener listener;
//    private ImageView login;
    private LinearLayout aboutus;
    private TextView mine_login;
    private LinearLayout setting;
    private LinearLayout vip;
    private TextView dashang;
    private ImageView head_iv;
//    private String phone;
    private String text;
    private String phone;

    private LinearLayout share;

    private String wang_zhi;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fourfragment, container,false);
//        login=view.findViewById(R.id.head_iv);
        aboutus=view.findViewById(R.id.aboutus);
        dashang=view.findViewById(R.id.dashang);
        mine_login=view.findViewById(R.id.mine_login);
        setting=view.findViewById(R.id.setting);
        share=view.findViewById(R.id.share);
        vip=view.findViewById(R.id.vip);
        head_iv = view.findViewById(R.id.head_iv);

        wang_zhi = this.getString(R.string.wang_zhi);

//        okHttpClient = new OkHttpClient();
        registerListeners();

        MyApplication myApplication = (MyApplication) getApplication();
        phone = myApplication.getPhone();

        if(phone.equals("null")){
            text = "点击登录";
        }else {
            text =phone;
            mine_login.setText(text);
        }
        setImage();//加载头像
        return view;
    }


    @Override
    public void onResume() {//每次点击frament都加载一次头像
        setImage();
        super.onResume();
    }

    private void registerListeners() {
        listener = new CustomeClickListener();
        head_iv.setOnClickListener(listener);
        aboutus.setOnClickListener(listener);
        dashang.setOnClickListener(listener);
        share.setOnClickListener(listener);
        mine_login.setOnClickListener(listener);
        setting.setOnClickListener(listener);
        vip.setOnClickListener(listener);
    }
    class CustomeClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.aboutus:
                    Intent intent2 = new Intent(getActivity(), AboutActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.mine_login:
                case R.id.head_iv:
                    text=mine_login.getText().toString();
                    if (text.equals("点击登录")){//跳转登录页
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }else {//跳转个人页
                        Intent intent4 = new Intent(getActivity(), CountActivity.class);
                        startActivity(intent4);
                    }
                    break;

                case R.id.setting:
                    Intent intent3 = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.share:
                    Intent qqIntent = new Intent(Intent.ACTION_SEND);
                    qqIntent.setPackage("com.tencent.mobileqq");
                    qqIntent.setType("text/plain");
                    qqIntent.putExtra(Intent.EXTRA_TEXT, "快来一起加入餐谋长吧！");
                    startActivity(qqIntent);
                    break;
                case R.id.dashang:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    //    设置Title的图标
                    //    设置Title的内容
                    builder.setTitle("打赏餐谋长");
                    //    设置Content来显示一个信息
                    builder.setMessage("很高兴大家一致以来的支持与鼓励，这是捐赠页面，如果有能力的朋友，欢迎请我们小组喝杯饮料，来份外卖，金额随意，感激不尽！无论捐赠与否，都感谢大家一路陪伴，爱你们");
                    //    设置一个PositiveButton
                    builder.setPositiveButton("跳转支付宝打赏", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String intentFullUrl = "intent://platformapi/startapp?saId=10000007&" +
                                    "clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2Ffkx070643dcbygq7d1cs591?t=1575247397502%3F_s" +   //这里的URLcode换成扫码得到的结果
                                    "%3Dweb-other&_t=1472443966571#Intent;" +
                                    "scheme=alipayqr;package=com.eg.android.AlipayGphone;end";
                            try {
                                Intent intent = Intent.parseUri(intentFullUrl, Intent.URI_INTENT_SCHEME);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    //    设置一个NegativeButton
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast.makeText(
                                    getActivity(),
                                    "没关系,感谢陪伴！",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });
                    //    设置一个NeutralButton
                    builder.setNeutralButton("忽略", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                        }
                    });
                    //    显示出该对话框
                    builder.show();
                    break;

            }

        }
    }

    private void setImage() {
        @SuppressLint("SdCardPath")
        String path1 = "/data/data/com.example.administrator.shixun/files"+"/CanMouZhang/"+phone+".jpg";
        File file = new File("/data/data/com.example.administrator.shixun/files"+"/CanMouZhang/"+phone+".jpg");
        if(file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(path1);
            head_iv.setImageBitmap(bitmap);
        }
    }

}


