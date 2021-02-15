package com.example.myapplication.clubhouse

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.myapplication.R
import kotlinx.android.synthetic.main.sp_filter_open.view.*
import kotlinx.android.synthetic.main.sp_settings_open.view.*

class spSettingsAdapter(context: Context, settingsList: Array<String>) : ArrayAdapter<String>(context, 0, settingsList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.sp_settings_closed, parent, false)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.sp_settings_open, parent, false)
        view.tvSetting.text = getItem(position)
        return view
    }

}