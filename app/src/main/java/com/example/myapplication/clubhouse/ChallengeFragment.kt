package com.example.myapplication.clubhouse

import android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.example.myapplication.Firebase.Firestore
import com.example.myapplication.R
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_challenge.*
//import

class ChallengeFragment : Fragment(R.layout.fragment_challenge){
    //fun popStack(){
   //     activity!!.supportFragmentManager.popBackStack(null, Fragment.POP_BACK_STACK_INCLUSIVE)
   // }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textToDisplay = Constants.CHALLENGINGTITLE + Variables.challengedName
        tvChallengedName.text = textToDisplay
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.flChallengeDetails,ChooseCourtFragment())
            .commit()
       // Variables.chosenCourtLive.observe(this, androidx.lifecycle.Observer {
       //     activity!!.supportFragmentManager.beginTransaction()
       //         .addToBackStack(null)
       //         .replace(R.id.flChallengeDetails,ChooseDayFragment())
       //         .commit()
       // })
      //  Variables.freeHoursWithDayLive.observe(this, androidx.lifecycle.Observer {
       //     activity!!.supportFragmentManager.beginTransaction()
       //         .addToBackStack(null)
       //         .replace(R.id.flChallengeDetails,ChooseHourFragment())
      //          .commit()
       // })

    }
}
