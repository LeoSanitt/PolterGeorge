package com.example.myapplication.clubhouse
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.example.myapplication.Firebase.Firestore
import com.example.myapplication.R
import com.example.myapplication.models.User
import com.example.myapplication.utils.Variables
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private fun validateDetails(): Boolean {
        return when {
            TextUtils.isEmpty(etPhoneNumber.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(applicationContext, R.string.no_phone, LENGTH_LONG).show()
                false

            }

            else -> true
        }
    }
    private fun registerUser() {
        if (validateDetails()) {
            auth = Firebase.auth
            val phone = etPhoneNumber.text.toString().trim { it <= ' '}
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phone)       // Phone number to verify
                .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS) // Timeout and unit
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        auth.signInWithCredential(credential)
                            .addOnCompleteListener { task: Task<AuthResult> ->
                                if (task.isSuccessful) {
                                    val firebaseUser: FirebaseUser = task.result!!.user!!
                                    val user = User(userId = firebaseUser.uid)
                                    Firestore().registerUser(user)
                                    intent  = Intent(applicationContext, AdditionalInfo::class.java)
                                    startActivityForResult(intent,0)
                                }
                            }
                    }
                    override fun onVerificationFailed(e: FirebaseException) {
                        Toast.makeText(applicationContext, R.string.logging_in_error, LENGTH_LONG).show()
                    }

                    override fun onCodeSent(verificationId: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                        val resendToken = forceResendingToken
                        var intent = Intent(applicationContext, VerifyActivity::class.java)
                        intent.putExtra("verificationId",verificationId)
                        finish()
                        startActivityForResult(intent,0)

                    }
                })
                .setActivity(this)                 // Activity (for callback binding)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firestore().getAllClubs()
        setContentView(R.layout.activity_login)
        btnLogin.setOnClickListener{if (validateDetails()) registerUser()}

    }
}
