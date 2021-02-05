package com.example.myapplication.clubhouse

import com.example.myapplication.models.Challenge
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Firebase.Firestore
import com.example.myapplication.R
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.challenges_info_qreq.view.*
import kotlinx.android.synthetic.main.player_info_qreq.view.*
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent

class ChallengesAdapter (
    var Challenges: MutableList<Challenge>,
    val listener: OnItemClickListener
) : RecyclerView.Adapter<ChallengesAdapter.ChallengesViewHolder>() {

    inner class ChallengesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.challenges_info_qreq, parent, false)

        return ChallengesViewHolder(view)
    }
    override fun getItemCount(): Int {
        return Challenges.size
    }
    override fun onBindViewHolder(holder: ChallengesViewHolder, position: Int) {
        holder.itemView.apply {
            if (Challenges[position].to==Variables.id) {
                tvName.text = Challenges[position].fromName
            }
            else{
                tvName.text = Challenges[position].toName
            }
            tvCourt.text = Challenges[position].court
            tvDay.text = Challenges[position].day
            tvHour.text = Challenges[position].hour
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}