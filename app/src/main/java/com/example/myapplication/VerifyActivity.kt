package com.example.myapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.example.myapplication.Firebase.Firestore
import com.example.myapplication.Tapptrial.MainActivity
import com.example.myapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class VerifyActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    fun userRegistrationSuccess(){
        Toast.makeText(applicationContext, resources.getString(R.string.register_success), LENGTH_LONG).show()

    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential,club: String,name: String, phone: String){
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val user = User(firebaseUser.uid, name, club, phone)
                    Firestore().registerUser(user, club)
                    intent  = Intent(applicationContext, MainActivity::class.java)
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
        val phone = intent.getStringExtra("phone")
        verify.setOnClickListener{
            var otp=otpGiven.text.toString().trim()
            if (otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(storedId.toString(), otp)
                signInWithPhoneAuthCredential(credential, club.toString(), name.toString(), phone.toString())
            }else{ }
        }

    }
}