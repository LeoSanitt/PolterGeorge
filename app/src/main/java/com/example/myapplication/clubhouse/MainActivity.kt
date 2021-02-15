package com.example.myapplication.clubhouse

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Firebase.Firestore
import com.example.myapplication.R
import com.example.myapplication.models.User
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Variables
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_info_qreq.*
import kotlinx.android.synthetic.main.player_info_qreq.view.*
import kotlinx.android.synthetic.main.sp_settings_open.*
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.days

class MainActivity : AppCompatActivity() {
    // TODO over the next week
    /*
    Take away messages from challenges - its not worth having (tick =  true)
    Add sending notifications to people (tick =  false)
    Add notifications for accepts, rejects, and for messages that people sent  (tick =  false)
    Change how the sign in system works - firstly ONLY phone number and then everything else (including club and name) (tick =  true)
    Add uses for everything in the toolbar (tick =  false)
    DONE :):):):):) (tick =  false)
    ->
    try to get someplace using it (tick =  false)
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

        Variables.userNameLive.observe(this, androidx.lifecycle.Observer {
            if (Variables.userName == ""){
                intent = Intent(applicationContext, AdditionalInfo::class.java)
                startActivityForResult(intent, 0)
                finish()
            }
        })
        Firestore().userInfo()
        setContentView(R.layout.activity_main)
        //val toolbar = findViewById<Toolbar>(R.id.bottomToolbar)
        //setActionBar(toolbar)
        Variables.days = getDays()
        val adapterFilter = spFilterAdapter(this, spFilterOptions.options)
        spFilter.adapter = adapterFilter


        spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent!!.getItemAtPosition(position)
                if (selectedItem=="Filter by age"){
                    if (Variables.userAge==""){
                        Toast.makeText(applicationContext, "You have not given your age", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val usersCloseToAge = mutableListOf<User>()
                        for (player in Variables.allUsers) {
                            if (player.userAge == Variables.userAge) {
                                usersCloseToAge.add(player)
                            }
                        }
                        Variables.allUsersDisplay = usersCloseToAge
                        loadPlayerList()
                    }
                }
                if (selectedItem=="No filter"){
                    Variables.allUsersDisplay = Variables.allUsers
                    loadPlayerList()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {

            }
        }
        val adapterSettings = spSettingsAdapter(this, spSettingsOptions.options)
        spSettings.adapter = adapterSettings
        spSettings.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent!!.getItemAtPosition(position)
                if (selectedItem=="Change profile"){
                    spSettings.setSelection(0)
                    intent  = Intent(applicationContext, AdditionalInfo::class.java)
                    startActivity(intent)
                }
                if (selectedItem=="Notifications") {
                    spSettings.setSelection(0)
                    loadNotifications()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        searchView.queryHint = ""
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (Variables.currentFragment == "PlayerListFragment") {
                    if (newText!!.isNotEmpty()) {
                        val search = newText.toLowerCase(Locale.getDefault())
                        Variables.allUsersDisplay = mutableListOf()
                        Variables.allUsers.forEach {
                            if (it.userName.toLowerCase(Locale.getDefault()).contains(search)) {
                                Variables.allUsersDisplay.add(it)
                            }
                        }
                        loadPlayerList()
                    }
                    if (newText!!.isEmpty()) {
                        Variables.allUsersDisplay = Variables.allUsers
                        loadPlayerList()
                    }
                }
                if (Variables.currentFragment == "AllChallengesFragment") {
                    if (newText!!.isNotEmpty()) {
                        val search = newText.toLowerCase(Locale.getDefault())
                        Variables.allChallengesDisplay = mutableListOf()
                        Variables.allChallenges.forEach {
                            if (it.fromName.toLowerCase(Locale.getDefault()).contains(search)) {
                                Variables.allChallengesDisplay.add(it)
                            }
                        }
                        loadAllChallengesFragment()
                    }
                    if (newText!!.isEmpty()) {
                        Variables.allChallengesDisplay = Variables.allChallenges
                        loadAllChallengesFragment()
                    }
                }
                if (Variables.currentFragment == "BookingsFragment") {
                    if (newText!!.isNotEmpty()) {
                        val search = newText.toLowerCase(Locale.getDefault())
                        Variables.allBookingsDisplay = mutableListOf()
                        Variables.allBookings.forEach {
                            if (it.fromName.toLowerCase(Locale.getDefault()).contains(search) || it.toName.toLowerCase(Locale.getDefault()).contains(search)) {
                                Variables.allBookingsDisplay.add(it)
                            }
                        }
                        loadBookings()
                    }
                    if (newText!!.isEmpty()) {
                        Variables.allBookingsDisplay = Variables.allBookings
                        loadBookings()
                    }
                }
                return true
            }
        }

        )
        tvChallenges.setOnClickListener {
            loadAllChallengesFragment()
        }
        tvPlayers.setOnClickListener {
            loadPlayerList()
        }
        tvBookings.setOnClickListener {
            loadBookings()
        }

        Variables.allUsersLive.observe(this, androidx.lifecycle.Observer {
            loadPlayerList()
        })
        Variables.challengedIdLive.observe(this, androidx.lifecycle.Observer {
            loadChallengeFragment()
        })
        Variables.chosenDayLive.observe(this, androidx.lifecycle.Observer {
            Firestore().findAvailableHours()

        })

        /*
        Variables.messageLive.observe(this, androidx.lifecycle.Observer {
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

         */


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

        }
        return true
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
        Variables.currentFragment = "AllChallengesFragment"
        supportFragmentManager.beginTransaction().apply {
            clearFragmentsFromContainer()
            addToBackStack(null)
            replace(R.id.flMAIN, AllChallengesFragment())
            commit()
        }
    }

    private fun loadPlayerList() {
        Variables.currentFragment = "PlayerListFragment"
        supportFragmentManager.beginTransaction().apply {
            clearFragmentsFromContainer()
            addToBackStack(null)
            replace(R.id.flMAIN, PlayerList())
            commit()
        }
    }

    private fun loadBookings() {
        Variables.currentFragment = "BookingsFragment"
        supportFragmentManager.beginTransaction().apply {
            clearFragmentsFromContainer()
            addToBackStack(null)
            replace(R.id.flMAIN, BookingsFragment())
            commit()
        }
    }
    private fun loadNotifications(){
        Variables.currentFragment = "NotificationsFragment"
        supportFragmentManager.beginTransaction().apply {
            clearFragmentsFromContainer()
            addToBackStack(null)
            replace(R.id.flMAIN, NotificationsFragment())
            commit()
        }
    }
    private fun loadMessage(){
        supportFragmentManager.beginTransaction().apply {
            clearFragmentsFromContainer()
            addToBackStack(null)
            replace(R.id.flMAIN, AddMessageFragment())
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





