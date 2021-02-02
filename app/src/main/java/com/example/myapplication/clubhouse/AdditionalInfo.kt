package com.example.myapplication.clubhouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.myapplication.R
import com.example.myapplication.Firebase.Firestore
import kotlinx.android.synthetic.main.activity_additional_info.*

class AdditionalInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_info)
        val age=findViewById<EditText>(R.id.etAge).toString()
        val gender=findViewById<EditText>(R.id.etGender).toString()
        val standard = findViewById<EditText>(R.id.etLevel).toString()
        btnDetail.setOnClickListener{
            Firestore().addInfo(age,gender,standard)
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}