package com.example.interim.ui.connection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.database.UsersService
import com.example.interim.models.User

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
        if(selectedFragment != null) requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            selectedFragment!!
        ).commit()


        var connectButton = view.findViewById<Button>(R.id.connectionButton)

        connectButton.setOnClickListener {
            signUp(view, selectedId)
        }
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



    private fun signUp(view: View, selectedId: Int) {
        Log.d("signup", selectedId.toString())
        when(selectedId){
            R.id.signUp_Interim -> {
                signUpInterim(view)
            }
            R.id.signUp_Employer -> {
                signUpEmployer(view)
            }
            R.id.signUp_Agency -> {
                signUpAgency(view)
            }
        }
    }

    private fun signUpAgency(view: View?) {

    }

    private fun signUpEmployer(view: View?) {

    }

    private fun signUpInterim(view: View) {
        Log.d("signUpInterim", "signUpInterim")
        val lastName = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.signUpEditTextLastName)
        val firstName = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.signUpEditTextFirstName)
        val email = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.signUpEditTextEmail)
        val password = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.signUpEditTextPassword)
        val phone = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.signUpEditTextPhone)
        val birthday = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.signUpEditTextBirthday)

        val user = User(
            lastName.text.toString(),
            firstName.text.toString(),
            email.text.toString(),
            password.text.toString(),
            phone.text.toString(),
            birthday.text.toString()
        )

        val userService = UsersService()
        userService.create(user)
        Log.d("user created", userService.get(user.email, user.password).toString())


    }
}