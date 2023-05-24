package com.example.interim.ui.connection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.database.UsersService
import com.example.interim.models.User

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpInterimFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpInterimFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up_interim, container, false)

        val button = view.findViewById<View>(R.id.interimSignUpButtonSignIn) as Button

        button.setOnClickListener { signUpInterim(view) }

        return view
    }

    private fun signUpInterim(view: View) {
        val lastName = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.interimSignUpEditTextFirstName)
        val firstName = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.interimSignUpEditTextLastName)
        val email = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.interimSignUpEditTextEmail)
        val password = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.interimSignUpEditTextPassword)
        val phone = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.interimSignUpEditTextPhone)
        val birthday = view.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.interimSignUpEditTextBirthday)

        val user = User(
            0,
            lastName.text.toString(),
            firstName.text.toString(),
            email.text.toString(),
            password.text.toString(),
            phone.text.toString(),
            birthday.text.toString()
        )

        val userService = UsersService()
        userService.create(user)
        Log.d("user created", userService.signIn(user.email, user.password).toString())


    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUpInterimFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpInterimFragment().apply {

            }
    }
}