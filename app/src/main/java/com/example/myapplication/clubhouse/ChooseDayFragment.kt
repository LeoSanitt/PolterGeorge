package com.example.myapplication.clubhouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_choose_court.*
import kotlinx.android.synthetic.main.fragment_choose_day.*

class ChooseDayFragment : Fragment(R.layout.fragment_choose_day), DayAdapter.OnItemClickListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        rvDays.adapter = DayAdapter(Constants.DAYS, this)
        rvDays.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rvDays.layoutManager = LinearLayoutManager(context)
        }



    override fun onItemClick(position: Int) {
        Variables.chosenDay = Constants.DAYS[position]
        Variables.chosenDayLive.apply {postValue(Variables.chosenDay)}
    }
}
