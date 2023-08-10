package com.stevepopovich.shortsfreeyt.android

import android.app.PictureInPictureParams
import android.os.Bundle
import android.view.Window
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.stevepopovich.shortsfreeyt.URL
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var webView: WebView
    private var userAgent: String? = null

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onPause() {
        super.onPause()
        enterPictureInPictureMode(PictureInPictureParams.Builder().build())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_NO_TITLE)

        webView = CustomWebView(this).apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    view?.evaluateJavascript(
                        "var elements = this.document.getElementsByClassName(\"pivot-shorts\");while(elements.length > 0) elements[0].parentNode.removeChild(elements[0])"
                    ) {}

                    view?.evaluateJavascript(
                        "var elements2 = document.getElementsByTagName(\"ytm-reel-shelf-renderer\");while (elements2[0]) elements2[0].parentNode.removeChild(elements2[0]);"
                    ) {}
                    GlobalScope.launch {
                        while (true) {
                            delay(100)
                            runOnUiThread {
                                view?.evaluateJavascript(
                                    "elements = this.document.getElementsByClassName(\"pivot-shorts\");while(elements.length > 0) elements[0].parentNode.removeChild(elements[0])"
                                ) {}

                                view?.evaluateJavascript(
                                    "elements2 = document.getElementsByTagName(\"ytm-reel-shelf-renderer\");while (elements2[0]) elements2[0].parentNode.removeChild(elements2[0]);"
                                ) {}
                            }
                        }
                    }
                }
            }

            // Allows logging into YouTube via popup
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.setSupportMultipleWindows(true)

            // Needed to load youtube fully
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            // Needed to keep user logged into YouTube
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

            loadUrl(URL.YOUTUBE)
        }

        // Keeps logged into YouTube
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)

        webView.webChromeClient = CustomChromeWebClient(
            webView = webView,
            activity = this
        )

        this.setContentView(webView)
    }
}