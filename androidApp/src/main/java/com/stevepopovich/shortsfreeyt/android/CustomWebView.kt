package com.stevepopovich.shortsfreeyt.android

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView


class CustomWebView : WebView {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    )

    // Allows playing with the screen off
    override fun onWindowVisibilityChanged(visibility: Int) {
        if (visibility != GONE) super.onWindowVisibilityChanged(VISIBLE)
    }
}