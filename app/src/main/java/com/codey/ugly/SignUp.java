package com.codey.ugly;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codey.ugly.core.BaseAppCompatActivity;
import com.codey.ugly.sortselect.PersonInfo;
import com.codey.ugly.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.IdentifyNumPage;

/**
 * 注册
 * Created by Mr.Codey on 2016/4/8.
 */
public class SignUp extends BaseAppCompatActivity implements View.OnClickListener
{
    private EditText etNumber, etCode;
    private Button btGetcodes, btSignin;
    private TextView tvTime;
    EventHandler eventHandler;
    private int time = 59;
    private String number;

    //秒表计时
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    tvTime.setText(time + "s后重新获取");
                    time--;
                    if (time > 0)
                    {
                        handler.sendEmptyMessageDelayed(1, 1000);
                    } else
                    {
                        btGetcodes.setClickable(true);
                        tvTime.setVisibility(View.INVISIBLE);
                        time = 59;
                    }
                    break;
                case 0:
                    showDialog("该手机号已被注册，是否直接登录？",null);
                    //注销短信接口
                    SMSSDK.unregisterEventHandler(eventHandler);
                    break;
                case 2:
                    Intent intent = new Intent(SignUp.this, PersonInfo.class);
                    intent.putExtra("account",number);
                    startActivity(intent);
                    overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom, android.support.design.R.anim.abc_slide_out_top);
                    finish();
                   // SMSSDK.unregisterAllEventHandler();
                    break;
                case 3:
                    ToastUtils.showCenter(SignUp.this, "提交失败");
                    break;
                case 4:
                    ToastUtils.showCenter(SignUp.this, "验证失败");
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
        return R.layout.sign_up;
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        etNumber = (EditText) findViewById(R.id.editText);
        etCode = (EditText) findViewById(R.id.et_codes);
        btGetcodes = (Button) findViewById(R.id.bt_code);
        btSignin = (Button) findViewById(R.id.bt_signup);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvTime.setVisibility(View.INVISIBLE);
        btGetcodes.setOnClickListener(this);
        btSignin.setOnClickListener(this);
    }

    /**
     * 判断输入的是否是正确的手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles)
    {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 短信验证
     */
    public void smsVal()
    {


        eventHandler = new EventHandler()
        {

            @Override
            public void afterEvent(int event, int result, Object data)
            {
                /*if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
                {*/
                    if (result == SMSSDK.RESULT_COMPLETE)
                    {
                        /*boolean smart = (Boolean) data;
                        if (smart)
                        {
                            //通过智能验证
                            handler.sendEmptyMessage(0);
                        } else
                        {*/
                            //依然走短信验证
                            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
                            {
                                //提交验证码成功
                                handler.sendEmptyMessage(2);
                                afterSubmit(result,data);
                                Log.i("val",data.toString());

                            } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
                            {
                                //获取验证码成功
                            }
                            else
                            {
                                handler.sendEmptyMessage(3);
                            }
                        }

                else
                {
                    handler.sendEmptyMessage(4);
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        // SMSSDK.getVerificationCode(country, phone);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_code:

                number = etNumber.getText().toString().trim();
                if (number.equals(""))
                {
                    ToastUtils.showCenter(SignUp.this, this.getResources().getString(R.string.input_number));
                } else if (!isMobileNO(number))
                {
                    ToastUtils.showCenter(SignUp.this, this.getResources().getString(R.string.check_number));
                } else
                {
                    smsVal();
                    SMSSDK.getVerificationCode("86", number);
                    //显示倒计时
                    tvTime.setVisibility(View.VISIBLE);
                    //设置按钮不可点击
                    btGetcodes.setClickable(false);
                    handler.sendEmptyMessage(1);
                }


                break;
            case R.id.bt_signup:
                String code = etCode.getText().toString().trim();
                if (code.equals(""))
                {
                    ToastUtils.showCenter(SignUp.this, getResources().getString(R.string.input_code));
                } else
                {
                    smsVal();
                    SMSSDK.submitVerificationCode("86",number,code);
                }
                break;
        }
    }

    private void afterSubmit(final int result, final Object data) {


                if(result == -1) {
                    HashMap message = new HashMap();
                    message.put("res", Boolean.valueOf(true));
                    message.put("page", Integer.valueOf(2));
                    message.put("phone", data);
                } else {
                    ((Throwable)data).printStackTrace();
                    String message1 = ((Throwable)data).getMessage();
                    int resId = 0;

                    try {
                        JSONObject e = new JSONObject(message1);
                        int status = e.getInt("status");
                        //resId = com.mob.tools.utils.R.getStringRes(IdentifyNumPage.this.activity, "smssdk_error_detail_" + status);
                    } catch (JSONException var5) {
                        var5.printStackTrace();
                    }
                }

            }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (eventHandler!=null)
        {
            SMSSDK.unregisterEventHandler(eventHandler);
        }
    }
}
