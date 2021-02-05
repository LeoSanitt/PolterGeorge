package com.example.myapplication.clubhouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Firebase.Firestore
import com.example.myapplication.R
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Variables
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_info_qreq.*
import kotlinx.android.synthetic.main.player_info_qreq.view.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    // TODO today
    // implement the ability to send challenges by:
    // making the database - just with one (or maybe two) court for the club at this point
    // making a few fragments to select court time and other stuff
    //sending the challenge over firebase
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
        setContentView(R.layout.activity_main)
        val dayFormat = SimpleDateFormat("dd")
        val monthFormat = SimpleDateFormat("MM")
        val day = dayFormat.format(Date())
        val month = monthFormat.format(Date())
        Log.d("testing dates", day)
        Log.d("testing dates", month)

        btnChallenges.setOnClickListener {
            loadAllChallengesFragment()
        }
        btnPlayers.setOnClickListener {
            loadPlayerList()
        }
        Firestore().userInfo()
        Variables.allUsersLive.observe(this, androidx.lifecycle.Observer{
            loadPlayerList()
        })
        Variables.challengedIdLive.observe(this, androidx.lifecycle.Observer {
            loadChallengeFragment()
        })
        Variables.chosenDayLive.observe(this, androidx.lifecycle.Observer {
            Firestore().findAvailableHours()

        })
        Variables.chosenHourLive.observe(this, androidx.lifecycle.Observer {
            Firestore().sendChallenge(to=Variables.challengedId, court=Variables.chosenCourt, day=Variables.chosenDay, hour=Variables.chosenHour)
            supportFragmentManager.beginTransaction().apply {
                clearFragmentsFromContainer()
                replace(R.id.flMAIN, PlayerList())
            }
        })
        Variables.chosenCourtLive.observe(this, androidx.lifecycle.Observer {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.flChallengeDetails,ChooseDayFragment())
                .commit()
        })
        Variables.freeHoursWithDayLive.observe(this, androidx.lifecycle.Observer {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.flChallengeDetails,ChooseHourFragment())
                .commit()
        })
    }


    private fun loadChallengeFragment(){
        supportFragmentManager.beginTransaction().apply {
            clearFragmentsFromContainer()
            addToBackStack(null)
            replace(R.id.flChallenge,  ChallengeFragment())
            commit()
        }
    }
    private fun loadAllChallengesFragment(){
        supportFragmentManager.beginTransaction().apply {
            clearFragmentsFromContainer()
            addToBackStack(null)
            replace(R.id.flMAIN,  AllChallengesFragment())
            commit()
        }
    }
    private fun loadPlayerList(){
        supportFragmentManager.beginTransaction().apply {
            clearFragmentsFromContainer()
            addToBackStack(null)
            replace(R.id.flMAIN,  PlayerList())
            commit()
        }
    }
    private fun clearFragmentsFromContainer() {
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        supportFragmentManager.popBackStack(Constants.NONAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}





