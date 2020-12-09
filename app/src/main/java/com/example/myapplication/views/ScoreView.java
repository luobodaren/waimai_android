package com.example.myapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class ScoreView extends FrameLayout {

    private float score = 0; // 0-5
    private String number_of_fans = "";

    private ImageView iv_star_1;
    private ImageView iv_star_2;
    private ImageView iv_star_3;
    private ImageView iv_star_4;
    private ImageView iv_star_5;

    private TextView tv_fans;

    public ScoreView(@NonNull Context context) {
        this(context,null);
    }

    public ScoreView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScoreView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View view = View.inflate(context, R.layout.layout_score_and_fans,null);
        iv_star_1 = view.findViewById(R.id.iv_star_1);
        iv_star_2 = view.findViewById(R.id.iv_star_2);
        iv_star_3 = view.findViewById(R.id.iv_star_3);
        iv_star_4 = view.findViewById(R.id.iv_star_4);
        iv_star_5 = view.findViewById(R.id.iv_star_5);

        tv_fans = view.findViewById(R.id.tv_fans);
        this.addView(view);
    }

    public void setScore(float score){
        this.score = score;
        int tem = (int)score;
        switch (tem){
            case 0:
                break;
            case 1:
                iv_star_1.setImageResource(R.drawable.ic_star_red);
                iv_star_2.setImageResource(R.drawable.ic_star_gray);
                iv_star_3.setImageResource(R.drawable.ic_star_gray);
                iv_star_4.setImageResource(R.drawable.ic_star_gray);
                iv_star_5.setImageResource(R.drawable.ic_star_gray);
                break;
            case 2:
                iv_star_1.setImageResource(R.drawable.ic_star_red);
                iv_star_2.setImageResource(R.drawable.ic_star_red);
                iv_star_3.setImageResource(R.drawable.ic_star_gray);
                iv_star_4.setImageResource(R.drawable.ic_star_gray);
                iv_star_5.setImageResource(R.drawable.ic_star_gray);
                break;
            case 3:
                iv_star_1.setImageResource(R.drawable.ic_star_red);
                iv_star_2.setImageResource(R.drawable.ic_star_red);
                iv_star_3.setImageResource(R.drawable.ic_star_red);
                iv_star_4.setImageResource(R.drawable.ic_star_gray);
                iv_star_5.setImageResource(R.drawable.ic_star_gray);
                break;
            case 4:
                iv_star_1.setImageResource(R.drawable.ic_star_red);
                iv_star_2.setImageResource(R.drawable.ic_star_red);
                iv_star_3.setImageResource(R.drawable.ic_star_red);
                iv_star_4.setImageResource(R.drawable.ic_star_red);
                iv_star_5.setImageResource(R.drawable.ic_star_gray);
                break;
            case 5:
                iv_star_1.setImageResource(R.drawable.ic_star_red);
                iv_star_2.setImageResource(R.drawable.ic_star_red);
                iv_star_3.setImageResource(R.drawable.ic_star_red);
                iv_star_4.setImageResource(R.drawable.ic_star_red);
                iv_star_5.setImageResource(R.drawable.ic_star_red);
                break;
            default:
                iv_star_1.setImageResource(R.drawable.ic_star_gray);
                iv_star_2.setImageResource(R.drawable.ic_star_gray);
                iv_star_3.setImageResource(R.drawable.ic_star_gray);
                iv_star_4.setImageResource(R.drawable.ic_star_gray);
                iv_star_5.setImageResource(R.drawable.ic_star_gray);
                break;


        }
    }

    public void setFansStr(String str){
        number_of_fans = str;
        tv_fans.setText(str);
    }

}
