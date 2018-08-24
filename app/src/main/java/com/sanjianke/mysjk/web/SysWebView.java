package com.sanjianke.mysjk.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dd.mylibrary.utils.web.WebViewInitUtils;
import com.dd.mylibrary.wedget.dialog.HttpDialog;
import com.sanjianke.mysjk.R;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by hgh on 2017/4/10.
 */

public class SysWebView extends FrameLayout {
    private Context context;

    public SysWebView(Context context) {
        super(context);
        init(context);
    }

    String murl;

    public void loadUrl(String url) {
        murl = url;
        webView.loadUrl(url);
    }

    public void reload() {
        loadUrl(murl);
    }

    public WebView getWebView() {
        return webView;
    }

    private boolean isShowD = true;

    public void setShowD(boolean showD) {
        isShowD = showD;
    }

    public SysWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected HttpDialog dialog;

    public HttpDialog getDialog() {
        return dialog;
    }

    WebView webView;
    View tv;

    private void init(final Context context) {
        this.context = context;
        webView = new WebView(context);
        webView.setBackgroundColor(0x00000000);
        webView.clearCache(true);
        dialog = new HttpDialog(context);
        dialog.setMessage("加载中...");
        WebViewInitUtils.WebSettingInit(webView);
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if( url.startsWith("http:") || url.startsWith("https:") ) {
//                    return false;
//                }
//
//                // Otherwise allow the OS to handle things like tel, mailto, etc.
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                context.startActivity( intent );
//                return true;
//            }
//        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

//                String js = ADFilterTool.getClearAdDivJs(context);
                try {
                    webView.setBackgroundColor(Color.parseColor("#ffffff"));
                    if (isShowD) {
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                }
                isShowD = true;
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                        && view.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println(url + "=======");
                if (!TextUtils.isEmpty(url)) {

                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                try {
                    if (isShowD)
                        dialog.show();
                } catch (Exception e) {
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                try {
                    if (isShowD)
                        dialog.dismiss();
                } catch (Exception e) {
                    {
                    }
                }

                isShowD = true;
//                tv.setVisibility(View.VISIBLE);
//                webView.setVisibility(View.GONE);
//                webView.loadUrl("file:///android_asset/errorweb.html");

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
//        tv = View.inflate(context, R.layout.empty_view, null);
//        TextView tv1 = (TextView) tv.findViewById(R.id.tv_empty);
//        tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//        tv1.setText("无法打开网址点击刷新");
//        tv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv.setVisibility(View.GONE);
//                webView.setVisibility(View.VISIBLE);
//                reload();
//            }
//        });
//        this.addView(tv);
        this.addView(webView);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        destroy();
    }

    public void destroy() {
        webView.stopLoading();
        webView.clearHistory();
        webView.clearView();
        clearWebViewCache();
        if (webView != null)
            webView.destroy();
    }

    public void clearWebViewCache() {
        // 清除cookie即可彻底清除缓存
        CookieSyncManager.createInstance(getContext());
        CookieManager.getInstance().removeAllCookie();
    }

    private static Map<String, Object> parseUrl(String url) {
        HashMap<String, Object> KVHashMap = new HashMap<>();
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String aKeyValue : keyValue) {
            String[] split = aKeyValue.split("=");
            KVHashMap.put(split[0], split[1]);
        }
        return KVHashMap;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }

}
