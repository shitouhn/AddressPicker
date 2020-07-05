package com.example.myroom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;


import com.example.myroom.db.Area;
import com.example.myroom.util.DensityUtils;

import java.util.Stack;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;


/**
 * Created by zhong on 2019/7/8.
 */
public class AreaView extends LinearLayoutCompat {
    private Paint paint;
    private float radius;
    private float diameter;
    private float cx;
    public AreaView(Context context) {
        super(context);
        init();
    }

    public AreaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        setOrientation(VERTICAL);
        radius = DensityUtils.dp2px(4);
        diameter = 2 * radius;
        cx = DensityUtils.dp2px(15);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        int count = getChildCount();
        Stack<Float> redDotStack = new Stack<>();
        for (int i = 0; i < count; i++) {
            TextView child = (TextView) getChildAt(i);
            Paint.FontMetrics fontMetrics = child.getPaint().getFontMetrics();
            float baseline = (fontMetrics.descent - fontMetrics.ascent) / 2f - fontMetrics.descent;
            float cy = child.getTop() + Math.abs(fontMetrics.top) + baseline + radius;
            Area area = (Area) child.getTag();
            if ("0".equals(area.AreaNo)){
                paint.setStrokeWidth(DensityUtils.dp2px(1));
                paint.setStyle(Paint.Style.STROKE);
            }else {
                paint.setStyle(Paint.Style.FILL);
            }
            canvas.drawCircle(cx,cy,radius,paint);
            redDotStack.push(cy + radius);
            if (i > 0){
                float dy2 = redDotStack.pop() - diameter;
                float dy1 = redDotStack.pop();
                canvas.drawLine(cx,dy1,cx,dy2,paint);
                redDotStack.push(dy2);
            }
        }
    }
}
