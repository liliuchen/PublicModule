package com.llc.publicmodule.utils;

import android.webkit.WebView;

/**
 * Created by Administrator on 2015/8/15.
 */
public class CHtmlLanguageMode {
    /**
     * @param webView
     * @param ui_id   控件的id
     * @param value   设置的值
     */
    public static void executeSetValueJavaScriptBaseId(WebView webView, String ui_id, String value) {
        String javascriptRoot = "javascript:document.getElementById('" + ui_id + "').value=" + value;
        webView.loadUrl(javascriptRoot);

    }

    /**
     * @param webView
     * @param methodName 要执行的方法名 要加括号
     */
    public static void executMethodJavaScriptBaseMethedName(WebView webView, String methodName) {
        String javascriptRoot = "javascript:" + methodName;
        webView.loadUrl(javascriptRoot);

    }

    /**
     * @param webView
     * @param ui_id
     */
    public static void executMethodJavaScriptBaseId(WebView webView, String ui_id) {
        String javascriptRoot = "javascript:document.getElementById('" + ui_id + "').onclick()";
        webView.loadUrl(javascriptRoot);

    }

    /**
     * @param webView
     * @param uiclass 控件的样式
     * @param value   设置的值
     */
    public static void executeSetValuejQueryBaseClass(WebView webView, String uiclass, String value) {
        String javascriptRoot = "javascript:$('." + uiclass + "').val('" + value + "')";
        webView.loadUrl(javascriptRoot);

    }

    public static void executeSetValuejQueryBaseId(WebView webView, String ui_id, String value) {
        String javascriptRoot = "javascript:$('#" + ui_id + "').val('" + value + "')";
        webView.loadUrl(javascriptRoot);

    }

    /**
     * @param webView   基于id
     * @param button_id 控件的id
     */
    public static void executMethodjQueryBaseId(WebView webView, String button_id) {
        String javascriptRoot = "javascript:$('#" + button_id + "').click()";
        webView.loadUrl(javascriptRoot);

    }

    /**
     * @param webView   基于样式
     * @param button_id
     */
    public static void executMethodjQueryBaseClass(WebView webView, String button_id) {
        String javascriptRoot = "javascript:$('." + button_id + "').click()";
        webView.loadUrl(javascriptRoot);

    }

    /**
     * 执行一个方法
     *
     * @param webView
     * @param str
     */
    public static void executMethod(WebView webView, String str) {
        String[] param = str.split(",");
        if (str.startsWith("js")) {
            if (param[1].equals("methed")) {
                executMethodJavaScriptBaseMethedName(webView, param[2]);
            } else if (param[1].equals("id")) {
                executMethodJavaScriptBaseId(webView, param[2]);
            }
        } else if (str.startsWith("jq")) {
            if (param[1].equals("class")) {
                executMethodjQueryBaseClass(webView, param[2]);
            } else if (param[1].equals("id")) {
                executMethodjQueryBaseId(webView, param[2]);
            }

        }

    }

    public static void executSetValue(WebView webView, String str, String valueTemp) {
        String[] param = str.split(",");
        if (str.startsWith("js")) {
            if (param[1].equals("id")) {
                executeSetValueJavaScriptBaseId(webView, param[2], valueTemp);
            }
        } else if (str.startsWith("jq")) {
            if (param[1].equals("class")) {
                executeSetValuejQueryBaseClass(webView, param[2], valueTemp);
            } else if (param[1].equals("id")) {
                executeSetValuejQueryBaseId(webView, param[2], valueTemp);
            }
        }

    }


}
