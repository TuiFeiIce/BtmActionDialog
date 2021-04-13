package com.yhyy.btmactiondialog;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.yhyy.btmactiondialog.inter.OnSheetItemClickListener;
import com.yhyy.btmactiondialog.widget.BtmActionDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onClick() {
        new BtmActionDialog(context)
                .builder()
                .setTitle("标题")
                .addSheetItem(ContextCompat.getDrawable(context, R.mipmap.ic_launcher_round), "选项一", R.color.green,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(Integer integer) {
                                Toast.makeText(context, integer + "", Toast.LENGTH_SHORT).show();
                            }
                        })
                .addSheetItem("选项二", R.color.red,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(Integer integer) {
                                Toast.makeText(context, integer + "", Toast.LENGTH_SHORT).show();
                            }
                        })
                .show();
    }
}