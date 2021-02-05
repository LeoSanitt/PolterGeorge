package com.example.myapplication.clubhouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.fragment_all_challenges.*
import kotlinx.android.synthetic.main.fragment_player_list.*

class AllChallengesFragment : Fragment(R.layout.fragment_all_challenges), ChallengesAdapter.OnItemClickListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvChallenges.adapter = ChallengesAdapter(Variables.allChallenges, this)
        rvChallenges.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rvChallenges.layoutManager = LinearLayoutManager(context)
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}