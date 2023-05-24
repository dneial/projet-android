package com.example.interim.ui.compte

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.interim.MainActivity
import com.example.interim.R

class CompteFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_compte, container, false)

        val button = view.findViewById<View>(R.id.btnDeconnexion) as Button
        button.setOnClickListener {
            val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)
            with(sharedPref?.edit()) {
                this?.putLong("user_id", -1)
                this?.apply()
            }

        }

        return view
    }

}