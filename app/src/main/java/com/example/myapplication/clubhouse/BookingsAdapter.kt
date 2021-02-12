package com.example.myapplication.clubhouse

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Challenge
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.bookings_qreq.view.*
import kotlinx.android.synthetic.main.challenges_info_qreq.view.*

class BookingsAdapter (
    var Bookings: MutableList<Challenge>,
    val listener: OnItemClickListener
    ) : RecyclerView.Adapter<BookingsAdapter.BookingsViewHolder>() {

        inner class BookingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

            init{
                itemView.imgCancel.setOnClickListener{
                    val position: Int = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        listener.onCancel(position)
                    }
                }
            }
            override fun onClick(v: View?) {
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.bookings_qreq, parent, false)
            return BookingsViewHolder(view)
        }
        override fun getItemCount(): Int {
            return Bookings.size
        }
        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: BookingsViewHolder, position: Int) {
            holder.itemView.apply {
                tvBookingCourt.text = " on "+Bookings[position].court
                tvBookingDay.text = " on "+Bookings[position].date.split("-")[0] + Bookings[position].date.split("-")[1]
                tvBookingHour.text = " at "+Bookings[position].hour + " O'clock"
                if (Bookings[position].players[0]==Variables.id) {
                    tvBookingName.text = "Match against" + Bookings[position].fromName
                }
                else{tvBookingName.text = "Match against" + Bookings[position].toName}
            }
        }
    interface OnItemClickListener{
        fun showBookingDetails(position: Int)
        fun onCancel(position: Int)
    }
}