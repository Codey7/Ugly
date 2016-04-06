package com.codey.ugly.sortselect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codey.ugly.R;

/**
 * Created by Mr.Codey on 2016/3/31.
 * 填写个人信息
 */
public class PersonInfo extends Activity
{
    private Button mbtNext;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_1);
        mbtNext= (Button) findViewById(R.id.bt_per_next);
        mbtNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),JobInfo.class);
                startActivity(intent);
                overridePendingTransition(android.support.design.R.anim.abc_slide_in_bottom, android.support.design.R.anim.abc_slide_out_top);
            }
        });
    }
}
