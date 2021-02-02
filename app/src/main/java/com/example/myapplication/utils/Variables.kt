package com.example.myapplication.utils

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.clubhouse.Player

object Variables {
    var allNames: MutableList<Player> = mutableListOf()
    var namesChanged = MutableLiveData<MutableList<Player>>()
   // var changes = MutableLiveData<Int>()
    var userClub: String = ""
    var userName: String = ""
    var challengedName =  MutableLiveData<String>()
}


