package com.example.myapplication.clubhouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_choose_day.*
import kotlinx.android.synthetic.main.fragment_choose_day.rvDays
import kotlinx.android.synthetic.main.fragment_choose_hour.*

class ChooseHourFragment : Fragment(R.layout.fragment_choose_hour), HourAdapter.OnItemClickListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvHours.adapter = HourAdapter(Variables.freeHoursWithDay, this)
        rvHours.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rvHours.layoutManager = LinearLayoutManager(context)

    }

    override fun onItemClick(position: Int) {
        Variables.chosenHour = Variables.freeHoursWithDay[position]
        Variables.chosenHourLive.apply {postValue(Variables.chosenHour)}
    }
}