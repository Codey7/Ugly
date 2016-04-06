package com.umeng.commm.ui.adapters.viewholders;

import android.text.TextUtils;
import android.widget.TextView;

import com.umeng.comm.core.beans.Category;
import com.umeng.comm.core.utils.DeviceUtils;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.widgets.RoundCornerImageView;

/**
 * Created by wangfei on 15/11/24.
 */
public class CategoryViewHolder extends ViewHolder{
    private TextView mTitletv;
    private RoundCornerImageView mIcon;
    private TextView mDestv;

    @Override
    protected int getItemLayout() {
        return ResFinder.getLayout("umeng_comm_category_item");
    }
    public void setFeedItem(Category category){
        mTitletv = findViewById(ResFinder.getId("category_item_title"));
        mTitletv.setText(category.name);
        mIcon = findViewById(ResFinder.getId("category_item_icon"));
        if (TextUtils.isEmpty(category.iconUrl)||category.iconUrl.equals(null)){

            mIcon.setImageDrawable(ResFinder.getDrawable("morentuf"));
        }else {
            mIcon.setImageUrl(category.iconUrl);
        }
        // 在代码里面设置CORNER RADIUS
        mIcon.setmCornerRaduis(DeviceUtils.dp2px(DeviceUtils.getContext(), 5));
        mDestv =findViewById(ResFinder.getId("category_item_des"));
        if (category.description.equals("null")){
            mDestv.setText(ResFinder.getString("umeng_comm_category_default"));
        }else {
            mDestv.setText(category.description);
        }
    }

}
