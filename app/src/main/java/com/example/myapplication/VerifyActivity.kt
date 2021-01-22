package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.myapplication.Tapptrial.LoginActivity
import com.example.myapplication.Tapptrial.MainActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class VerifyActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential,club: String,name: String){
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    firebaseAnalytics.setUserProperty("Club", club)
                    firebaseAnalytics.setUserProperty("Name", name)
                    intent  = Intent(applicationContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
        auth = FirebaseAuth.getInstance()
        val verify = findViewById<Button>(R.id.btnVerify)
        val otpGiven=findViewById<EditText>(R.id.etCode)
        val storedId = intent.getStringExtra("verificationId")
        val club = intent.getStringExtra("club")
        val name = intent.getStringExtra("name")
        verify.setOnClickListener{
            var otp=otpGiven.text.toString().trim()
            if (otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(storedId.toString(), otp)
                signInWithPhoneAuthCredential(credential, club.toString(), name.toString())
            }else{ }
        }

    }
}