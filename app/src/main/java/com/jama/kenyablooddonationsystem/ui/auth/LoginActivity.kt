package com.jama.kenyablooddonationsystem.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jama.kenyablooddonationsystem.R
import com.jama.kenyablooddonationsystem.ui.home.HomeActivity
import com.jama.kenyablooddonationsystem.viewModels.auth.LoginViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val loginModel = ViewModelProviders.of(this)[LoginViewModel::class.java]

        linearLayoutCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginModel.errorMap.observe(this, Observer {
            textViewErrorMessage.text = it["message"] as CharSequence?
            textViewErrorMessage.visibility = if (it["isError"] as Boolean) View.VISIBLE else View.GONE
        })

        loginModel.showProgress.observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
            buttonLogin.isEnabled = !it
        })

        loginModel.loginUser.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        })

        buttonLogin.setOnClickListener {
            loginModel.signIn(editTextEmail.text.toString(), editTextPassword.text.toString())
        }

    }
}
