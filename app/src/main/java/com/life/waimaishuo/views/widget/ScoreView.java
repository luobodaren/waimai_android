package com.life.waimaishuo.views.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.life.waimaishuo.R;

public class ScoreView extends FrameLayout {

    private int score; // 0-5
    private int number_of_fans = 0;

    private ImageView iv_star_1;
    private ImageView iv_star_2;
    private ImageView iv_star_3;
    private ImageView iv_star_4;
    private ImageView iv_star_5;

    private TextView tv_title;
    private TextView tv_fans;

    public ScoreView(@NonNull Context context) {
        this(context,null);
    }

    public ScoreView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScoreView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScoreView);
        score = typedArray.getInteger(R.styleable.ScoreView_score,5);
        number_of_fans = typedArray.getInteger(R.styleable.ScoreView_fans_number,0);
        typedArray.recycle();

        init(context);
    }

    private void init(Context context){
        View view = View.inflate(context, R.layout.layout_score_and_fans,null);
        iv_star_1 = view.findViewById(R.id.iv_star_1);
        iv_star_2 = view.findViewById(R.id.iv_star_2);
        iv_star_3 = view.findViewById(R.id.iv_star_3);
        iv_star_4 = view.findViewById(R.id.iv_star_4);
        iv_star_5 = view.findViewById(R.id.iv_star_5);

        tv_title = view.findViewById(R.id.tv_title);
        tv_fans = view.findViewById(R.id.tv_fans);
        this.addView(view);

        setScore(score);
        setFansStr(number_of_fans);
    }

    public void setScore(int score){
        this.score = score;
        switch (score){
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

    public void setFansStr(int num){
        number_of_fans = num;

        tv_fans.setText(getContext().getString(R.string.number_of_fans,String.valueOf(num)));
    }

    public void hideTitle(){
        tv_title.setVisibility(GONE);
    }

    public void hideFans(){
        tv_fans.setVisibility(GONE);
    }

    public void showTitle(){
        tv_title.setVisibility(VISIBLE);
    }

    public void showFans(){
        tv_fans.setVisibility(VISIBLE);
    }

}
