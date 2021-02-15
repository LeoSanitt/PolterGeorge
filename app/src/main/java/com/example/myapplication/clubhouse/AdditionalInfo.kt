package com.example.myapplication.clubhouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.Firebase.Firestore
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.activity_additional_info.*

class AdditionalInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firestore().getAllClubs()
        setContentView(R.layout.activity_additional_info)
        if (Variables.userName!=""){
            etName.setText(Variables.userName)
        }
        if (Variables.userClub!=""){
            etClub.setText(Variables.userClub)
        }
        if (Variables.userAge!=""){
            etAge.setText(Variables.userAge)
        }
        btnDetail.setOnClickListener{
            Log.d("do the DEETS work",detailsWork().toString())
            Log.d("DEETS", Variables.allClubs.toString())
            if (detailsWork()){
                val age=findViewById<EditText>(R.id.etAge).text.toString().trim { it <= ' '}
                val club=findViewById<EditText>(R.id.etClub).text.toString().trim { it <= ' '}
                val name=findViewById<EditText>(R.id.etName).text.toString().trim { it <= ' '}
                Firestore().addInfo(age,club, name)
                finish()
            }
        }
    }
    private fun detailsWork(): Boolean{
        return when{
            TextUtils.isEmpty(etClub.text.toString().trim { it <= ' '}) -> {
                Toast.makeText(applicationContext, "Please enter your club", Toast.LENGTH_SHORT).show()
                Log.d("DEETS", "no club")
                false
            }
            TextUtils.isEmpty(etName.text.toString().trim { it <= ' '}) -> {
                Toast.makeText(applicationContext, "Please enter your name", Toast.LENGTH_SHORT).show()
                Log.d("DEETS", "no name")

                false
            }
            etClub.text.toString().trim { it <= ' '} !in Variables.allClubs -> {
                Toast.makeText(applicationContext, "This club does not exist - check your spelling", Toast.LENGTH_SHORT).show()
                Log.d("DEETS", "not in list")
                Log.d("DEETS", Variables.allClubs.toString())
                false
            }
            else -> {true}
        }
    }
}