package com.codey.ugly.view.sortselect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.codey.ugly.model.StringKey;
import com.codey.ugly.view.Login;
import com.codey.ugly.view.MainActivity;
import com.codey.ugly.R;
import com.codey.ugly.bean.SignUserBean;
import com.codey.ugly.bean.JudgeResult;
import com.codey.ugly.core.BaseAppCompatActivity;
import com.codey.ugly.utils.NetUtil;
import com.codey.ugly.utils.SharedPreferencesUtil;
import com.codey.ugly.utils.ToastUtils;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.Source;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.LoginListener;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mr.Codey on 2016/4/22.
 */
public class AccountSet extends BaseAppCompatActivity
{
    private EditText metUsername,metPwd,metRepwd;
    private Button mbtNext;
    private String username,pwd,account="",sex;
    public static final int type=0;
    private RadioButton mrb;
    RadioGroup mrg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getIntent()!=null)
        {
            account = getIntent().getStringExtra("account");
        }
        checkInput();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.account_set;
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        metUsername= (EditText) findViewById(R.id.et_user_name);
        metPwd= (EditText) findViewById(R.id.et_pwd);
        metRepwd= (EditText) findViewById(R.id.et_re_pwd);
        mbtNext= (Button) findViewById(R.id.bt_per_next);
        mrg= (RadioGroup) findViewById(R.id.rg_sex);
    }
    private void checkInput()
    {
        username=metUsername.getText().toString().trim();
        pwd=metPwd.getText().toString().trim();
        String repwd=metRepwd.getText().toString().trim();
        mrb= (RadioButton) findViewById(mrg.getCheckedRadioButtonId());
        sex=mrb.getText().toString();
        if (username==null||pwd==null||repwd==null)
        {
            ToastUtils.showCenter(this,"请输入内容！");
        }
        else if(!pwd.equals(repwd))
        {
            ToastUtils.showCenter(this,"两次输入不一致！");
        }
        else {
            loadToUmeng();
                 upLoad();
        }
    }

    private void loadToUmeng()
    {
        CommUser user = new CommUser();
        user.name = username;
        user.id = pwd;
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
                    SharedPreferencesUtil.savaData(getApplicationContext(), StringKey.UMENG_LOGIN_STATUS,1);
                } else
                {
                    Log.e("umeng","友盟登录失败！");
                }

            }
        });
    }
    private void upLoad()
    {
        SignUserBean userBean=new SignUserBean(account,0,username,pwd,sex);
        Call<JudgeResult> back= NetUtil.getInstance().getNetService().createUser(userBean);
        back.enqueue(new Callback<JudgeResult>()
        {

            @Override
            public void onResponse(Call<JudgeResult> call, Response<JudgeResult> response)
            {
                JudgeResult judgeResult= response.body();
                    if (judgeResult.getResult()==1)
                    {
                        getCookieString(response);
                        if (sex.equals("男生"))
                        {
                            Intent intent = new Intent(AccountSet.this, PersonInfo.class);
                            startActivity(intent);
                            SharedPreferencesUtil.savaData(getApplicationContext(),StringKey.ACCOUNT,account);
                            SharedPreferencesUtil.savaData(getApplicationContext(),StringKey.LOGIN_STATUS,1);
                            SharedPreferencesUtil.savaData(getApplicationContext(),StringKey.SEX,"男生");
                            overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom, android.support.design.R.anim.abc_slide_out_top);
                            finish();
                        }
                        else if (sex.equals("女生"))
                        {
                            Intent intent = new Intent(AccountSet.this, MainActivity.class);
                            startActivity(intent);
                            SharedPreferencesUtil.savaData(getApplicationContext(),StringKey.ACCOUNT,account);
                            SharedPreferencesUtil.savaData(getApplicationContext(),StringKey.LOGIN_STATUS,1);
                            SharedPreferencesUtil.savaData(getApplicationContext(),StringKey.SEX,"女生");
                            overridePendingTransition(android.support.design.R.anim.abc_fade_in, android.support.design.R.anim.abc_fade_out);
                        }
                    }
                else
                    {
                        Intent intent=new Intent(AccountSet.this,Login.class);
                        showDialog("该手机号已被注册，是否直接登录？",intent);
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
        NetUtil.setCookies(cookie);
    }
}
