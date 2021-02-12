package com.example.myapplication.clubhouse

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Firebase.Firestore
import com.example.myapplication.R
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.fragment_bookings.*
import kotlinx.android.synthetic.main.fragment_player_list.*

class BookingsFragment : Fragment(R.layout.fragment_bookings),BookingsAdapter.OnItemClickListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRV()
        imgNewBooking.setOnClickListener {
            Variables.challengedId = Constants.BOOKINGS
            Variables.challengedName = Constants.BOOKINGS
            Variables.challengedIdLive.apply {
                postValue(Constants.BOOKINGS)
            }
        }
    }

    override fun showBookingDetails(position: Int) {
        Log.d("hi there", "hello")
    }

    override fun onCancel(position: Int) {
        Firestore().cancelBooking(Variables.allBookings[position])
        Variables.allBookings.removeAt(position)
        setUpRV()
        Toast.makeText(context, "Booking cancelled", Toast.LENGTH_SHORT).show()
    }
    private fun setUpRV(){
        rvBookings.adapter = BookingsAdapter(Variables.allBookings, this)
        rvBookings.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        rvBookings.layoutManager = LinearLayoutManager(context)
    }
}

