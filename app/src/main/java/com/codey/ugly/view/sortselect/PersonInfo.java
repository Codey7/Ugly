package com.codey.ugly.view.sortselect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codey.ugly.R;
import com.codey.ugly.core.BaseAppCompatActivity;

/**
 * Created by Mr.Codey on 2016/3/31.
 * 填写个人信息
 */
public class PersonInfo extends BaseAppCompatActivity
{
    private Button mbtNext;
    private EditText metHeight,metWeight;
    private int height,weight;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mbtNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkInput();
            }
        });
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.personal_info_1;
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        mbtNext = (Button) findViewById(R.id.bt_per_next);
        metHeight= (EditText) findViewById(R.id.et_height);
        metWeight= (EditText) findViewById(R.id.et_weight);
    }

    /**
     * 重构的时候这个可以直接提到base里
     */
    private void checkInput()
    {
        String heigh=metHeight.getText().toString().trim();
        String weig=metHeight.getText().toString().trim();
        if (heigh.equals("")||weig.equals(""))
        {
            showToast(getResources().getString(R.string.wrong_input));
        }
        else
        {
            height=Integer.parseInt(heigh);
            weight=Integer.parseInt(weig);
            Intent intent=new Intent(PersonInfo.this,GirlsInfo.class);
            Bundle b=new Bundle();
            b.putInt("height",height);
            b.putInt("weight",weight);
            intent.putExtras(b);
            startActivity(intent);
            overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom, android.support.design.R.anim.abc_slide_out_top);
        }

    }
}
