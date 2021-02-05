package com.example.myapplication.clubhouse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.days_qreq.view.*
import kotlinx.android.synthetic.main.hours_qreq.view.*

class HourAdapter (
    var Hours: MutableList<String>,
    val listener: ChooseHourFragment
) : RecyclerView.Adapter<HourAdapter.HourViewHolder>() {

    inner class HourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hours_qreq, parent, false)

        return HourViewHolder(view)
    }
    override fun getItemCount(): Int {
        return Hours.size
    }
    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.itemView.apply {
            tvHourName.text = Hours[position]
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}
