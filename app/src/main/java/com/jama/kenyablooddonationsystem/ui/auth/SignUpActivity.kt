package com.jama.kenyablooddonationsystem.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.models.SignUpModel
import com.jama.kenyablooddonationsystem.ui.home.HomeActivity
import com.jama.kenyablooddonationsystem.viewModels.auth.AuthViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.progressBar
import kotlinx.android.synthetic.main.activity_sign_up.textViewErrorMessage

class SignUpActivity : AppCompatActivity() {

    private val bloodTypes = arrayOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
    private val gender = arrayOf("Male", "Female")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val authViewModel = ViewModelProviders.of(this)[AuthViewModel::class.java]

        authViewModel.checkIfUserExists()

        val bloodTypeAdapter : ArrayAdapter<String> = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            bloodTypes
        )
        editTextBloodType.setAdapter(bloodTypeAdapter)

        val genderAdapter : ArrayAdapter<String> = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            gender
        )
        editTextGender.setAdapter(genderAdapter)

        authViewModel.errorMap.observe(this, Observer {
            textViewErrorMessage.text = it["message"] as CharSequence?
            textViewErrorMessage.visibility = if (it["isError"] as Boolean) View.VISIBLE else View.GONE
        })

        authViewModel.showProgress.observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
            buttonSignUp.isEnabled = !it
            scrollView.fullScroll(View.FOCUS_DOWN)
        })

        authViewModel.loginUser.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        })

        buttonSignUp.setOnClickListener {
            authViewModel.signUp(SignUpModel(
                editTextFullNames.text.toString(),
                editTextEmail.text.toString(),
                editTextPassword.text.toString(),
                editTextNationalID.text.toString(),
                editTextPhone.text.toString(),
                editTextGender.text.toString(),
                editTextBloodType.text.toString()
            ))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
