package com.example.interim.ui.connection

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

class ConnectionFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val signupButton = view.findViewById<TextView>(R.id.signUpTextView)

        signupButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.connectionFragmentContainer, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }

        val signin = view.findViewById<Button>(R.id.connectionButton)

        signin.setOnClickListener{
            signIn(view)
        }

        return view

    }

    private fun signIn(view: View?) {
        val email = view?.findViewById<TextView>(R.id.emailEditText)?.text.toString()
        val password = view?.findViewById<TextView>(R.id.passwordEditText)?.text.toString()

        if(email == "" || password == "") {
            Log.d("ConnectionFragment", "email or password empty")
        } else {
            Log.d("ConnectionFragment", "email: $email, password: $password")
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }


}