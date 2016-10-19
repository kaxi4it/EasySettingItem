package com.guyj;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by kaxi4it on 2016/10/12.
 */
public class EasySettingItem extends LinearLayout {
    private ImageView icon;
    private TextView text;
    private ImageView arrow;
    private LayoutParams iconParams;
    private LayoutParams textParams;
    private LayoutParams arrowParams;
    private int iconResID;
    private int arrowResID;
    private String textContent;
    private int textColor;
    private int textSize;
    private int leftMargin;
    private int topMargin;
    private int rightMargin;
    private int bottomMargin;


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
        textColor = typedArray.getColor(R.styleable.EasySettingItem_textColor, Color.parseColor("#000000"));
        textContent = typedArray.getString(R.styleable.EasySettingItem_textContent);
        textSize = typedArray.getDimensionPixelOffset(R.styleable.EasySettingItem_textSize, sp2px(12));//得到的是px
        leftMargin = typedArray.getDimensionPixelOffset(R.styleable.EasySettingItem_textMarginLeft, 0);//得到的是px
        topMargin = typedArray.getDimensionPixelOffset(R.styleable.EasySettingItem_textMarginTop, 0);//得到的是px
        rightMargin = typedArray.getDimensionPixelOffset(R.styleable.EasySettingItem_textMarginRight, 0);//得到的是px
        bottomMargin = typedArray.getDimensionPixelOffset(R.styleable.EasySettingItem_textMarginBottom, 0);//得到的是px

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
