package com.yhyy.btmactiondialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.yhyy.btmactiondialog.R;
import com.yhyy.btmactiondialog.bean.SheetItem;
import com.yhyy.btmactiondialog.inter.OnSheetItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by IceWolf on 2019/9/16.
 */
public class BtmActionDialog {
    private Context context;
    private Dialog dialog;
    private TextView text_title;
    private TextView text_cancel;
    private LinearLayout layout_content;
    private LinearLayout layout_title;
    private View view_line;
    private ScrollView scroll_content;
    private boolean showTitle = false;
    private List<SheetItem> sheetItemList;
    DisplayMetrics dm = new DisplayMetrics();

    public BtmActionDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
    }

    public BtmActionDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.btmactiondialog, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(dm.widthPixels);

        // 获取自定义Dialog布局中的控件
        scroll_content = (ScrollView) view.findViewById(R.id.sl_content);
        layout_content = (LinearLayout) view.findViewById(R.id.ll_content);
        layout_title = (LinearLayout) view.findViewById(R.id.ll_title);
        text_title = (TextView) view.findViewById(R.id.tv_title);
        view_line = (View) view.findViewById(R.id.view_line);
        text_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        text_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.CustomDialogG);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    public BtmActionDialog setTitle(String title) {
        showTitle = true;
        layout_title.setVisibility(View.VISIBLE);
        text_title.setText(title);
        return this;
    }

    public BtmActionDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public BtmActionDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * @param name     条目名称
     * @param color    条目字体颜色，设置null则默认蓝色
     * @param listener
     * @return
     */
    public BtmActionDialog addSheetItem(Drawable drawableLeft, String name, Integer color,
                                        OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.add(new SheetItem(drawableLeft, name, color, listener));
        return this;
    }

    /**
     * @param name     条目名称
     * @param color    条目字体颜色，设置null则默认蓝色
     * @param listener
     * @return
     */
    public BtmActionDialog addSheetItem(String name, Integer color,
                                        OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.add(new SheetItem(name, color, listener));
        return this;
    }

    /**
     * 设置多个条目布局
     */
    private void setSheetItems() {
        // 循环添加条目
        for (int i = 0; i < sheetItemList.size(); i++) {
            View view = View.inflate(context, R.layout.item_dialog_sheet, null);
            LinearLayout layout_item = (LinearLayout) view.findViewById(R.id.ll_item_content);
            TextView textView = (TextView) view.findViewById(R.id.tv_item_content);
            View view_item_line = (View) view.findViewById(R.id.view_item_line);
            int index = i;
            SheetItem sheetItem = sheetItemList.get(i);
            Drawable drawable = sheetItem.getDrawableLeft();
            String name = sheetItem.getName();
            Integer color = sheetItem.getColor();
            OnSheetItemClickListener listener = sheetItem.getOnSheetItemClickListener();
            textView.setText(name);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                textView.setCompoundDrawables(drawable, null, null, null);
            }
            // 字体颜色
            if (color == null) {
                textView.setTextColor(ContextCompat.getColor(context, R.color.blue));
            } else {
                textView.setTextColor(ContextCompat.getColor(context, sheetItem.getColor()));
            }

            if (showTitle) {
                layout_title.setBackgroundResource(R.drawable.round_wa_16);
                view_line.setVisibility(View.VISIBLE);
                if (i < sheetItemList.size() - 1) {
                    view_item_line.setVisibility(View.VISIBLE);
                    layout_item.setBackgroundResource(R.drawable.rec_select);
                } else {
                    view_item_line.setVisibility(View.GONE);
                    layout_item.setBackgroundResource(R.drawable.round_select_b);
                }
            } else {
                if (i == 0) {
                    view_item_line.setVisibility(View.VISIBLE);
                    layout_item.setBackgroundResource(R.drawable.round_select_a);
                } else if (i < sheetItemList.size() - 1) {
                    view_item_line.setVisibility(View.VISIBLE);
                    layout_item.setBackgroundResource(R.drawable.rec_select);
                } else {
                    view_item_line.setVisibility(View.GONE);
                    layout_item.setBackgroundResource(R.drawable.round_select_b);
                }
            }

            // 点击事件
            layout_item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index);
                    dialog.dismiss();
                }
            });
            layout_content.addView(layout_item);
        }
        //measure方法的参数值都设为0即可
        layout_content.measure(0, 0);
        int linearHeight = layout_content.getMeasuredHeight();
        int windowHeight = dm.heightPixels / 3;
        if (linearHeight > windowHeight) {
            // 防止条目过多的时候控制高度
            LayoutParams params = (LayoutParams) scroll_content
                    .getLayoutParams();
            params.height = dm.heightPixels / 3;
            scroll_content.setLayoutParams(params);
        }
    }

    /**
     * 设置单个条目布局
     */
    private void setSheetItem() {
        View view = View.inflate(context, R.layout.item_dialog_sheet, null);
        LinearLayout layout_item = (LinearLayout) view.findViewById(R.id.ll_item_content);
        TextView textView = (TextView) view.findViewById(R.id.tv_item_content);
        View view_item_line = (View) view.findViewById(R.id.view_item_line);
        if (sheetItemList.size() == 0) {
            if (showTitle) {
                layout_title.setBackgroundResource(R.drawable.round_wc_16);
                view_line.setVisibility(View.GONE);
                view_item_line.setVisibility(View.GONE);
                layout_item.setBackgroundResource(R.drawable.round_select_b);
            } else {
                view_item_line.setVisibility(View.GONE);
                layout_item.setBackgroundResource(R.drawable.round_select_c);
            }
        } else {
            SheetItem sheetItem = sheetItemList.get(0);
            Drawable drawable = sheetItem.getDrawableLeft();
            String name = sheetItem.getName();
            Integer color = sheetItem.getColor();
            OnSheetItemClickListener listener = sheetItem.getOnSheetItemClickListener();
            textView.setText(name);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                textView.setCompoundDrawables(drawable, null, null, null);
            }
            // 字体颜色
            if (color == null) {
                textView.setTextColor(ContextCompat.getColor(context, R.color.blue));
            } else {
                textView.setTextColor(ContextCompat.getColor(context, sheetItem.getColor()));
            }

            if (showTitle) {
                layout_title.setBackgroundResource(R.drawable.round_wa_16);
                view_line.setVisibility(View.VISIBLE);
                view_item_line.setVisibility(View.GONE);
                layout_item.setBackgroundResource(R.drawable.round_select_b);
            } else {
                view_item_line.setVisibility(View.GONE);
                layout_item.setBackgroundResource(R.drawable.round_select_c);
            }

            // 点击事件
            layout_item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(0);
                    dialog.dismiss();
                }
            });
            layout_content.addView(layout_item);
        }
    }

    public void show() {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        int size = sheetItemList.size();
        if (size < 2) {
            setSheetItem();
        } else {
            setSheetItems();
        }
        dialog.show();
    }
}
