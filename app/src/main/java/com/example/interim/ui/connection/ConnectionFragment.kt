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
import com.example.interim.services.UsersService
import com.example.interim.models.TemporaryWorker
import com.example.interim.models.User
import com.example.interim.ui.LocationActivity

class ConnectionFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val signupButton = view.findViewById<Button>(R.id.signUpButton)

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


        val anonymous = view.findViewById<Button>(R.id.anonymousLoginButton)

        anonymous.setOnClickListener{

            val intent = Intent(activity, LocationActivity::class.java)
            startActivity(intent)
        }


        return view

    }

    private fun signIn(view: View?) {
        val usersService = UsersService()

        val email = view?.findViewById<TextView>(R.id.emailEditText)?.text.toString()
        val password = view?.findViewById<TextView>(R.id.passwordEditText)?.text.toString()
        val warning = view?.findViewById<TextView>(R.id.identificationWarningTextView)

        if(email == "" || password == "") {
            warning?.visibility = View.VISIBLE
            Log.d("ConnectionFragment", "email or password empty")
        } else {
            val user: User? = usersService.signIn(email, password)
            if(user != null){
                warning?.visibility = View.GONE
                saveSession(user)
                val intent = Intent(activity, LocationActivity::class.java)
                startActivity(intent)
            } else {
                warning?.visibility = View.VISIBLE
            }

        }
    }

    private fun saveSession(user: User){
        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE) ?: return
        val expirationTime = System.currentTimeMillis() + 864000000

        with (sharedPref.edit()) {
            putLong("user_id", user.getId())
            putString("user_role", user.getRole())
            putLong("expirationTime", expirationTime)
            commit()
        }
    }

}