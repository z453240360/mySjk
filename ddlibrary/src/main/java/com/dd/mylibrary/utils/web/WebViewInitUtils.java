package com.dd.mylibrary.utils.web;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * WebView Cookie WebSetting
 */
public class WebViewInitUtils {
    /**
     * 添加cookie
     */
    public static void addCookie(Activity activity, String url) {
//        CookieJarImpl coi = (CookieJarImpl) OkGo.getInstance().getOkHttpClient().cookieJar();
//        List<Cookie> persistentCookieStore = (List<Cookie>) coi.getCookieStore().getAllCookie();
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Cookie cookie : persistentCookieStore) {
//            if ("sessionid".equals(cookie.name())) {
//                stringBuilder.append(cookie.name() + "=" + cookie.value()
//                        + ";");
//            }
//        }
//        String cookieStr = "";
//        try {
//            cookieStr = stringBuilder.substring(0, stringBuilder.length() - 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        CookieSyncManager cookieSyncManager = CookieSyncManager
//                .createInstance(activity);
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        cookieManager.setCookie(url, cookieStr);
//        cookieSyncManager.sync();
//        cookieSyncManager.startSync();
    }

    /**
     * 初始化
     *
     * @param web_view
     * @return
     */

    public static WebView WebSettingInit(WebView web_view) {
        baseSet(web_view);

        web_view.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        return web_view;
    }

    public static WebView WebSettingInit1(WebView web_view) {
        baseSet(web_view);

        web_view.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                        && view.canGoBack()) {
                    view.goBack();
                    return true;
                }
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return web_view;
    }

    public static WebView WebSettingInit(WebView web_view, final TextView tvTitle) {
        baseSet(web_view);

        web_view.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                // TODO Auto-generated method stub
                super.onReceivedTitle(view, title);
                tvTitle.setText(title);
            }

        });

        web_view.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                        && view.canGoBack()) {
                    view.goBack();
                    return true;
                }
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return web_view;
    }


    public static WebView WebSettingInit(WebView web_view, final Dialog dialog, final TextView tvTitle) {
        baseSet(web_view);

        web_view.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                // TODO Auto-generated method stub
                super.onReceivedTitle(view, title);
                tvTitle.setText(title);
            }

        });

        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                        && view.canGoBack()) {
                    view.goBack();
                    return true;
                }
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                dialog.show();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                dialog.dismiss();
            }
        });
        return web_view;
    }

    private static void baseSet(WebView web_view) {
        WebSettings ws = web_view.getSettings();
        if (Build.VERSION.SDK_INT >= 21) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        ws.setJavaScriptEnabled(true); // 支持js
        ws.setBuiltInZoomControls(false);// 支持缩放按钮
        ws.setUseWideViewPort(true);// 设置此属性，可任意比例缩放 将图片调整到适合webview的大小
        ws.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        ws.setSupportZoom(false); // 支持缩放

        ws.setAllowFileAccess(true); //设置可以访问文件
        ws.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        ws.setLoadsImagesAutomatically(true); //支持自动加载图片
        ws.setDefaultTextEncodingName("utf-8");//设置编码格式

        // 设置 缓存模式
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE); // 关闭webview中缓存
        // 开启 DOM storage API 功能
        ws.setDomStorageEnabled(true);

        ws.setRenderPriority(RenderPriority.HIGH);
        // 开启 database storage API 功能
        ws.setDatabaseEnabled(false);
    }

    public static WebView WebSettingInit(WebView web_view, final Dialog dialog) {
        baseSet(web_view);

        web_view.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }


        });

        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                        && view.canGoBack()) {
                    view.goBack();
                    return true;
                }
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                dialog.show();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                dialog.dismiss();
            }
        });
        return web_view;
    }
}
