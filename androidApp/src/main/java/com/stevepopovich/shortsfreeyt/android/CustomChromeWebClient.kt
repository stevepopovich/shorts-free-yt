package com.stevepopovich.shortsfreeyt.android

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout


class CustomChromeWebClient(
    private val webView: WebView,
    private val activity: MainActivity
) : WebChromeClient() {

    private var fullscreen: View? = null

    override fun onHideCustomView() {
        fullscreen?.visibility = View.GONE
        webView.visibility = View.VISIBLE
    }

    over

    override fun onShowCustomView(view: View, callback: CustomViewCallback?) {
        webView.visibility = View.GONE
        if (fullscreen != null) {
            (activity.window.decorView as FrameLayout).removeView(fullscreen)
        }
        fullscreen = view
        (activity.window.decorView as FrameLayout).addView(
            fullscreen,
            FrameLayout.LayoutParams(-1, -1)
        )
        fullscreen?.visibility = View.VISIBLE
    }
}