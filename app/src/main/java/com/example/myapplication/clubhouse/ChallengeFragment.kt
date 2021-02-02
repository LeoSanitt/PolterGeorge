package com.example.myapplication.clubhouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.fragment_challenge.*

class ChallengeFragment : Fragment(R.layout.fragment_challenge){


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textToDisplay = Constants.CHALLENGINGTITLE + Variables.challengedName
        tvChallengedName.text = textToDisplay
    }
}
