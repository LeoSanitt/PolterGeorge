package com.example.myapplication.clubhouse

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.myapplication.R
import kotlinx.android.synthetic.main.sp_filter_open.view.*

class spFilterAdapter(context: Context, optionsList: Array<String>) : ArrayAdapter<String>(context, 0, optionsList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.sp_filter_closed, parent, false)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.sp_filter_open, parent, false)
        view.tvOption.text = getItem(position)
        return view
    }

}