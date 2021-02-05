package com.example.myapplication.clubhouse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.days_qreq.view.*

class DayAdapter (
    var Days: List<String>,
    val listener: ChooseDayFragment
    ) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

        inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.days_qreq, parent, false)

            return DayViewHolder(view)
        }
        override fun getItemCount(): Int {
            return Days.size
        }
        override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
            holder.itemView.apply {
                tvDayName.text = Days[position]
            }
        }
        interface OnItemClickListener{
            fun onItemClick(position: Int)
        }
    }



