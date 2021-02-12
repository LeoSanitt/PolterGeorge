package com.example.myapplication.clubhouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.fragment_add_message.*


class AddMessageFragment : Fragment(R.layout.fragment_add_message) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imgSend.setOnClickListener {
            Variables.message = tiMessage.text.toString()
            Variables.messageLive.apply { setValue(Variables.message) }
        }
    }
}