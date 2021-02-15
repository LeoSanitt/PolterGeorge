package com.example.myapplication.utils

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Firebase.Firestore
import com.example.myapplication.models.Challenge
import com.example.myapplication.models.Notification
import com.example.myapplication.models.User

object Variables {
    var userNameLive = MutableLiveData<String>()
    var allUsers: MutableList<User> = mutableListOf()
    var allUsersDisplay: MutableList<User> = mutableListOf()
    var allUsersLive = MutableLiveData<MutableList<User>>()
    var allChallengesLive = MutableLiveData<MutableList<Challenge>>()
    var allChallenges: MutableList<Challenge> = mutableListOf()
    var allChallengesDisplay: MutableList<Challenge> = mutableListOf()
    var allBookings: MutableList<Challenge> = mutableListOf()
    var allBookingsDisplay: MutableList<Challenge> = mutableListOf()
    var userClub: String = ""
    var userName: String = ""
    var challengedIdLive =  MutableLiveData<String>()
    var challengedId: String = ""
    var challengedNameLive = MutableLiveData<String>()
    var challengedName: String = ""
    var id: String = Firestore().getUserId()
    var chosenCourt: String = ""
    var chosenCourtLive = MutableLiveData<String>()
    var chosenDay: String = ""
    var chosenDayLive = MutableLiveData<String>()
    var chosenHour: String = ""
    var chosenHourLive = MutableLiveData<String>()
    var clubCourts: MutableList<String> = mutableListOf()
    var freeHoursWithDay: MutableList<String> = mutableListOf()
    var freeHoursWithDayLive = MutableLiveData<MutableList<String>>()
    var allClubs: MutableList<String> = mutableListOf()
    var days: MutableList<String> = mutableListOf()
    var date: String = ""
    var textToDisplay = ""
    var textToDisplayLive = MutableLiveData<String>()
    var message: String = ""
    var messageLive = MutableLiveData<String>()
    var userAge: String = "0"
    var currentFragment: String = ""
    var allNotifications: MutableList<Notification> = mutableListOf()
    var allNotificationsDisplay: MutableList<Notification> = mutableListOf()
}