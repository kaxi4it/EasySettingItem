package com.guyj;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by kaxi4it on 2016/10/12.
 */
public class EasySettingItem extends LinearLayout{
    private ImageView icon;//左侧图标
    private TextView text;//中间文字
    private ImageView arrow;//右侧图标
    private CheckBox checkBox;//最右侧单选框
    private LayoutParams iconParams;//左侧图标的Params
    private LayoutParams textParams;//文字的Params
    private LayoutParams arrowParams;//右侧图标的Params
    private LayoutParams cbParams;//check的Params
    private int iconResID;//左侧图标资源ID
    private int arrowResID;//右侧图标资源ID，一般为向右箭头
    private String textContent;//文字内容
    private int textColor;//文字颜色
    private int textSize;//文字尺寸，sp/px/dp在xml中都支持
    private int leftMargin;//文字与左侧的外边距
    private int topMargin;//文字与上侧的外边距
    private int rightMargin;//文字与右侧的外边距
    private int bottomMargin;//文字与下侧的外边距
    private boolean isChecked;//单选框的选中状态，实时更新
    private int cbDrawableResID;//单选框的selector资源ID
    private long intervalTime=0l;//点击区间时间，2次点击间隔过短则判为无效点击，不做响应
    private long firstClickTime=0l;//记录第一次点击事件
    private long nextClickTime=0l;//记录第二次点击事件
    private Toast toast;
    private String toastMsg;
    private OnCheckBoxChangeListener cbListener;//含有checkBox时，可使用的listener,无checkBox时无效
    private OnItemClickListener itemListener;//含有checkBox时，可使用的listener,无checkBox时无效

    //含有checkBox时，优先返回checkbox与选中状态，其他控件也一并返回方便可能的其他操作
    public interface OnCheckBoxChangeListener{void checkBoxChange(CheckBox checkBox,boolean isChecked,LinearLayout EasyItem);}
    public interface OnItemClickListener{void itemClick(EasySettingItem EasyItem);}

    public void setCheckBoxChangekListener(OnCheckBoxChangeListener cbListener){this.cbListener=cbListener;}
    public void setOnItemClickListener(OnItemClickListener itemListener){this.itemListener=itemListener;}

    public ImageView getIcon() {
        return icon;
    }

    public TextView getText() {
        return text;
    }

    public ImageView getArrow() {
        return arrow;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public EasySettingItem(Context context) {
        super(context);
    }

    public EasySettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EasySettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context con, AttributeSet attrs) {
        initAttrs(attrs);
        initView(con);
    }

    private void initAttrs(AttributeSet attrs) {

        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs,
                R.styleable.EasySettingItem);

        arrowResID = typedArray.getResourceId(R.styleable.EasySettingItem_arrowResID, 0);
        iconResID = typedArray.getResourceId(R.styleable.EasySettingItem_iconResID, 0);
        cbDrawableResID = typedArray.getResourceId(R.styleable.EasySettingItem_cbDrawableResID, 0);
        textColor = typedArray.getColor(R.styleable.EasySettingItem_textColor, Color.parseColor("#000000"));
        isChecked = typedArray.getBoolean(R.styleable.EasySettingItem_isChecked, false);
        textContent = typedArray.getString(R.styleable.EasySettingItem_textContent);
        textSize = typedArray.getDimensionPixelOffset(R.styleable.EasySettingItem_textSize, sp2px(12));//得到的是px
        leftMargin = typedArray.getDimensionPixelOffset(R.styleable.EasySettingItem_textMarginLeft, 0);//得到的是px
        topMargin = typedArray.getDimensionPixelOffset(R.styleable.EasySettingItem_textMarginTop, 0);//得到的是px
        rightMargin = typedArray.getDimensionPixelOffset(R.styleable.EasySettingItem_textMarginRight, 0);//得到的是px
        bottomMargin = typedArray.getDimensionPixelOffset(R.styleable.EasySettingItem_textMarginBottom, 0);//得到的是px
        intervalTime = typedArray.getInt(R.styleable.EasySettingItem_intervalTime,0);
        toastMsg = typedArray.getString(R.styleable.EasySettingItem_toastMsg);
        typedArray.recycle();

    }

    private void initView(Context context) {
        if (iconResID != 0) {
            icon = new ImageView(context);
            icon.setImageResource(iconResID);
            iconParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(icon, iconParams);
        }
        text = new TextView(context);
        text.setText(textContent);
        text.setTextColor(textColor);
        text.setTextSize(px2sp(textSize));//该方法传入的是sp
        textParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        textParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);//传入px
        addView(text, textParams);
        if (arrowResID != 0) {
            arrow = new ImageView(context);
            arrow.setImageResource(arrowResID);
            arrowParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(arrow, arrowParams);
        }
        if (cbDrawableResID!=0){
            checkBox=new CheckBox(context);
            checkBox.setChecked(isChecked);
            checkBox.setClickable(false);
            checkBox.setBackgroundDrawable(null);
            checkBox.setButtonDrawable(cbDrawableResID);
            cbParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(checkBox, cbParams);
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.toggle();
                    isChecked=checkBox.isChecked();
                    if (cbListener!=null){
                        cbListener.checkBoxChange(checkBox,isChecked,EasySettingItem.this);
                    }
                    if (itemListener!=null){
                        itemListener.itemClick(EasySettingItem.this);
                    }
                }
            });
        }else{
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListener!=null){
                        itemListener.itemClick(EasySettingItem.this);
                    }
                }
            });
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstClickTime=System.currentTimeMillis();
                if (firstClickTime-nextClickTime<intervalTime){
                    if (!TextUtils.isEmpty(toastMsg)){
                        if (toast==null) {
                            toast = Toast.makeText(getContext(), toastMsg, Toast.LENGTH_SHORT);
                        }else{
                            toast.setText(toastMsg);
                            toast.setDuration(Toast.LENGTH_SHORT);
                        }
                        toast.show();
                    }
                    this.setClickable(false);
                }else{
                    this.setClickable(true);
                }
                nextClickTime=firstClickTime;
                break;
        }
        return super.onTouchEvent(event);
    }

    private int px2sp(float pxValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    private int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
