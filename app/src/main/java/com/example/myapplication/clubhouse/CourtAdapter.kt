package com.example.myapplication.clubhouse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.courts_qreq.view.*

class CourtAdapter(
    var Courts: MutableList<String>,
    val listener: ChooseCourtFragment
    ) : RecyclerView.Adapter<CourtAdapter.CourtViewHolder>() {

        inner class CourtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourtViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.courts_qreq, parent, false)

            return CourtViewHolder(view)
        }
        override fun getItemCount(): Int {
            return Courts.size
        }
        override fun onBindViewHolder(holder: CourtViewHolder, position: Int) {
            holder.itemView.apply {
                tvCourtName.text = Courts[position]
            }
        }
        interface OnItemClickListener{
            fun onItemClick(position: Int)
        }
    }

