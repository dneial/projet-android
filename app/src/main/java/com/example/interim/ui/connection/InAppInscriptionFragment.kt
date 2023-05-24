package com.example.interim.ui.connection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.example.interim.R

class InAppInscriptionFragment: Fragment(), RadioGroup.OnCheckedChangeListener {

    private var selectedFragment: Fragment? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.example.interim.R.layout.fragment_signup, container, false)

        val radioGroup: RadioGroup = view.findViewById<RadioGroup>(R.id.radio_group)

        radioGroup.setOnCheckedChangeListener(this)

        val selectedId: Int = radioGroup.checkedRadioButtonId

        when(selectedId){
            R.id.signUp_Interim -> {
                selectedFragment = SignUpInterimFragment()
            }
            R.id.signUp_Employer -> {
                selectedFragment = SignUpEmployerFragment()
            }
            R.id.signUp_Agency -> {
                selectedFragment = SignUpEmployerFragment()
            }
        }
        if(selectedFragment != null) requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            selectedFragment!!
        ).commit()

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