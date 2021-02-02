package com.example.myapplication.models

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class User(
    val id: String = "",
    val name: String = "",
    val club: String = "",
    val phoneNo: String="",
    val age:String = "",
    val gender: String= "",
    val level: String = "")

