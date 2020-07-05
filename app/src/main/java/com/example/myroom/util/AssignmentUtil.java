package com.example.myroom.util;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

/**
 * Created by zhong on 2018/12/7.
 */
public class AssignmentUtil {

    /**
     * 使用图片
     * @param view
     * @param url
     */
    public static void setImage(ImageView view, String url){
        //setImage(view,url, R.drawable.placeholder);
    }

    public static void setImage(ImageView view, @DrawableRes int id){
        //ImageProvider.with(view.getContext()).load(id).into(view);
    }


    public static void setImage(ImageView view, String url, @DrawableRes int placeholder){
        if (!StringUtils.isEmpty(url)){
            view.post(()->{
                int width = view.getWidth();
                int height = view.getHeight();
                LogUtils.d("ImageSize","width = " + width + ", height = " + height);
                //ImageProvider.with(view.getContext()).load(url).override(width,height).placeHolder(placeholder).into(view);
            });
        }else {
            view.setImageResource(placeholder);
        }

    }

    public static void setText(TextView tv, int value){
        setText(tv, String.valueOf(value));
    }

    public static void setText(TextView view, CharSequence text){
        if (StringUtils.isEmpty(text)) text = "";
        view.setText(text);
    }
}
