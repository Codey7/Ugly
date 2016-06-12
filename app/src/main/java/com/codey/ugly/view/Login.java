package com.codey.ugly.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codey.ugly.R;
import com.codey.ugly.bean.BaseUserBean;
import com.codey.ugly.core.BaseAppCompatActivity;
import com.codey.ugly.model.StringKey;
import com.codey.ugly.utils.Md5Util;
import com.codey.ugly.view.sortselect.PersonInfo;
import com.codey.ugly.utils.NetUtil;
import com.codey.ugly.utils.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.Source;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.imageloader.utils.Md5Helper;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.LoginListener;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Mr.Codey on 2016/3/30.
 */
public class Login extends BaseAppCompatActivity implements View.OnClickListener
{
    private Button mbtLogin;

    private EditText et1, et2;
    private TextView tv_signup, tvNologin;
    private String account, pwd;

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    /*upload();*/
                    Intent intent = new Intent(Login.this, PersonInfo.class);
                    startActivity(intent);
                    SharedPreferencesUtil.savaData(Login.this, StringKey.LOGIN_STATUS, 1);
                    overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom, android.support.design.R.anim.abc_slide_out_top);
                    break;
                case 0:
                    showDialog("umeng登录失败", null);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        tv_signup = (TextView) findViewById(R.id.tv_signup);
        et1 = (EditText) findViewById(R.id.editText);
        et2 = (EditText) findViewById(R.id.editpwd);
        mbtLogin = (Button) findViewById(R.id.login);
        tvNologin = (TextView) findViewById(R.id.tv_not_login);
        tv_signup.setOnClickListener(this);
        mbtLogin.setOnClickListener(this);
        tvNologin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {

        Intent intent;
        switch (v.getId())
        {
            case R.id.tv_signup:
                intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                break;
            case R.id.login:
                umengUser();
                //upload();
                break;
            case R.id.tv_not_login:
                umengDefault();
                intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.support.design.R.anim.abc_fade_in, android.support.design.R.anim.abc_fade_out);
                finish();
                break;
        }
    }


    public void umengUser()
    {
        CommUser user = new CommUser();
        user.name = et1.getText().toString().trim();
        user.id = et2.getText().toString().trim();
        account = user.name;
        pwd = user.id;
        user.source = Source.SELF_ACCOUNT;// 登录系统来源


        if (account.equals("") || pwd.equals(""))
        {
            showToast("输入有误！");
        } else
        {
            CommunityFactory.getCommSDK(getApplicationContext()).loginToUmengServer(getApplicationContext(), user, new LoginListener()
            {
                @Override
                public void onStart()
                {

                }

                @Override
                public void onComplete(int stCode, CommUser commUser)
                {
                    Log.d("tag", "login result is" + stCode);          //获取登录结果状态码
                    if (ErrorCode.NO_ERROR == stCode)
                    {
                        handler.sendEmptyMessage(1);
                    } else
                    {
                        handler.sendEmptyMessage(0);
                    }

                    /*Log.d("tag", "login result is" + stCode);          //获取登录结果状态码
                    if (ErrorCode.NO_ERROR == stCode)
                    {
                        SharedPreferencesUtil.savaData(getApplicationContext(),"umeng_login_status",1);
                    } else
                    {
                        Log.e("umeng","友盟登录失败！");
                    }*/
                }
            });
        }
    }

    public void umengDefault()
    {
        CommUser user = new CommUser();
        user.name = "用户123";
        user.id = "123";
        user.source = Source.SELF_ACCOUNT;// 登录系统来源
        CommunityFactory.getCommSDK(getApplicationContext()).loginToUmengServer(getApplicationContext(), user, new LoginListener()
            {
                @Override
                public void onStart()
                {

                }

                @Override
                public void onComplete(int stCode, CommUser commUser)
                {
                    Log.d("tag", "login result is" + stCode);          //获取登录结果状态码
                    if (ErrorCode.NO_ERROR == stCode)
                    {
                    } else
                    {
                        handler.sendEmptyMessage(0);
                    }

                    /*Log.d("tag", "login result is" + stCode);          //获取登录结果状态码
                    if (ErrorCode.NO_ERROR == stCode)
                    {
                        SharedPreferencesUtil.savaData(getApplicationContext(),"umeng_login_status",1);
                    } else
                    {
                        Log.e("umeng","友盟登录失败！");
                    }*/
                }
            });
    }

    private void upload()
    {
        String md5Pwd= Md5Util.parseStrToMd5L32(pwd);
        BaseUserBean baseUserBean = new BaseUserBean(account, md5Pwd, 0);
        Gson gson=new Gson();
        String s=gson.toJson(baseUserBean);
        String ss="user="+"\""+s+"\"";
        Log.i("Gson",ss);
        Call<String> call = NetUtil.getInstance().getNetService().variUser(ss);
        call.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                Log.i("status",response.code()+"code");
                /*JudgeResult judgeResult= response.body();
                if (judgeResult!=null)
                {
                    if (judgeResult.getResult() == 1)
                    {
                        getCookieString(response);
                        Intent intent = new Intent(Login.this, PersonInfo.class);
                        startActivity(intent);
                        SharedPreferencesUtil.savaData(Login.this, "login_status", 1);
                        overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom, android.support.design.R.anim.abc_slide_out_top);
                    } else if (judgeResult.getResult() == 2)
                    {
                        showToast("密码错误！");
                    } else if (judgeResult.getResult() == 3)
                    {
                        showToast("用户名不存在！");
                    }
                }
                else
                {
                    Log.i("back",response.errorBody().toString());
                }*/
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Log.i("status",t.getMessage()+" message");
            }
        });
    }
    private void getCookieString(Response response) {
        Headers headers=response.headers();
        String cookie=headers.get("Set_Cookie");
        if (null!=cookie)
            NetUtil.setCookies(cookie);
    }
}
