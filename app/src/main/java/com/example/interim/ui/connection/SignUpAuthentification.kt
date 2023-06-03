package com.example.interim.ui.connection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.interim.MainActivity
import com.example.interim.R
import com.example.interim.services.UsersService
import com.example.interim.models.Employer
import com.example.interim.models.TemporaryWorker
import com.example.interim.models.User
import com.example.interim.ui.LocationActivity
import com.teamcreative.javamailapidemo.JavaMailAPI
import kotlin.random.Random

class SignUpAuthentification : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sign_up_authentification, container, false)
        val role = arguments?.getString("type")

        val intent = Intent(requireContext(), ConnectionActivity::class.java)

        val user = generateUser()

        var code = generateRandomCode(6)
        Log.d("code", code)
        if (user.getEmail() != ""){
            sendMail(code, user.getEmail())
        } else { sendMessage(code, user.getPhone()) }

        view.findViewById<Button>(R.id.authSignUpButtonCancel).setOnClickListener {
            startActivity(intent)
        }
        view.findViewById<Button>(R.id.authSignUpButtonConfirm).setOnClickListener {
            if (view.findViewById<EditText>(R.id.authSignUpEditTextCode).text.toString() == code){
                if (role == "TemporaryWorker")
                    UsersService().create(user as TemporaryWorker)
                else if (role == "Employer")
                    UsersService().create(user as Employer)
                else
                    Log.d("error", "role not found: $role")
                UsersService().signIn(user.getEmail(), user.getPassword())
                startActivity(intent)
            }
        }
        view.findViewById<Button>(R.id.authSignUpButtonResend).setOnClickListener {
            code = generateRandomCode(6)
            Log.d("code", code)
            if (user.getEmail() != ""){
                sendMail(code, user.getEmail())
            } else { sendMessage(code, user.getPhone()) }
        }

        return view
    }

    private fun generateUser(): User {
        val user: User
        if (arguments?.getString("type") == "TemporaryWorker") {
            val lastName = arguments?.getString("lastName")
            val firstName = arguments?.getString("firstName")
            val email = arguments?.getString("email")
            val password = arguments?.getString("password")
            val phone = arguments?.getString("phone")
            val birthday = arguments?.getString("birthday")
            val nationality = arguments?.getString("nationality")
            val city = arguments?.getString("city")
            val commentary = arguments?.getString("commentary")

            user = TemporaryWorker(0, firstName!!, lastName!!, email!!, password!!,
                phone!!, birthday!!, nationality!!, city!!, commentary!!)

        } else {
            val name = arguments?.getString("name")
            val service = arguments?.getString("service")
            val subService = arguments?.getString("subService")
            val SIRET = arguments?.getString("SIRET")
            val contact = arguments?.getString("contact")
            val subContact = arguments?.getString("subContact")
            val email = arguments?.getString("email")
            val secondEmail = arguments?.getString("secondEmail")
            val phone = arguments?.getString("phone")
            val subPhone= arguments?.getString("subPhone")
            val address = arguments?.getString("address")
            val commentary = arguments?.getString("commentary")
            val password = arguments?.getString("password")
            user = Employer(0, name!!, service!!, subService!!, SIRET!!, contact!!, subContact!!,
                email!!, secondEmail!!, phone!!, subPhone!!, address!!, commentary!!, password!!)
        }
        return user
    }

    private fun generateRandomCode(codeLength : Int): String {
        val stringBuilder = StringBuilder()

        repeat(codeLength) {
            val randomDigit = Random.nextInt(10)
            stringBuilder.append(randomDigit)
        }

        return stringBuilder.toString()
    }

    private fun sendMail(code : String,  mail : String){
        JavaMailAPI(requireContext(), mail, "Interim authentification", "Your code is $code").execute()
        Log.d("Authentification", "Email: $mail     Code: $code")
    }

    private fun sendMessage(code : String,  phone : String){
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