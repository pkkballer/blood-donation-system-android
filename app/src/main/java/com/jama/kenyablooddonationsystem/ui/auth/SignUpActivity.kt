package com.jama.kenyablooddonationsystem.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.jama.kenyablooddonationsystem.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    val bloodTypes : Array<String> = arrayOf("A+", "A-", "B+", "B-")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val adapter : ArrayAdapter<String> = ArrayAdapter(
            this,
            R.layout.dropdown_menu_blood_type,
            bloodTypes
        )
        textViewBloodType.setAdapter(adapter)

        buttonBack.setOnClickListener {
            finish()
        }
        
    }
}
