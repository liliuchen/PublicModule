package com.llc.publicmodule.utils;

import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.llc.publicmodule.R;

import java.util.Calendar;

public class CDialogUtils {
    Context context;
    ProgressDialog progressDialog;
    DialogStyle style;
    String text;
    Builder builder;

    public enum DialogStyle {
        DialogDownloading, ProgressDownloading
    }

    Handler handler = null;

    public CDialogUtils(Context context) {
        super();
        this.context = context;
        handler = new Handler();
    }

    public void ShowProgressDialog(String text) {
        this.text = text;
        initProgressDialog();
        progressDialog.setMessage(text);
        progressDialog.show();
    }

    public void DismissProgressDialog() {
        progressDialog.dismiss();
    }

    private void initProgressDialog() {
        // TODO Auto-generated method stub
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            progressDialog.setIndeterminate(false);//进度条是否明确
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(true);
        }
    }

    Dialog dialog = null;

    public void showTipDialog(final int timeOfdismiss, String text) {
        dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        final View layout = View.inflate(context, R.layout.dialog_voucher_result, null);
        ((TextView) layout.findViewById(R.id.dialog_voucher_result_textview)).setText(text);
        dialog.setContentView(layout);
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {

                View imageview = layout.findViewById(R.id.dialog_voucher_result_imageview);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
                alphaAnimation.setDuration(2000);
                imageview.setAnimation(alphaAnimation);
                SystemClock.sleep(timeOfdismiss);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (null != dialog && dialog.isShowing()) dialog.dismiss();
                    }
                });
            }
        }).start();
    }

    public Dialog showBottomDialog(String tilte, String msg, final DialogBtnListener listener) {
        final Dialog dlg = new Dialog(context, R.style.MMTheme_DataSheet);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.dialog_bottom_tip, null);
        final int cFullFillWidth = 10000;
        TextView tvTitle = (TextView) layout.findViewById(R.id.dialog_bottom_tip_tv_title);
        tvTitle.setText(tilte);
        TextView tvmsg = (TextView) layout.findViewById(R.id.dialog_bottom_tip_tv_msg);
        tvmsg.setText(msg);
        Button btnOK, btnCancel;
        btnCancel = (Button) layout.findViewById(R.id.dialog_bottom_tip_btn_cancel);
        btnOK = (Button) layout.findViewById(R.id.dialog_bottom_tip_btn_ok);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onCancelClick(dlg, view);
                }
                dlg.dismiss();
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onOKClick(dlg, view);
                }
                dlg.dismiss();
            }
        });
        TextView tvTop = (TextView) layout.findViewById(R.id.dialog_bottom_tip_tv_cancel_top);
        tvTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
        layout.setMinimumWidth(cFullFillWidth);
        // set a large value put it in bottom
        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(true);
        dlg.setContentView(layout);
        dlg.setCanceledOnTouchOutside(false);
        dlg.show();
        return dlg;
    }

    public interface DialogBtnListener {
        public void onOKClick(Dialog dialog, View view);

        public void onCancelClick(Dialog dialog, View view);
    }

    public Dialog showBottomDialog(View v) {
        final Dialog dlg = new Dialog(context, R.style.MMTheme_DataSheet);
        final int cFullFillWidth = 10000;
        v.setMinimumWidth(cFullFillWidth);
        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(true);
        dlg.setContentView(v);
        dlg.show();
        return dlg;
    }

    public interface DateSelectedListener {
        public void onSelected(int[] startDateArray, int[] endDateArray);
    }

    int[] startDateArray, endDateArray;

    public void showSelectStartEndDate(Context context, String title1, String title2, final DateSelectedListener listener) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog2 = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                endDateArray = new int[]{year, monthOfYear + 1, dayOfMonth};
                listener.onSelected(startDateArray, endDateArray);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog2.setTitle(title2);
        DatePickerDialog datePickerDialog1 = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDateArray = new int[]{year, monthOfYear + 1, dayOfMonth};
                datePickerDialog2.show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog1.setTitle(title1);
        datePickerDialog1.show();
    }


}
