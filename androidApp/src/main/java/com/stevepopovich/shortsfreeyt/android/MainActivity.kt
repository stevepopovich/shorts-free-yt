package com.stevepopovich.shortsfreeyt.android

import android.app.PictureInPictureParams
import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
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

        setContent {
            MyApplicationTheme {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = {
                        webView = MediaWebView(this).apply {
                            webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    GlobalScope.launch {
                                        delay(1000)
                                        runOnUiThread {
                                            view?.evaluateJavascript(
                                                "var elements = this.document.getElementsByClassName(\"pivot-shorts\");while(elements.length > 0) elements[0].parentNode.removeChild(elements[0])"
                                            ) {}

                                            view?.evaluateJavascript(
                                                "var elements2 = document.getElementsByTagName(\"ytm-reel-shelf-renderer\");while (elements2[0]) elements2[0].parentNode.removeChild(elements2[0]);"
                                            ) {}
                                        }

                                        while (true) {
                                            delay(300)
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

                            // Set User Agent
                            userAgent = System.getProperty("http.agent")
                            settings.userAgentString = userAgent + "ShortsFreeYT"

                            settings.javaScriptCanOpenWindowsAutomatically = true
                            settings.setSupportMultipleWindows(true)

                            // WebView Tweaks
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

                            loadUrl(URL.YOUTUBE)
                        }

                        // Enable Cookies
                        CookieManager.getInstance().setAcceptCookie(true)
                        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)

                        webView
                    }
                )
            }
        }
    }
}