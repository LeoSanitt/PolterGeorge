package com.example.myapplication.clubhouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Firebase.Firestore
import com.example.myapplication.R
import com.example.myapplication.utils.Variables
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bookings.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_player_list.*
class NotificationsFragment : Fragment(R.layout.fragment_notifications), NotificationsAdapter.OnItemClickListener
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRV()
    }

    override fun onCancel(position: Int) {
        Firestore().deleteNotification(Variables.allNotificationsDisplay[position])
        Variables.allNotificationsDisplay.removeAt(position)
        setUpRV()
        Toast.makeText(context, "Notification deleted", Toast.LENGTH_SHORT).show()    }
    private fun setUpRV(){
        rvNotifications.adapter = NotificationsAdapter(Variables.allNotificationsDisplay, this)
        rvNotifications.addItemDecoration(
                DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                )
        )
        rvNotifications.layoutManager = LinearLayoutManager(context)
    }
}