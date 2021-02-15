package com.example.myapplication.clubhouse

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Challenge
import com.example.myapplication.models.Notification
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.bookings_qreq.view.*
import kotlinx.android.synthetic.main.challenges_info_qreq.view.*
import kotlinx.android.synthetic.main.notifications_qreq.view.*

class NotificationsAdapter (
    var Notifications: MutableList<Notification>,
    val listener: OnItemClickListener
) : RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder>() {

    inner class NotificationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init{
            itemView.imgDeleteNotification.setOnClickListener{
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onCancel(position)
                }
            }
        }
        override fun onClick(v: View?) {
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bookings_qreq, parent, false)
        return NotificationsViewHolder(view)
    }
    override fun getItemCount(): Int {
        return Notifications.size
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        holder.itemView.apply {
            tvMessage.text = Notifications[position].message
            tvTitle.text =  Notifications[position].title
        }
    }
    interface OnItemClickListener{
        fun onCancel(position: Int)
    }
}