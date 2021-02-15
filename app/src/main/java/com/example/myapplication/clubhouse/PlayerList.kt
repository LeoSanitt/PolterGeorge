package com.example.myapplication.clubhouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_player_list.*
class PlayerList : Fragment(R.layout.fragment_player_list), PlayerAdapter.OnItemClickListener
    {
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            rvPlayers.adapter = PlayerAdapter(Variables.allUsersDisplay, this)
            rvPlayers.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            rvPlayers.layoutManager = LinearLayoutManager(context)

        }
        override fun onItemClick(position: Int) {
            val playerClicked = Variables.allUsers[position]
            Variables.challengedId = playerClicked.userId
            Variables.challengedName = playerClicked.userName
            Variables.challengedIdLive.apply { postValue(playerClicked.userId) }

            }
        }



