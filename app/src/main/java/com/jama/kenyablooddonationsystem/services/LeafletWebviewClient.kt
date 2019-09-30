package com.jama.kenyablooddonationsystem.services

import android.webkit.WebView
import android.webkit.WebViewClient

class LeafletWebviewClient(private val webView: WebView, val lat: String, val lng: String): WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        webView.loadUrl("javascript:initializeMap($lat, $lng)")
    }
}