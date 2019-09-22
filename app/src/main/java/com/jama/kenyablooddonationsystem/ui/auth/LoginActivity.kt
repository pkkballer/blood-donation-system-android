package com.jama.kenyablooddonationsystem.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        buttonLogin.setOnClickListener {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
            loginModel.signIn("email", "pas")
        }

    }
}
