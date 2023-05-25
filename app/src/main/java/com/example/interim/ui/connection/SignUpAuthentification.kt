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

        var user : User? = null
        var email = ""
        var phone = ""
        var role : String? = arguments?.getString("type")

        if (role == "TemporaryWorker") {
            val lastName = arguments?.getString("lastName")
            val firstName = arguments?.getString("firstName")
            email = arguments?.getString("email").toString()
            val password = arguments?.getString("password")
            phone = arguments?.getString("phone").toString()
            val birthday = arguments?.getString("birthday")
            val nationality = arguments?.getString("nationality")
            val city = arguments?.getString("city")
            val commentary = arguments?.getString("commentary")

            user = TemporaryWorker(0, firstName!!, lastName!!, email!!, password!!,
                phone!!, birthday!!, nationality!!, city!!, commentary!!)
        }

        val code = generateRandomCode(6)
        Log.d("code", code)
        if (email != ""){
            sendMail(code, email)
        } else { sendMessage(code, phone) }

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
            if (email != ""){
                sendMail(code, email)
            } else { sendMessage(code, phone) }
            it.isClickable = false
        }

        return view
    }

    fun generateRandomCode(codeLength : Int): String {
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