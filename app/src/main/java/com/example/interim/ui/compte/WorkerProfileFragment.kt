package com.example.interim.ui.compte

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.models.TemporaryWorker
import com.example.interim.services.UsersService

class WorkerProfileFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.worker_profile_fragment, container, false)

        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)!!
        val user_id = sharedPref.getLong("user_id", -1)

        val user_info_layout = view.findViewById<View>(R.id.worker_info)!!
        bind_user_info(user_info_layout, user_id)


        return view
    }

    private fun bind_user_info(userInfoLayout: View, user_id: Long) {

        val user = UsersService().getTemporaryWorker(user_id)

        if(user != null) {
            val worker_name = userInfoLayout.findViewById<TextView>(R.id.interim_prenom)
            val worker_last_name = userInfoLayout.findViewById<TextView>(R.id.interim_last_name)
            val worker_email = userInfoLayout.findViewById<TextView>(R.id.interim_email)
            val worker_phone = userInfoLayout.findViewById<TextView>(R.id.interim_phone)
            val worker_birthday = userInfoLayout.findViewById<TextView>(R.id.interim_birthday)

            worker_name.text = user.getFirstName()
            worker_last_name.text = user.getLastName()
            worker_email.text = user.getEmail()
            worker_phone.text = user.getPhone()
            worker_birthday.text = user.getBirthday()
        }

    }
}