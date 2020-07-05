package com.example.myroom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.example.myroom.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zhong on 2019/6/4.
 */
public class RoundRecyclerView extends RecyclerView {
    private static final int DEFAULT_RADIUS = 0;
    private float[] radiusArray = new float[8];
    public RoundRecyclerView(@NonNull Context context) {
        super(context);
    }

    public RoundRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RoundRecyclerView);
        float radius = a.getDimension(R.styleable.RoundRecyclerView_radius, DEFAULT_RADIUS);// 默认为0dp
        float topLeftRadius =  a.getDimension(R.styleable.RoundRecyclerView_topLeftRadius, DEFAULT_RADIUS);
        float topRightRadius = a.getDimension(R.styleable.RoundRecyclerView_topRightRadius, DEFAULT_RADIUS);
        float bottomRightRadius = a.getDimension(R.styleable.RoundRecyclerView_bottomRightRadius, DEFAULT_RADIUS);
        float bottomLeftRadius = a.getDimension(R.styleable.RoundRecyclerView_bottomLeftRadius, DEFAULT_RADIUS);
        a.recycle();

        if (topLeftRadius == 0 && topRightRadius == 0 && bottomLeftRadius == 0 && bottomRightRadius == 0){
            topLeftRadius = radius;
            topRightRadius = radius;
            bottomLeftRadius = radius;
            bottomRightRadius = radius;
        }
        //左上角
        radiusArray[0] = topLeftRadius;
        radiusArray[1] = topLeftRadius;
        //右上角
        radiusArray[2] = topRightRadius;
        radiusArray[3] = topRightRadius;
        //右下角
        radiusArray[4] = bottomRightRadius;
        radiusArray[5] = bottomRightRadius;
        //左下角
        radiusArray[6] = bottomLeftRadius;
        radiusArray[7] = bottomLeftRadius;
    }

    @Override
    public void draw(Canvas c) {
        if (path == null){
            path = new Path();
            int w = getWidth();
            int h = getHeight();
            path.addRoundRect(new RectF(0,0,w,h),radiusArray, Path.Direction.CW);
        }
        c.clipPath(path);
        super.draw(c);
    }

    Path path;
}
