package com.example.myapplication.models

import com.example.myapplication.utils.Variables
import java.util.*

class Challenge (

        val players: MutableList<String> =  mutableListOf(Variables.id),
        val fromName: String = "",
        val toName: String = "",
        val court: String = "",
        val date: String = "",
        val hour: String = "",
        val message: String = "",
        val accepted: Boolean = false,
        val rejected: Boolean = false,
        val uniqueId: String = ""
        )