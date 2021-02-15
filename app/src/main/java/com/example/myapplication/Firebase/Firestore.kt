package com.example.myapplication.Firebase
import android.content.Context
import android.content.Intent
import android.service.notification.Condition.newId
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.myapplication.clubhouse.AdditionalInfo
import com.example.myapplication.models.*
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Variables
import com.google.errorprone.annotations.Var
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.model.mutation.Precondition.exists
import com.google.firebase.ktx.Firebase
import java.util.*

class Firestore {
    val mFirestore = FirebaseFirestore.getInstance()
    fun getAllClubs(){
        Log.d("DEETS", "testing")
        val clubNames = mutableListOf<String>()
        mFirestore.collection(Constants.CLUBS).get().addOnSuccessListener { result ->
            Log.d("DEETS", result.toString())
            for(club in result){
                Log.d("DEETS", club.toObject(Club::class.java).name)
                val name = club.toObject(Club::class.java).name
                clubNames.add(name)
                Log.d("DEETS DEETS", clubNames.toString())
            }
            Variables.allClubs = clubNames
        }

    }
    fun getNameWithId(id: String){
        mFirestore.collection(Constants.USERSNOTCLUBS).document(id).get().addOnSuccessListener { document ->
            Variables.challengedName = document.toObject(User::class.java)!!.userName
            Variables.challengedIdLive.apply { postValue(Variables.challengedName) }
        }
    }
    fun registerUser(userInfo: User) {
        mFirestore.collection(Constants.USERSNOTCLUBS).document(userInfo.userId).set(userInfo, SetOptions.merge())
    }

