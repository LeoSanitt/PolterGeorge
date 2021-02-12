package com.example.myapplication.clubhouse

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.fragment_challenge.*

class ChallengeFragment : Fragment(R.layout.fragment_challenge){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (Constants.BOOKINGS != Variables.challengedName) {
            val textToDisplay = Constants.CHALLENGINGTITLE + Variables.challengedName
            tvChallengedName.text = textToDisplay
        }
        else{
            val textToDisplay ="Make booking"
            tvChallengedName.text = textToDisplay
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flChallengeDetails,ChooseCourtFragment())
            .commit()
    }
}
