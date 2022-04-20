package com.example.module_clock;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class XpzClockView extends ConstraintLayout {
    private final ConstraintLayout constraintLayout;
    private final ConstraintSet constraintSet;
    private final List<TextView> numberTextViews = new ArrayList<>();
    private final ImageView iv1_center,iv2_hour,iv3_minute,iv4_second;
    private final Chronometer cr1;
    private final int[] numbers = {12,1,2,3,4,5,6,7,8,9,10,11};
    private final Calendar calendar;
    public XpzClockView(Context context) {
        this(context,null);
    }

    public XpzClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public XpzClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.xpzclock_layout,this);

        constraintLayout = findViewById(R.id.c1);
        constraintSet = new ConstraintSet();
        iv1_center = findViewById(R.id.iv1_center);
        iv2_hour = findViewById(R.id.iv2_hour);
        iv3_minute = findViewById(R.id.iv3_minute);
        iv4_second = findViewById(R.id.iv4_second);
        iv2_hour.setPivotX(0.5f);
        iv2_hour.setPivotY(0);
        iv3_minute.setPivotX(0.5f);
        iv3_minute.setPivotY(0);
        iv4_second.setPivotX(0.5f);
        iv4_second.setPivotY(0);
        calendar = Calendar.getInstance();
        cr1 = findViewById(R.id.cr1);
        cr1.setOnChronometerTickListener(chronometer -> doChronometerTick());
        initMainActivityView(context);
        cr1.start();
    }
    private void doChronometerTick() {
        float angleSecond;
        float angleMinute;
        float angleHour;
        angleSecond = calendar.get(Calendar.SECOND) * 6;
        angleMinute = calendar.get(Calendar.MINUTE) * 6 + calendar.get(Calendar.SECOND) * 6 / 60;
        angleHour = calendar.get(Calendar.HOUR) * 30 + calendar.get(Calendar.MINUTE) * 30 / 60 + calendar.get(Calendar.SECOND) * 30 / 3600;
        iv4_second.setRotation(180 + angleSecond);
        iv3_minute.setRotation(180 + angleMinute);
        iv2_hour.setRotation(180 + angleHour);
        calendar.add(Calendar.SECOND,1);
    }

    private void initMainActivityView(Context context) {
        for (int number : numbers) {
            TextView textView = new TextView(context);
            textView.setId(View.generateViewId());
            textView.setText(number + "");
            textView.setTextSize(24);
            textView.setTextColor(Color.BLACK);
            constraintLayout.addView(textView);
            numberTextViews.add(textView);
        }
        constraintSet.clone(constraintLayout);
        for (int i = 0; i < numberTextViews.size(); i++) {
            TextView t = numberTextViews.get(i);
            constraintSet.connect(t.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP);
            constraintSet.connect(t.getId(),ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM);
            constraintSet.connect(t.getId(),ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START);
            constraintSet.connect(t.getId(),ConstraintSet.END,ConstraintSet.PARENT_ID,ConstraintSet.END);
            constraintSet.constrainCircle(t.getId(),iv1_center.getId(),200,30*i);
            constraintSet.applyTo(constraintLayout);
        }
    }
}
