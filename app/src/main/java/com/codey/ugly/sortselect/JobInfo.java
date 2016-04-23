package com.codey.ugly.sortselect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codey.ugly.R;
import com.codey.ugly.core.BaseAppCompatActivity;
import com.codey.ugly.core.BasePersonInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Codey on 2016/3/31.
 * 选择工作类型
 */
public class JobInfo extends BasePersonInfo implements View.OnClickListener
{
    private Button mbtNext;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mbtNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView[] tvs={tv1,tv2,tv3,tv4,tv5,tv6,tv7};
                ArrayList list= getCheckText(tvs);
                Intent nextintent=new Intent(JobInfo.this,GirlsInfo.class);
                Intent intent=getIntent();
                Bundle b=intent.getExtras();
                b.putStringArrayList("job",list);
                nextintent.putExtras(b);
                startActivity(nextintent);
                finish();
                overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom, android.support.design.R.anim.abc_slide_out_top);
            }
        });
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.person_info_2;
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
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
        mbtNext= (Button) findViewById(R.id.bt_per_next);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.tv_select1:
                setTextView(tv1);
                break;
            case R.id.tv_select2:
                setTextView(tv2);
                break;
            case R.id.tv_select3:
                setTextView(tv3);
                break;
            case R.id.tv_select4:
                setTextView(tv4);
                break;
            case R.id.tv_select5:
                setTextView(tv5);
                break;
            case R.id.tv_select6:
                setTextView(tv6);
            break;
            case R.id.tv_select7:
                setTextView(tv7);
                break;

        }
    }

}
