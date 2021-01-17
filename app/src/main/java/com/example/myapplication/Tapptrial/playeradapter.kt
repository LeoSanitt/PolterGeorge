package com.example.myapplication.Tapptrial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.player_info_qreq.view.*

class playeradapter(
    var players: MutableList<Player>
) : RecyclerView.Adapter<playeradapter.playerVierHolder>() {

    inner class playerVierHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): playerVierHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_info_qreq, parent, false)
        return playerVierHolder(view)
    }
    override fun getItemCount(): Int {
        return players.size
    }
    override fun onBindViewHolder(holder: playerVierHolder, position: Int) {
        holder.itemView.apply {
            tvPlayerName.text = players[position].playerTitle
            btnPlayerELO.text = players[position].elo
        }
    }
    fun tempAddPlayer() {
        players.add(Player("John", "1000"))
        notifyItemInserted(players.size-1)
        }
}