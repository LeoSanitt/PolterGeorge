package com.example.myapplication.Firebase
import android.content.Context
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.clubhouse.LaunchActivity
import com.example.myapplication.clubhouse.Player
import com.example.myapplication.clubhouse.playeradapter
import com.example.myapplication.models.User
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Variables
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_main.*
import com.example.myapplication.clubhouse.MainActivity
import com.example.myapplication.models.Challenge
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*

class Firestore {
    val mFirestore = FirebaseFirestore.getInstance()
    fun registerUser(userInfo: User, club: String) {
        mFirestore.collection(Constants.CLUBS).document(club).collection(Constants.USERS).document(userInfo.id)
                .set(userInfo, SetOptions.merge())
        mFirestore.collection(Constants.USERSNOTCLUBS).document(userInfo.id).set(userInfo, SetOptions.merge())
    }

    fun addInfo(age: String, gender: String, level: String) {
        val id = getUserId()
        mFirestore.collection(Constants.USERSNOTCLUBS).document(id).get()
                .addOnSuccessListener { document ->
                    Variables.userClub = document.toObject(User::class.java)!!.club
                    Variables.userName = document.toObject(User::class.java)!!.name
                    mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.USERS)
                            .document(id).set(age, SetOptions.merge())
                    mFirestore.collection(Constants.USERSNOTCLUBS).document(id).set(age, SetOptions.merge())
                }

    }

    private fun getUserId(): String {
        var currentUserId = ""
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    fun userInfo(){
        val id = getUserId()
        mFirestore.collection(Constants.USERSNOTCLUBS).document(id).get()
                .addOnSuccessListener { document ->
                    val thisUser = document.toObject(User::class.java)
                    Variables.userClub = thisUser!!.club
                    Variables.userName = thisUser.name
                    val names = mutableListOf<Player>()
                    mFirestore.collection(Constants.CLUBS).document(Variables.userClub).collection(Constants.USERS).get().addOnSuccessListener { result ->
                        for (document in result) {
                            val currentPlayer = document.toObject(User::class.java)
                            names.add(Player(currentPlayer.name, currentPlayer.age, currentPlayer.gender,currentPlayer.id))
                        }
                        Variables.allNames = names
                        Variables.namesChanged.setValue(names)
                    }
                }


    }

    fun sendChallenge(to: String, court: String, day: String, hour: String) {
        mFirestore.collection(Constants.USERSNOTCLUBS).document(to).collection(Constants.CHALLENGES).document(Date().toString()).set(Challenge(getUserId(), to, court, day, hour))
    }
}
