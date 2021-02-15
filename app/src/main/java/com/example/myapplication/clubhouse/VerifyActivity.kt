package com.example.myapplication.clubhouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.myapplication.Firebase.Firestore
import com.example.myapplication.R
import com.example.myapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class VerifyActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential){
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val user = User(userId = firebaseUser.uid)
                        Firestore().registerUser(user)
                        finish()
                        intent  = Intent(applicationContext, AdditionalInfo::class.java)
                        startActivityForResult(intent, 0)
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
        verify.setOnClickListener{
            var otp=otpGiven.text.toString().trim()
            if (otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(storedId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{ }
        }

    }
}