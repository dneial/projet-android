package com.example.interim.ui.connection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.interim.MainActivity
import com.example.interim.R
import com.example.interim.database.UsersService
import com.example.interim.models.TemporaryWorker

class InAppConnectionFragment: Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.popup_login, container, false)


        val signin = view.findViewById<View>(R.id.connectionButton)
        signin.setOnClickListener {
            signIn(view)
        }

        val signup = view.findViewById<TextView>(R.id.signUpTextView)
        signup.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_inapp_connection_to_navigation_inapp_inscription)
        }
        return view
    }

    private fun signIn(view: View?) {
        val usersService = UsersService()

        val email = view?.findViewById<TextView>(R.id.emailEditText)?.text.toString()
        val password = view?.findViewById<TextView>(R.id.passwordEditText)?.text.toString()

        if(email == "" || password == "") {
            Log.d("ConnectionFragment", "email or password empty")
        } else {
            val temporaryWorker: TemporaryWorker? = usersService.signIn(email, password)
            if(temporaryWorker != null){
                saveSession(temporaryWorker)
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun saveSession(temporaryWorker: TemporaryWorker){

        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE) ?: return
        val expirationTime = System.currentTimeMillis() + 864000000

        with (sharedPref.edit()) {
            putLong("user_id", temporaryWorker.getId())
            putLong("expirationTime", expirationTime)
            commit()
        }
    }
}