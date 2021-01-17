package com.example.myapplication.Tapptrial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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