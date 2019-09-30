package com.jama.kenyablooddonationsystem.ui.qrCodeScanner

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.models.RequestModel
import com.jama.kenyablooddonationsystem.services.QrCodeAnalyzer
import com.jama.kenyablooddonationsystem.viewModels.request.RequestsViewModel
import kotlinx.android.synthetic.main.activity_qrcode_scanner.*

class QRCodeScannerActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_CAMERA = 1
    private lateinit var requestModel: RequestModel
    private lateinit var requestsViewModel: RequestsViewModel
    private var closeQrCode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scanner)

        requestModel = intent.getSerializableExtra("request") as RequestModel

        requestsViewModel = run {
            ViewModelProviders.of(this)[RequestsViewModel::class.java]
        }

        checkIfHasPermissions()

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

        requestsViewModel.closeQrCode.observe(this, Observer {
            if (it) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        })

    }

    private fun checkIfHasPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                PERMISSIONS_REQUEST_CAMERA)
        } else {
            startCamera()
        }
    }

    private fun startCamera() {
        val previewConfig = PreviewConfig.Builder().run {
            setLensFacing(CameraX.LensFacing.BACK)
            build()
        }
        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {
            textureView.surfaceTexture = it.surfaceTexture
        }

        val imageAnalysisConfig = ImageAnalysisConfig.Builder().build()
        val imageAnalysis = ImageAnalysis(imageAnalysisConfig)

        val qrCodeAnalyzer = QrCodeAnalyzer {barCodes ->
            barCodes.forEach {
                if (!closeQrCode) {
                    if (requestModel.uid == it.rawValue) {
                        println("qrcode passed")
                        requestsViewModel.registerDonation(requestModel)
                        closeQrCode = true
                    } else {
                        println("qrcode failed")
                    }
                }
            }
        }

        imageAnalysis.analyzer = qrCodeAnalyzer

        CameraX.bindToLifecycle(this as LifecycleOwner, preview, imageAnalysis)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    startCamera()
                } else {
                    checkIfHasPermissions()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
