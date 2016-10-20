package com.easy.easysettingitem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guyj.EasySettingItem;

public class MainActivity extends AppCompatActivity {
    private EasySettingItem item1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        item1=(EasySettingItem)findViewById(R.id.list_item1);
        item1.setOnItemClickListener(new EasySettingItem.OnItemClickListener() {
            @Override
            public void itemClick(EasySettingItem EasyItem) {
                Log.i("tag",EasyItem.getCheckBox().isChecked()+"");
            }
        });
        item1.setCheckBoxChangekListener(new EasySettingItem.OnCheckBoxChangeListener() {
            @Override
            public void checkBoxChange(CheckBox checkBox, boolean isChecked, LinearLayout EasyItem) {
                Log.i("tag","box");
            }
        });

    }
}
