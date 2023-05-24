package com.example.interim.ui.connection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.database.UsersService
import com.example.interim.models.User
import java.util.regex.Pattern

class SignUpFragment: Fragment(), OnCheckedChangeListener{

    private var selectedFragment: Fragment? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.example.interim.R.layout.fragment_signup, container, false)

        val radioGroup: RadioGroup = view.findViewById<RadioGroup>(R.id.radio_group)

        radioGroup.setOnCheckedChangeListener(this)

        return view
    }


    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.signUp_Interim -> {
                Log.d("button selected", "interim")

                selectedFragment = SignUpInterimFragment()
            }
            R.id.signUp_Employer -> {
                Log.d("button selected", "employeur")

                selectedFragment = SignUpEmployerFragment()
            }
            R.id.signUp_Agency -> {
                Log.d("button selected", "agency")

                selectedFragment = SignUpEmployerFragment()
            }
        }

        if(selectedFragment != null)
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            selectedFragment!!
        ).commit()

    }
}