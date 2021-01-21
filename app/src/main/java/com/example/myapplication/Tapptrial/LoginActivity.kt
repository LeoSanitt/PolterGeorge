package com.example.myapplication.Tapptrial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.example.myapplication.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private fun validateDetails(): Boolean {
        return when {
            TextUtils.isEmpty(etClub.text.toString().trim { it <= ' ' }) -> {
                val error = Toast.makeText(applicationContext, R.string.no_club, LENGTH_LONG)
                error.show()
                false
            }
            TextUtils.isEmpty(etName.text.toString().trim { it <= ' ' }) -> {
                val error = Toast.makeText(applicationContext, R.string.no_name, LENGTH_LONG)
                error.show()
                false

            }
            TextUtils.isEmpty(etPhoneNumber.text.toString().trim { it <= ' ' }) -> {
                val error = Toast.makeText(applicationContext, R.string.no_phone, LENGTH_LONG)
                error.show()
                false

            }
            else -> true
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val login = btnLogin.setOnClickListener{validateDetails()}
    }
}