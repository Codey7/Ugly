package com.codey.ugly.core;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.codey.ugly.utils.ToastUtils;

/**
 * Created by Mr.Codey on 2016/4/8.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initViews(savedInstanceState);
    }

    /**
     * 获取layoutid
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initViews(Bundle savedInstanceState);

    /*********
     * Toast *
     *********/

    public void showToast(String msg) {
        this.showToast(msg, Toast.LENGTH_SHORT);
    }


    public void showToast(String msg, int duration) {
        if (msg == null) return;
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            ToastUtils.show(this, msg, duration);
        } else {
            ToastUtils.show(this, msg, ToastUtils.LENGTH_SHORT);
        }
    }


    public void showToast(int resId) {
        this.showToast(resId, Toast.LENGTH_SHORT);
    }


    public void showToast(int resId, int duration) {
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            ToastUtils.show(this, resId, duration);
        } else {
            ToastUtils.show(this, resId, ToastUtils.LENGTH_SHORT);
        }
    }

    /*********
     * Toast *
     *********/


    public void showDialog(String title, final Intent intent)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(title)
                .setPositiveButton("是", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (intent==null)
                        {
                            finish();
                        }
                        else {
                            startActivity(intent);
                            finish();
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }
}
