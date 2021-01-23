package com.example.myapplication.Tapptrial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser
        if(user==null){
            intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(R.layout.activity_main)

        var playerList = mutableListOf(Player("Leo", "3000"))
        val adapter = playeradapter(playerList)
        rvPlayerItems.adapter = adapter
        rvPlayerItems.layoutManager = LinearLayoutManager(this)

        btnDropdown.setOnClickListener{
            adapter.tempAddPlayer()
        }
    }
}