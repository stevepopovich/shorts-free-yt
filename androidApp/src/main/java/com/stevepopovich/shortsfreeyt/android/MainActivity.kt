package com.stevepopovich.shortsfreeyt.android

import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.stevepopovich.shortsfreeyt.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    private lateinit var webView: WebView

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = {
                        webView = WebView(this).apply {
                            webViewClient =  object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    view?.evaluateJavascript(
                                        "var elements = this.document.getElementsByClassName(\"pivot-shorts\");while(elements.length > 0) elements[0].parentNode.removeChild(elements[0])"
                                    ) {}

                                    view?.evaluateJavascript(
                                        "var elements2 = document.getElementsByTagName(\"ytm-reel-shelf-renderer\");while (elements2[0]) elements2[0].parentNode.removeChild(elements2[0]);"
                                    ) {}
                                    GlobalScope.launch {
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
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK;
                            loadUrl(URL.YOUTUBE)
                        }
                        webView
                })
            }
        }
    }
}