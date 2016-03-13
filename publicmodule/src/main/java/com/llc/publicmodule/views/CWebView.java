package com.llc.publicmodule.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by Administrator on 2015/8/4.
 */
public class CWebView extends WebView {
    OnWebViewScrollToTopListener onWebViewScrollToTopListener;
    OnWebViewScrollToBottomListener onWebViewScrollToBottomListener;
    public CWebView(Context context) {
        super(context);
    }
    public CWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //webview总高度
        float webviewcontentHeight = getContentHeight() * getScale();
        //webview 现在的高度
        float webviewcurrentHeight = getHeight() + getScrollY();
//        Log.i("main", (webviewcontentHeight - webviewcurrentHeight) + "");
        if (webviewcontentHeight - webviewcurrentHeight < 10 && onWebViewScrollToBottomListener != null) {
            onWebViewScrollToBottomListener.onWebViewScrollToBottom(this);
        } else if (getScrollY() == 0) {
            if (onWebViewScrollToTopListener != null) {
                onWebViewScrollToTopListener.onWebViewScrollToTop(this);
            }
        }


    }

    public void setOnWebViewScrollToTopListener(OnWebViewScrollToTopListener onWebViewScrollToTopListener) {
        this.onWebViewScrollToTopListener = onWebViewScrollToTopListener;

    }

    public void setOnWebViewScrollToBottomListener(OnWebViewScrollToBottomListener onWebViewScrollToBottomListener) {
        this.onWebViewScrollToBottomListener = onWebViewScrollToBottomListener;
    }

    public interface OnWebViewScrollToBottomListener {
        public void onWebViewScrollToBottom(WebView webView);
    }

    public interface OnWebViewScrollToTopListener {
        public void onWebViewScrollToTop(WebView webView);
    }

}
