package com.codey.ugly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codey.ugly.sortselect.PersonInfo;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.Source;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.LoginListener;

import org.w3c.dom.Text;

/**
 * Created by Mr.Codey on 2016/3/30.
 */
public class Login extends Activity
{
    private Button mbtLogin;
    private CommunitySDK mCommSDK;
    private EditText et1,et2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        et1= (EditText) findViewById(R.id.editText);
        et2= (EditText) findViewById(R.id.editpwd);
        mCommSDK = CommunityFactory.getCommSDK(getApplicationContext());
// 初始化sdk，请传递ApplicationContext
        mCommSDK.initSDK(getApplicationContext());
        mbtLogin= (Button) findViewById(R.id.login);
        mbtLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                CommUser user = new CommUser();
                user.name = et1.getText().toString().trim();
                user.id = et2.getText().toString().trim();
                user.source = Source.SELF_ACCOUNT;// 登录系统来源
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
                            Intent intent = new Intent(Login.this, PersonInfo.class);
                            startActivity(intent);
                            overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom, android.support.design.R.anim.abc_slide_out_top);
                        }

                    }
                });

            }
        });
    }
}
