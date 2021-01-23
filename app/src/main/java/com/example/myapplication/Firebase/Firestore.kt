package com.example.myapplication.Firebase
import com.example.myapplication.VerifyActivity
import com.example.myapplication.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
class Firestore {
    val mFirestore = FirebaseFirestore.getInstance()
    fun registerUser(userInfo: User, club: String) {
        mFirestore.collection("Clubs").document(club).collection("users").document(userInfo.id).set(userInfo, SetOptions.merge())
    }
}
