package com.jama.kenyablooddonationsystem.ui.qrCodeScanner

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import com.jama.kenyablooddonationsystem.R

class QRCodeScannerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scanner)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        window.setLayout(((width * .8).toInt()), ((height * .5).toInt()))

        val params: WindowManager.LayoutParams = window.attributes
        params.apply {
            gravity = Gravity.CENTER
            x = 0
            y = -20
        }

        window.attributes = params

    }
}
