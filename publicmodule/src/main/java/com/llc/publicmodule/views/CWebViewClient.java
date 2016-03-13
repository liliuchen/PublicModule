package com.llc.publicmodule.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.llc.publicmodule.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/8/6.
 */
public class CWebViewClient extends WebViewClient {
    Context context;
    LinearLayout layoutParent;
    LinearLayout errorLinearLayout;
    Button mbtnBack, mbtnRefresh;
    ProgressDialog progressDialog;
    WebView mWebView;
    Handler mHandler;

    public CWebViewClient(Context context) {
        this.context = context;
        errorLinearLayout = (LinearLayout) View.inflate(context, R.layout.webview_loading_error, null);
        progressDialog = new ProgressDialog(context);
        initView(errorLinearLayout);
        setLinstener();
        progressDialog.setMessage("正在飞奔加载中...");
        mHandler = new Handler();
    }

    private void setLinstener() {
        mbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    mWebView.setVisibility(View.VISIBLE);
                    layoutParent.removeView(errorLinearLayout);
                }
            }
        });
        mbtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
                mWebView.setVisibility(View.VISIBLE);
                layoutParent.removeView(errorLinearLayout);

            }
        });
    }

    Timer timer = null;

    private void initView(View layout) {
        mbtnBack = (Button) layout.findViewById(R.id.webview_loading_error_btn_back);
        mbtnRefresh = (Button) layout.findViewById(R.id.webview_loading_error_btn_refresh);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        progressDialog.show();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if (timer!=null){
                            timer.cancel();
                            timer=null;
                        }
                    }
                });

            }
        }, 3000);

    }
    boolean isFirstAddErrorLayout=true;
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//        super.onReceivedError(view, errorCode, description, failingUrl);
        if(!isFirstAddErrorLayout) {
            layoutParent = (LinearLayout) view.getParent();
            layoutParent.addView(errorLinearLayout);
            mWebView = view;
            view.setVisibility(View.GONE);
            isFirstAddErrorLayout=false;
        }else{
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//        super.onReceivedSslError(view, handler, error);
        if(!isFirstAddErrorLayout) {
            layoutParent = (LinearLayout) view.getParent();
            layoutParent.addView(errorLinearLayout);
            mWebView = view;
            view.setVisibility(View.GONE);
            isFirstAddErrorLayout=false;
        }else{
            view.setVisibility(View.GONE);
        }
    }
    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
//        super.onReceivedClientCertRequest(view, request);
        layoutParent = (LinearLayout) view.getParent();
        layoutParent.addView(errorLinearLayout);
        mWebView = view;
        view.setVisibility(View.GONE);
    }
}
