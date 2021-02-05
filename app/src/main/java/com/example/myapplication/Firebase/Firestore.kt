package com.example.myapplication.Firebase
import android.util.Log
import com.example.myapplication.models.User
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Variables
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.example.myapplication.models.Challenge
import com.example.myapplication.models.Court
import com.example.myapplication.models.Hour
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class Firestore {
    val mFirestore = FirebaseFirestore.getInstance()
    fun getNameWithId(id: String){
        mFirestore.collection(Constants.USERSNOTCLUBS).document(id).get().addOnSuccessListener { document ->
            Variables.challengedName = document.toObject(User::class.java)!!.userName
            Variables.challengedIdLive.apply { postValue(Variables.challengedName) }
        }
    }
    fun registerUser(userInfo: User, club: String) {
        mFirestore.collection(Constants.CLUBS).document(club).collection(Constants.USERS).document(userInfo.userId)
                .set(userInfo, SetOptions.merge())
        mFirestore.collection(Constants.USERSNOTCLUBS).document(userInfo.userId).set(userInfo, SetOptions.merge())
    }

    fun addInfo(age: String, gender: String, level: String) {
        val id = getUserId()
        mFirestore.collection(Constants.USERSNOTCLUBS).document(id).get()
                .addOnSuccessListener { document ->
                    Variables.userClub = document.toObject(User::class.java)!!.userClub
                    Variables.userName = document.toObject(User::class.java)!!.userName
                    mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.USERS)
                            .document(id).set(age, SetOptions.merge())
                    mFirestore.collection(Constants.USERSNOTCLUBS).document(id).set(age, SetOptions.merge())
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

    fun userInfo(){
        val id = Variables.id
        mFirestore.collection(Constants.USERSNOTCLUBS).document(id).get()
                .addOnSuccessListener { document ->
                    val thisUser = document.toObject(User::class.java)
                    Variables.userClub = thisUser!!.userClub
                    Variables.userName = thisUser.userName
                    val users = mutableListOf<User>()
                    Log.d("checking whether club", Variables.userClub)
                    mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.USERS).get().addOnSuccessListener { result ->
                        for (document in result) {
                            val currentPlayer = document.toObject(User::class.java)
                            users.add(User(currentPlayer.userName, currentPlayer.userId, currentPlayer.userAge,currentPlayer.userGender, Variables.userClub))
                        }
                        Variables.allUsers = users
                        Variables.allUsersLive.setValue(users)
                    }
                    mFirestore.collection(Constants.USERSNOTCLUBS).document(Variables.id).collection(Constants.CHALLENGES).get().addOnSuccessListener { result ->
                        Variables.allChallenges = mutableListOf()
                        for (challenge in result){
                            Variables.allChallenges.add(challenge.toObject(Challenge::class.java))
                        }
                    }
                    mFirestore.collection(Constants.USERSNOTCLUBS).document(Variables.id).collection(Constants.CHALLENGES).addSnapshotListener { snapshots, error ->
                        if (error!=null){
                            return@addSnapshotListener
                        }
                        Variables.allChallenges = mutableListOf()
                        for (challenge in snapshots!!){
                            val toAdd = challenge.toObject(Challenge::class.java)
                            Variables.allChallenges.add(toAdd)
                        }
                    }
                    mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.BOOKINGS).get().addOnSuccessListener { result ->
                        Variables.clubCourts = mutableListOf()
                        for (court in result){
                            Variables.clubCourts.add(court.toObject(Court::class.java).name)
                        }
                    }
                }



    }

    fun sendChallenge(to: String, court: String, day: String, hour: String) {
        mFirestore.collection(Constants.USERSNOTCLUBS).document(to).collection(Constants.CHALLENGES).document(to+Variables.id+Date()).set(Challenge(Variables.id, to, Variables.userName, Variables.challengedName, court, day, hour))
        mFirestore.collection(Constants.USERSNOTCLUBS).document(Variables.id).collection(Constants.CHALLENGES).document(to+Variables.id+Date()).set(Challenge(Variables.id, to, Variables.userName, Variables.challengedName, court, day, hour))
    }

    fun findAvailableHours() {
        mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.BOOKINGS).document(Variables.chosenCourt).collection(Variables.chosenDay).whereEqualTo("booked", false).get().addOnSuccessListener { result ->
            Variables.freeHoursWithDay = mutableListOf()
            for (document in result){
                Variables.freeHoursWithDay.add(document.toObject(Hour::class.java).time)
            }
            Variables.freeHoursWithDayLive.apply{setValue(Variables.freeHoursWithDay)}
        }

    }
}
