package com.example.myapplication.clubhouse

import android.annotation.SuppressLint
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
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.days

class MainActivity : AppCompatActivity() {
    // TODO over the next week
    /*
    Get it working in real time - bookings are deleted after the time of the booking has passed
    Replace the challenge page with a notifications page where all notifications will be displayed (challenges, rejects, accepts etc) if possible otherwise add separate page for that
    Add functionality where messages can be added to challenges and accepts/rejects
    Add functionality to make a booking without going through a challenge
    Improve the UI so it could be an actual app that a club would pay for
     */
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
        Variables.days = getDays()
        btnChallenges.setOnClickListener {
            loadAllChallengesFragment()
        }
        btnPlayers.setOnClickListener {
            loadPlayerList()
        }
        btnBookings.setOnClickListener {
            loadBookings()
        }
        Firestore().userInfo()
        Variables.allUsersLive.observe(this, androidx.lifecycle.Observer {
            loadPlayerList()
        })
        Variables.challengedIdLive.observe(this, androidx.lifecycle.Observer {
            loadChallengeFragment()
        })
        Variables.chosenDayLive.observe(this, androidx.lifecycle.Observer {
            Firestore().findAvailableHours()

        })
        Variables.chosenHourLive.observe(this, androidx.lifecycle.Observer {
            Firestore().sendChallenge(
                to = Variables.challengedId,
                court = Variables.chosenCourt,
                day = Variables.chosenDay,
                hour = Variables.chosenHour
            )
            supportFragmentManager.beginTransaction().apply {
                clearFragmentsFromContainer()
                replace(R.id.flMAIN, PlayerList())
            }
        })
        Variables.chosenCourtLive.observe(this, androidx.lifecycle.Observer {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.flChallengeDetails, ChooseDayFragment())
                .commit()
        })
        Variables.freeHoursWithDayLive.observe(this, androidx.lifecycle.Observer {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.flChallengeDetails, ChooseHourFragment())
                .commit()
        })
    }


    private fun loadChallengeFragment() {
        supportFragmentManager.beginTransaction().apply {
            clearFragmentsFromContainer()
            addToBackStack(null)
            replace(R.id.flChallenge, ChallengeFragment())
            commit()
        }
    }

    private fun loadAllChallengesFragment() {
        supportFragmentManager.beginTransaction().apply {
            clearFragmentsFromContainer()
            addToBackStack(null)
            replace(R.id.flMAIN, AllChallengesFragment())
            commit()
        }
    }

    private fun loadPlayerList() {
        supportFragmentManager.beginTransaction().apply {
            clearFragmentsFromContainer()
            addToBackStack(null)
            replace(R.id.flMAIN, PlayerList())
            commit()
        }
    }

    private fun loadBookings() {
        supportFragmentManager.beginTransaction().apply {
            clearFragmentsFromContainer()
            addToBackStack(null)
            replace(R.id.flMAIN, BookingsFragment())
            commit()
        }
    }

    private fun clearFragmentsFromContainer() {
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        supportFragmentManager.popBackStack(
            Constants.NONAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    @SuppressLint("SimpleDateFormat")
    fun getDays(): MutableList<String> {
        val days = mutableListOf<String>()
        val overall = SimpleDateFormat("EEEE-dd-MMM")
        val cal = Calendar.getInstance()
        var date = cal.time
        for (i in 1..Constants.NUMBEROFDAYS) {
            Log.d("pleasework", overall.format(date))
            cal.add(Calendar.DAY_OF_YEAR, 1)
            date = cal.time
            days.add(overall.format(date))
        }
        return days
    }
}





