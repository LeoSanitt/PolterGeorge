package com.example.myapplication.clubhouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Firebase.Firestore
import com.example.myapplication.R
import com.example.myapplication.utils.Variables
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_info_qreq.*
import kotlinx.android.synthetic.main.player_info_qreq.view.*
import java.util.*

class MainActivity : AppCompatActivity(), playeradapter.OnItemClickListener {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser
        if (user == null) {
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivityForResult(intent, 0)
            finish()
        }
        Firestore().userInfo()
        Variables.namesChanged.observe(this, androidx.lifecycle.Observer{
            setContentView(R.layout.activity_main)
            loadPlayerList()
            //rvPlayerItems.adapter = playeradapter(Variables.allNames, this)
            //rvPlayerItems.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
            //rvPlayerItems.layoutManager = LinearLayoutManager(this@MainActivity)

            //Variables.namesChanged.removeObservers(this)
        })
        Variables.challengedName.observe(this, androidx.lifecycle.Observer {
            loadChallengeFragment()
        })


    }


    override fun onItemClick(position: Int) {
        val playerClicked = Variables.allNames[position]
        val challengeFragment = ChallengeFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flChallenge,  challengeFragment)
            addToBackStack(null)
            commit()
        }
    }
    fun loadChallengeFragment(){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flChallenge,  ChallengeFragment())
            addToBackStack(null)
            commit()
        }
    }
    fun loadPlayerList(){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flPlayerList,  PlayerList())
            commit()
        }
    }
}





