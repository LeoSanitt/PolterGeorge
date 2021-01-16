package com.example.myapplication.Tapptrial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var playeradapter: playeradapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPlayerItems.adapter = playeradapter
        rvPlayerItems.layoutManager = LinearLayoutManager(this)

        btnDropdown.setOnClickListener{
            playeradapter.tempAddPlayer()
        }
    }
}