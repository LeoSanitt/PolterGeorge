package com.example.myapplication.Tapptrial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class playeradapter(
    private val players: MutableList<Player>
) : RecyclerView.Adapter<playeradapter.playerviewh>() {

    class playerviewh(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): playerviewh {
        return playerviewh(
            LayoutInflater.from(parent.context).inflate(
                R.layout.player_info_qreq,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: playerviewh, position: Int) {
        val cur
    }
}