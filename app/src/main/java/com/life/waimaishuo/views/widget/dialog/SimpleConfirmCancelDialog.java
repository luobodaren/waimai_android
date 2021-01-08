package com.life.waimaishuo.views.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.util.Utils;

public class SimpleConfirmCancelDialog extends Dialog {

    private TextView contentTv;
    private Button leftBt;
    private Button centerBt;
    private Button rightBt;

    ButtonClickListener buttonClickListener;

    private SimpleConfirmCancelDialog(@NonNull Context context) {
        this(context,R.style.mySimpleNoTitleDialog);
    }

    private SimpleConfirmCancelDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    private void initView(){
        View view = View.inflate(getContext(),
                R.layout.layout_dialog_simple_confirm_cancle,null);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        contentTv = view.findViewById(R.id.tv_tip);
        leftBt = view.findViewById(R.id.bt_left);
        centerBt = view.findViewById(R.id.bt_center);
        rightBt = view.findViewById(R.id.bt_right);

        leftBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonClickListener != null){
                    dismiss();
                    buttonClickListener.onButtonClick(v,1);
                }
            }
        });
        centerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonClickListener != null){
                    dismiss();
                    buttonClickListener.onButtonClick(v,2);
                }
            }
        });
        rightBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonClickListener != null){
                    dismiss();
                    buttonClickListener.onButtonClick(v,3);
                }
            }
        });

        setContentView(view);
    }

    private void setContentString(String text, @ColorRes int colorId){
        contentTv.setText(text);
        contentTv.setTextColor(getContext().getResources().getColor(colorId));
    }

    private void initButton(Button button,String text, @ColorRes int colorId, boolean isBold){
        button.setVisibility(View.VISIBLE);
        button.setText(text);
        button.setTextColor(getContext().getResources().getColor(colorId));
        button.getPaint().setFakeBoldText(isBold);
    }

    @Override
    public void show() {
        showAtCenter();
    }

    private boolean initShowLocation = false;
    private void showAtCenter(){
        if(initShowLocation){
            initShowLocation = true;
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.gravity = Gravity.CENTER;
//        params.width = (int) UIUtils.getInstance(requireContext()).scalePx(
//                getResources().getDimensionPixelSize(R.dimen.members_qr_code_dialog_width));
//        params.height = (int) UIUtils.getInstance(requireContext()).scalePx(
//                getResources().getDimensionPixelSize(R.dimen.members_qr_code_dialog_height));
            getWindow().setAttributes(params);
        }
        super.show();
    }


    public interface ButtonClickListener{
        /**
         * @param view
         * @param position  从左到右 1 2 3
         */
        void onButtonClick(View view,int position);
    }

    public static class Builder{

        private Context context;

        private String contentString = "";
        private int contentTextColorId;
        private String leftBtText = "";
        private int leftTextColorId;
        private boolean isLeftTextBold;

        private String centerBtText = "";
        private int centerTextColorId;
        private boolean isCenterTextBold;

        private String rightBtText = "";
        private int rightTextColorId;
        private boolean isRightTextBold;

        private boolean touchOutsizeCancelable = false;

        ButtonClickListener buttonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentString(String text, @ColorRes int colorId){
            this.contentString = text;
            this.contentTextColorId = colorId;
            return this;
        }

        public Builder initLeftBt(String text, @ColorRes int colorId, boolean isBold){
            this.leftBtText = text;
            this.leftTextColorId = colorId;
            isLeftTextBold = isBold;
            return this;
        }

        public Builder initCenterBt(String text, @ColorRes int colorId, boolean isBold){
            this.centerBtText = text;
            this.centerTextColorId = colorId;
            isCenterTextBold = isBold;
            return this;
        }

        public Builder initRightBt(String text, @ColorRes int colorId, boolean isBold){
            this.rightBtText = text;
            this.rightTextColorId = colorId;
            isRightTextBold = isBold;
            return this;
        }

        public Builder setOnButtonClickListener(ButtonClickListener buttonClickListener){
            this.buttonClickListener = buttonClickListener;
            return this;
        }

        public Builder setTouchOutsizeCancelable(boolean cancelable){
            this.touchOutsizeCancelable = cancelable;
            return this;
        }

        public SimpleConfirmCancelDialog build(){
            SimpleConfirmCancelDialog simpleConfirmCancelDialog
                    = new SimpleConfirmCancelDialog(context);
            simpleConfirmCancelDialog.setCanceledOnTouchOutside(touchOutsizeCancelable);
            simpleConfirmCancelDialog.setContentString(contentString,contentTextColorId);
            if(!"".equals(leftBtText)){
                simpleConfirmCancelDialog.initButton(
                        simpleConfirmCancelDialog.leftBt,leftBtText,leftTextColorId,isLeftTextBold);
            }
            if(!"".equals(centerBtText)){
                simpleConfirmCancelDialog.initButton(
                        simpleConfirmCancelDialog.centerBt,centerBtText,centerTextColorId,isCenterTextBold);
            }
            if(!"".equals(rightBtText)){
                simpleConfirmCancelDialog.initButton(
                        simpleConfirmCancelDialog.rightBt,rightBtText,rightTextColorId,isRightTextBold);
            }
            simpleConfirmCancelDialog.buttonClickListener = buttonClickListener;
            return simpleConfirmCancelDialog;
        }

    }

}
