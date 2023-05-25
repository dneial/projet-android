package com.example.interim.ui.connection

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.interim.R
import com.example.interim.database.UsersService
import com.example.interim.models.Employer
import com.example.interim.models.TemporaryWorker
import com.example.interim.models.User
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

        var user = generateUser()
        val role = arguments?.getString("type")

        var code = generateRandomCode(6)
        Log.d("code", code)
        if (user?.getEmail() != ""){
            sendMail(code, user!!.getEmail())
        } else { sendMessage(code, user!!.getPhone()) }

        view.findViewById<Button>(R.id.authSignUpButtonCancel).setOnClickListener {
            //Go page accueil
        }
        view.findViewById<Button>(R.id.authSignUpButtonConfirm).setOnClickListener {
            if (view.findViewById<EditText>(R.id.authSignUpEditTextCode).text.toString() == code){
                if (role == "TemporaryWorker")
                    UsersService().create(user as TemporaryWorker)
                //Go page accueil
            }
        }
        view.findViewById<Button>(R.id.authSignUpButtonResend).setOnClickListener {
            code = generateRandomCode(6)
            Log.d("code", code)
            if (user?.getEmail() != ""){
                sendMail(code, user!!.getEmail())
            } else { sendMessage(code, user!!.getPhone()) }
        }

        return view
    }

    private fun generateUser(): User {
        val user: User
        if (arguments?.getString("type") == "TemporaryWorker") {
            val lastName = arguments?.getString("lastName")
            val firstName = arguments?.getString("firstName")
            val email = arguments?.getString("email").toString()
            val password = arguments?.getString("password")
            val phone = arguments?.getString("phone").toString()
            val birthday = arguments?.getString("birthday")
            val nationality = arguments?.getString("nationality")
            val city = arguments?.getString("city")
            val commentary = arguments?.getString("commentary")

            user = TemporaryWorker(0, firstName!!, lastName!!, email!!, password!!,
                phone!!, birthday!!, nationality!!, city!!, commentary!!)
        } else {
            val name = arguments?.getString("name")
            val service = arguments?.getString("service")
            val subService = arguments?.getString("subService").toString()
            val SIRET = arguments?.getString("SIRET")
            val contact = arguments?.getString("contact").toString()
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
        return user!!
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
    }

    private fun sendMessage(code : String,  mail : String){
    }
}