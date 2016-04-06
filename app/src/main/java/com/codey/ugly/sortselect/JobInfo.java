package com.codey.ugly.sortselect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codey.ugly.R;

/**
 * Created by Mr.Codey on 2016/3/31.
 * 选择工作类型
 */
public class JobInfo extends Activity implements View.OnClickListener
{
    private Button mbtNext;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_2);
        setTextView();
        mbtNext= (Button) findViewById(R.id.bt_per_next);

        mbtNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), GirlsInfo.class);
                startActivity(intent);
                overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom, android.support.design.R.anim.abc_slide_out_top);
            }
        });
    }

    private  void setTextView()
    {
         tv1= (TextView) findViewById(R.id.tv_select1);
         tv2= (TextView) findViewById(R.id.tv_select2);
         tv3= (TextView) findViewById(R.id.tv_select3);
         tv4= (TextView) findViewById(R.id.tv_select4);
         tv5= (TextView) findViewById(R.id.tv_select5);
         tv6= (TextView) findViewById(R.id.tv_select6);
         tv7= (TextView) findViewById(R.id.tv_select7);

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.tv_select1:
                tv1.setSelected(true);
                tv1.setTextColor(getResources().getColor(R.color.umeng_comm_white_color));
                break;
            case R.id.tv_select2:
                tv2.setSelected(true);
                tv2.setTextColor(getResources().getColor(R.color.umeng_comm_white_color));
                break;
            case R.id.tv_select3:
                tv3.setSelected(true);
                tv3.setTextColor(getResources().getColor(R.color.umeng_comm_white_color));
                break;
            case R.id.tv_select4:
                tv4.setSelected(true);
                tv4.setTextColor(getResources().getColor(R.color.umeng_comm_white_color));
                break;
            case R.id.tv_select5:
                tv5.setSelected(true);
                tv5.setTextColor(getResources().getColor(R.color.umeng_comm_white_color));
                break;
            case R.id.tv_select6:
                tv6.setSelected(true);
                tv6.setTextColor(getResources().getColor(R.color.umeng_comm_white_color));
            break;
            case R.id.tv_select7:
                tv7.setSelected(true);
                tv7.setTextColor(getResources().getColor(R.color.umeng_comm_white_color));
                break;

        }
    }
}
