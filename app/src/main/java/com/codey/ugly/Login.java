package com.codey.ugly;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codey.ugly.bean.BaseUserBean;
import com.codey.ugly.bean.JudgeResult;
import com.codey.ugly.core.BaseAppCompatActivity;
import com.codey.ugly.sortselect.PersonInfo;
import com.codey.ugly.utils.NetUtil;
import com.codey.ugly.utils.SharedPreferencesUtil;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.Source;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.LoginListener;

import okhttp3.Headers;
import okhttp3.internal.framed.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Mr.Codey on 2016/3/30.
 */
public class Login extends BaseAppCompatActivity implements View.OnClickListener
{
    private Button mbtLogin;
    private CommunitySDK mCommSDK;
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
                    upload();
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

        mCommSDK = CommunityFactory.getCommSDK(getApplicationContext());
// 初始化sdk，请传递ApplicationContext
        mCommSDK.initSDK(getApplicationContext());

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
                break;
            case R.id.tv_not_login:
                intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
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
            mCommSDK.loginToUmengServer(getApplicationContext(), user, new LoginListener()
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

                }
            });
        }
    }

    private void upload()
    {
        BaseUserBean baseUserBean = new BaseUserBean(account, pwd, 0);
        Call<JudgeResult> call = NetUtil.getInstance().getNetService().variUser(baseUserBean);
        call.enqueue(new Callback<JudgeResult>()
        {
            @Override
            public void onResponse(Call<JudgeResult> call, Response<JudgeResult> response)
            {
                JudgeResult judgeResult= response.body();
                if (judgeResult.getResult()==1)
                {
                    getCookieString(response);
                    Intent intent = new Intent(Login.this, PersonInfo.class);
                    startActivity(intent);
                    SharedPreferencesUtil.savaData(Login.this,"login_status",1);
                    overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom, android.support.design.R.anim.abc_slide_out_top);
                }
                else if(judgeResult.getResult()==2)
                {
                    showToast("密码错误！");
                }
                else if (judgeResult.getResult()==3)
                {
                    showToast("用户名不存在！");
                }
            }

            @Override
            public void onFailure(Call<JudgeResult> call, Throwable t)
            {

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
