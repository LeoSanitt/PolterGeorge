package com.example.myapplication.models

data class Notification(
        val concerning: String = "",
        val uniqueId: String = "",
        val title: String = "",
        val message: String = ""
)