    fun addInfo(age: String, club: String, name: String) {
        val id = getUserId()
        val data = hashMapOf("userClub" to club, "userAge" to age, "userName" to name)
        mFirestore.collection(Constants.USERSNOTCLUBS).document(id).set(data, SetOptions.merge()).addOnSuccessListener {
            mFirestore.collection(Constants.USERSNOTCLUBS).document(id).get()
                    .addOnSuccessListener { document ->
                        Variables.userClub = document.toObject(User::class.java)!!.userClub
                        Variables.userName = document.toObject(User::class.java)!!.userName
                        mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.USERS)
                                .document(id).set(document.toObject(User::class.java)!!, SetOptions.merge())
                    }
                    .addOnSuccessListener {
                        userInfo()
                    }
        }

    }

    fun getUserId(): String {
        var currentUserId = ""
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    fun userInfo() {
        val id = Variables.id
        mFirestore.collection(Constants.USERSNOTCLUBS).document(id).get()
            .addOnSuccessListener { document ->
                val thisUser = document.toObject(User::class.java)
                Variables.userClub = thisUser!!.userClub
                Variables.userAge = thisUser!!.userAge
                Variables.userName = thisUser.userName
                Variables.userNameLive.apply{setValue(Variables.userName)}
                val users = mutableListOf<User>()
                Log.d("checking whether club", Variables.userClub)
                mFirestore.collection(Constants.CLUBS).document(Variables.userClub)
                    .collection(Constants.USERS).get().addOnSuccessListener { result ->
                    for (document in result) {
                        val currentPlayer = document.toObject(User::class.java)
                        users.add(
                            User(
                                currentPlayer.userName,
                                currentPlayer.userId,
                                currentPlayer.userAge,
                                currentPlayer.userGender,
                                Variables.userClub
                            )
                        )
                    }
                    Variables.allUsers = users
                    Variables.allUsersDisplay = users
                    Variables.allUsersLive.setValue(users)
                }
                mFirestore.collection(Constants.CLUBS).document(Variables.userClub)
                    .collection(Constants.CHALLENGES).whereArrayContains(Constants.PLAYERS, Variables.id).whereEqualTo("toName", Variables.userName)
                    .whereEqualTo("rejected", false).whereEqualTo("accepted", false).get()
                    .addOnSuccessListener { result ->
                        Variables.allChallenges = mutableListOf()
                        for (challenge in result) {
                            Variables.allChallenges.add(challenge.toObject(Challenge::class.java))
                        }
                        Variables.allChallengesDisplay = Variables.allChallenges
                    }
                mFirestore.collection(Constants.CLUBS).document(Variables.userClub)
                    .collection(Constants.CHALLENGES)
                    .whereArrayContains(Constants.PLAYERS, Variables.id).whereEqualTo("toName", Variables.userName).whereEqualTo("rejected", false)
                    .whereEqualTo("accepted", false)
                    .addSnapshotListener { snapshots, error ->
                        if (error != null) {
                            return@addSnapshotListener
                        }
                        Variables.allChallenges = mutableListOf()
                        for (challenge in snapshots!!) {
                            val toAdd = challenge.toObject(Challenge::class.java)
                            if (toAdd.players[0] == Variables.id && toAdd !in Variables.allChallenges) {
                                Variables.allChallenges.add(toAdd)
                            }
                        }
                        Variables.allChallengesDisplay = Variables.allChallenges
                    }
                mFirestore.collection(Constants.CLUBS).document(Variables.userClub)
                    .collection(Constants.BOOKINGS).get().addOnSuccessListener { result ->
                    Variables.clubCourts = mutableListOf()
                    for (court in result) {
                        Variables.clubCourts.add(court.toObject(Court::class.java).name)
                    }
                }
                mFirestore.collection(Constants.CLUBS).document(Variables.userClub)
                    .collection(Constants.CHALLENGES).whereArrayContains(Constants.PLAYERS, Variables.id)
                    .whereEqualTo("accepted", true).get().addOnSuccessListener { result ->
                        Variables.allBookings = mutableListOf()

                        for (booking in result) {
                            Variables.allBookings.add(booking.toObject(Challenge::class.java))
                        }
                        Variables.allBookingsDisplay = Variables.allBookings
                    }
                mFirestore.collection(Constants.CLUBS).document(Variables.userClub)
                    .collection(Constants.CHALLENGES)
                    .whereArrayContains(Constants.PLAYERS, Variables.id).whereEqualTo("accepted", true)
                    .addSnapshotListener { snapshots, error ->
                        if (error != null) {
                            return@addSnapshotListener
                        }
                        Variables.allBookings = mutableListOf()
                        for (booking in snapshots!!) {
                            val toAdd = booking.toObject(Challenge::class.java)
                            Variables.allBookings.add(toAdd)

                        }
                        Variables.allBookingsDisplay = Variables.allBookings
                    }

            }
    }

    fun sendChallenge(to: String, court: String, day: String, hour: String) {
        var accept = false
        if (Variables.challengedName == Constants.BOOKINGS){
            accept = true
        }
        mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.CHALLENGES).add(Challenge(
            players=mutableListOf(Variables.id, Variables.challengedId), fromName=Variables.userName, toName=Variables.challengedName, court=court, date=Variables.date, hour=hour, accepted = accept))
            .addOnSuccessListener { documentReference ->
                documentReference.update("uniqueId",documentReference.id)

            }
    }

    fun findAvailableHours() {
        val notToAdd = mutableListOf<String>()
        mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.CHALLENGES)
            .whereEqualTo("date", Variables.date).whereEqualTo("court", Variables.chosenCourt).whereEqualTo("accepted", true).get().addOnSuccessListener { result ->
                for (document in result) {
                    notToAdd.add(document.toObject(Challenge::class.java).hour)
                }

                mFirestore.collection(Constants.CLUBS).document(Variables.userClub)
                    .collection(Constants.BOOKINGS).document(Variables.chosenCourt)
                    .collection(Variables.chosenDay).get().addOnSuccessListener { result ->
                    Variables.freeHoursWithDay = mutableListOf()
                    for (document in result) {
                        if (document.toObject(Hour::class.java).time !in notToAdd)
                            Variables.freeHoursWithDay.add(document.toObject(Hour::class.java).time)
                    }
                        Variables.freeHoursWithDay = Variables.freeHoursWithDay.sortedDescending().toMutableList()
                        Variables.freeHoursWithDayLive.apply{setValue(Variables.freeHoursWithDay)}
                }

            }

        }


    fun rejectChallenge(challenge: Challenge){
        mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.CHALLENGES).document(challenge.uniqueId).update("rejected", true)
    }
    fun acceptChallenge(challenge: Challenge){
        mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.CHALLENGES)
            .whereEqualTo("accepted", true).whereEqualTo("date", challenge.date).whereEqualTo("hour", challenge.hour).get().addOnSuccessListener { result ->
                if(result.isEmpty){
                    mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.CHALLENGES).document(challenge.uniqueId).update("accepted", true)
                    Variables.textToDisplay = "Booking made!"
                    Variables.textToDisplayLive.apply{setValue(Variables.textToDisplay)}
                }

                else {
                    mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.CHALLENGES).document(challenge.uniqueId).update("rejected", true)
                    Variables.textToDisplay = "Booking failed - time already taken"
                    Variables.textToDisplayLive.apply{setValue(Variables.textToDisplay)}
                }

            }


    }

    fun cancelBooking(challenge: Challenge) {
        mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.CHALLENGES).document(challenge.uniqueId).update("accepted", false)
        mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.CHALLENGES).document(challenge.uniqueId).update("rejected", true)
    }
    fun getAllUsers(){
        val users = mutableListOf<User>()
        if (Variables.userClub!="") {
            mFirestore.collection(Constants.CLUBS).document(Variables.userClub)
                    .collection(Constants.USERS).get().addOnSuccessListener { result ->
                        for (document in result) {
                            val currentPlayer = document.toObject(User::class.java)
                            users.add(
                                    User(
                                            currentPlayer.userName,
                                            currentPlayer.userId,
                                            currentPlayer.userAge,
                                            currentPlayer.userGender,
                                            Variables.userClub
                                    )
                            )
                        }
                        Variables.allUsers = users
                        Variables.allUsersLive.setValue(users)

                    }
        }
    }
}
