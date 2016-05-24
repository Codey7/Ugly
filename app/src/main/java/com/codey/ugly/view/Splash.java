package com.codey.ugly.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import com.codey.ugly.R;
import com.codey.ugly.core.BaseAppCompatActivity;
import com.codey.ugly.utils.SharedPreferencesUtil;

/**
 * Created by Mr.Codey on 2016/4/23.
 */
public class Splash extends BaseAppCompatActivity
{
    private LinearLayout mllSplash;

    @Override
    protected int getLayoutId()
    {
        return R.layout.splash;
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        mllSplash= (LinearLayout) findViewById(R.id.ll_splash);
        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(3000);
        mllSplash.startAnimation(aa);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                setMainActivity();
            }
        }, 3000);
    }

    public void setMainActivity()
    {
        Intent intent;
        int login_status= (int) SharedPreferencesUtil.getData(getApplicationContext(),"login_status",0);
        if (login_status==1)
        {
            intent=new Intent(Splash.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            intent=new Intent(Splash.this,Login.class);
            startActivity(intent);
            finish();
        }
    }
}
