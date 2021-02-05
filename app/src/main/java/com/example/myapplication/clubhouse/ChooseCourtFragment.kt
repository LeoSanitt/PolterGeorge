package com.example.myapplication.clubhouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_choose_court.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChooseCourtFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseCourtFragment : Fragment(R.layout.fragment_choose_court), CourtAdapter.OnItemClickListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvCourts.adapter = CourtAdapter(Variables.clubCourts, this)
        rvCourts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rvCourts.layoutManager = LinearLayoutManager(context)

    }

    override fun onItemClick(position: Int) {
        Variables.chosenCourt = Variables.clubCourts[position]
        Variables.chosenCourtLive.apply {postValue(Variables.chosenCourt)}
    }
}
