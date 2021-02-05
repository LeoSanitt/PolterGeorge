package com.example.myapplication.models

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

data class User(
    val userName: String = "",
    val userId: String = "",
    val userAge: String = "",
    val userGender: String = "",
    val userClub: String = ""
)

