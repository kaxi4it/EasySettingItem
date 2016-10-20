package com.easy.easysettingitem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.guyj.EasySettingItem;

public class MainActivity extends AppCompatActivity {
    private EasySettingItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        item=(EasySettingItem)findViewById(R.id.list_item);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((EasySettingItem)v).getCheckBox().setChecked(! ((EasySettingItem)v).getCheckBox().isChecked());
                ((EasySettingItem)v).getCheckBox().toggle();
                Log.d("checkState", ((EasySettingItem)v).getCheckBox().isChecked()+"");
            }
        });

    }
}
