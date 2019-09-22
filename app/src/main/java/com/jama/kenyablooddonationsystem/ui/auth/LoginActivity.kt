package com.jama.kenyablooddonationsystem.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.ui.home.HomeActivity
import com.jama.kenyablooddonationsystem.viewModels.auth.AuthViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val authViewModel = ViewModelProviders.of(this)[AuthViewModel::class.java]

        authViewModel.checkIfUserExists()

        linearLayoutCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        authViewModel.errorMap.observe(this, Observer {
            textViewErrorMessage.text = it["message"] as CharSequence?
            textViewErrorMessage.visibility = if (it["isError"] as Boolean) View.VISIBLE else View.GONE
        })

        authViewModel.showProgress.observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
            buttonLogin.isEnabled = !it
        })

        authViewModel.loginUser.observe(this, Observer {
            if (it) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        buttonLogin.setOnClickListener {
            authViewModel.signIn(editTextEmail.text.toString(), editTextPassword.text.toString())
        }

    }
}
