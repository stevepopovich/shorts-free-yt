package com.stevepopovich.shortsfreeyt.android

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat


class CustomChromeWebClient(
    private val webView: WebView,
    private val activity: MainActivity
) : WebChromeClient() {

    private var fullscreen: View? = null
    private val controller = WindowInsetsControllerCompat(
        activity.window,
        activity.window.decorView
    )

    override fun onHideCustomView() {
        fullscreen?.visibility = View.GONE
        webView.visibility = View.VISIBLE
        controller.show(WindowInsetsCompat.Type.systemBars())
    }

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

        controller.hide(WindowInsetsCompat.Type.systemBars())
    }
}