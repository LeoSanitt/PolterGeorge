package com.example.myapplication.clubhouse

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.User
import kotlinx.android.synthetic.main.player_info_qreq.view.*

class PlayerAdapter (
    var Users: MutableList<User>,
    val listener: OnItemClickListener
    ) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_info_qreq, parent, false)

        return PlayerViewHolder(view)
    }
    override fun getItemCount(): Int {
        return Users.size
    }
    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.itemView.apply {
            tvPlayerName.text = Users[position].userName
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}



