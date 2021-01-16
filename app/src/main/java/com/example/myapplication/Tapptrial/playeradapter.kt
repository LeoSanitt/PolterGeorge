package com.example.myapplication.Tapptrial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.player_info_qreq.view.*

class playeradapter(
    private val players: MutableList<Player>
) : RecyclerView.Adapter<playeradapter.playerVierHolder>() {

    class playerVierHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): playerVierHolder {
        return playerVierHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.player_info_qreq,
                parent,
                false
            )
        )
    }

    fun tempAddPlayer() {
        players.add(Player("John", "1000"))
        notifyItemInserted(players.size-1)
    }


    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: playerVierHolder, position: Int) {
        val curPlayer = players[position]
        holder.itemView.apply{
            Playerinf.text = curPlayer.playerTitle
            PlayerELO.text = curPlayer.elo
        }
    }
}