package com.example.interim.ui.compte

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.interim.MainActivity
import com.example.interim.R
import com.example.interim.services.UsersService
import com.example.interim.ui.connection.ConnectionActivity

class CompteFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)!!
        val user_id = sharedPref.getLong("user_id", -1)

        if(user_id == -1L) {
            val intent = Intent(activity, ConnectionActivity::class.java)
            startActivity(intent)
        }

        val view = inflater.inflate(R.layout.fragment_compte, container, false)

        val button = view.findViewById<View>(R.id.btnDeconnexion) as Button
        button.setOnClickListener {
            with(sharedPref.edit()) {
                this?.putLong("user_id", -1)
                this?.apply()
            }

            val intent = Intent(activity, ConnectionActivity::class.java)
            startActivity(intent)

        }

        val user_info_layout = view.findViewById<View>(R.id.worker_info)!!
        bind_user_info(user_info_layout, user_id)

        return view
    }

    private fun bind_user_info(userInfoLayout: View, user_id: Long) {
        val user = UsersService().getTemporaryWorker(user_id)!!

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