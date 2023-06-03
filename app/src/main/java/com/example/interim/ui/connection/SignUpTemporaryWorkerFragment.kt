package com.example.interim.ui.connection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.services.UsersService
import java.util.regex.Pattern


class SignUpTemporaryWorkerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up_temporary_worker, container, false)

        val button = view.findViewById<Button>(R.id.interimSignUpButton)

        button.setOnClickListener { signUpInterim(view) }

        return view
    }

    private fun signUpInterim(view: View){
        val lastName = view.findViewById<EditText>(R.id.interimSignUpEditTextFirstName)
        val firstName = view.findViewById<EditText>(R.id.interimSignUpEditTextLastName)
        val email = view.findViewById<EditText>(R.id.interimSignUpEditTextEmail)
        val password = view.findViewById<EditText>(R.id.interimSignUpEditTextPassword)
        val phone = view.findViewById<EditText>(R.id.interimSignUpEditTextPhone)
        val birthday = view.findViewById<EditText>(R.id.interimSignUpEditTextBirthday)
        val city = view.findViewById<EditText>(R.id.interimSignUpEditTextCity)
        val nationality = view.findViewById<EditText>(R.id.interimSignUpEditTextNationality)
        val commentary = view.findViewById<EditText>(R.id.interimSignUpEditTextCommentary)

        if (checkSignUpInterim(view)){
            val user = Bundle()
            user.putString("type", "TemporaryWorker")
            user.putString("lastName", lastName.text.toString())
            user.putString("firstName", firstName.text.toString())
            user.putString("email", email.text.toString())
            user.putString("password", password.text.toString())
            user.putString("phone", phone.text.toString())
            user.putString("birthday", birthday.text.toString())
            user.putString("city", city.text.toString())
            user.putString("nationality", nationality.text.toString())
            user.putString("commentary", commentary.text.toString())

            val targetFragment = SignUpAuthentification()
            targetFragment.arguments = user

            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.connectionFragmentContainer, targetFragment).commit()

            Log.d("signUpInterim", "Send authentification")
        } else {
            Log.d("signUpInterim", "Inscription invalide")
        }
    }

    private fun checkSignUpInterim(view: View) : Boolean{
        var correct : Boolean = true
        val lastName = view.findViewById<EditText>(R.id.interimSignUpEditTextFirstName)
        val firstName = view.findViewById<EditText>(R.id.interimSignUpEditTextLastName)
        val email = view.findViewById<EditText>(R.id.interimSignUpEditTextEmail)
        val password = view.findViewById<EditText>(R.id.interimSignUpEditTextPassword)
        val phone = view.findViewById<EditText>(R.id.interimSignUpEditTextPhone)
        val birthday = view.findViewById<EditText>(R.id.interimSignUpEditTextBirthday)
        val city = view.findViewById<EditText>(R.id.interimSignUpEditTextCity)
        val nationality = view.findViewById<EditText>(R.id.interimSignUpEditTextNationality)

        val lastNameWarning = view.findViewById<TextView>(R.id.interimSignUpTextViewWarningFirstName)
        val firstNameWarning = view.findViewById<TextView>(R.id.interimSignUpTextViewWarningLastName)
        val emailWarning = view.findViewById<TextView>(R.id.interimSignUpTextViewWarningEmail)
        val passwordWarning = view.findViewById<TextView>(R.id.interimSignUpTextViewWarningPassword)
        val phoneWarning = view.findViewById<TextView>(R.id.interimSignUpTextViewWarningPhone)
        val birthdayWarning = view.findViewById<TextView>(R.id.interimSignUpTextViewWarningBirthday)
        val cityWarning = view.findViewById<TextView>(R.id.interimSignUpTextViewWarningCity)
        val nationalityWarning = view.findViewById<TextView>(R.id.interimSignUpTextViewWarningNationality)

        var patternText = Pattern.compile("^[\\p{L}\\s'-]+\$")
        var patternEmail = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        var patternPhone = Pattern.compile("^[0-9]{10}$")
        var patternDate = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/((19|20)\\d\\d)\$")

        if (firstName.text.toString() == "" || !patternText.matcher(firstName.text.toString()).matches()){
            firstName.setBackgroundResource(R.drawable.outline_warning)
            firstNameWarning.visibility = View.VISIBLE
            correct = false
        } else {
            firstNameWarning.visibility = View.GONE
            firstName.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        }

        if (lastName.text.toString() == "" || !patternText.matcher(lastName.text.toString()).matches()){
            lastNameWarning.visibility = View.VISIBLE
            lastName.setBackgroundResource(R.drawable.outline_warning)
            correct = false
        } else {
            lastNameWarning.visibility = View.GONE
            lastName.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        }

        if (password.text.toString() == ""){
            password.setBackgroundResource(R.drawable.outline_warning)
            passwordWarning.visibility = View.VISIBLE
            correct = false
        } else {
            passwordWarning.visibility = View.GONE
            password.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        }

        if (city.text.toString() != "" && !patternText.matcher(city.text.toString()).matches()){
            city.setBackgroundResource(R.drawable.outline_warning)
            cityWarning.visibility = View.VISIBLE
        } else {
            cityWarning.visibility = View.GONE
            city.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        }

        if (nationality.text.toString() != "" && !patternText.matcher(nationality.text.toString()).matches()){
            nationality.setBackgroundResource(R.drawable.outline_warning)
            nationalityWarning.visibility = View.VISIBLE
        } else {
            nationalityWarning.visibility = View.GONE
            nationality.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        }

        if (email.text.toString() == "" && phone.text.toString() == ""){
            email.setBackgroundResource(R.drawable.outline_warning)
            phone.setBackgroundResource(R.drawable.outline_warning)
            emailWarning.visibility = View.VISIBLE
            phoneWarning.visibility = View.VISIBLE
            correct = false
        } else {

            if (email.text.toString() != "" && !patternEmail.matcher(email.text.toString()).matches()){
                email.setBackgroundResource(R.drawable.outline_warning)
                emailWarning.setText(R.string.warning_form)
                emailWarning.visibility = View.VISIBLE
                correct = false
            } else if (!UsersService().checkEmailUnique(email.text.toString())) {
                email.setBackgroundResource(R.drawable.outline_warning)
                emailWarning.setText(R.string.warning_mail)
                emailWarning.visibility = View.VISIBLE
                correct = false
            } else { emailWarning.visibility = View.GONE
                email.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

            if (phone.text.toString() != "" && !patternPhone.matcher(phone.text.toString()).matches()){
                phone.setBackgroundResource(R.drawable.outline_warning)
                phoneWarning.visibility = View.VISIBLE
                correct = false
            }else { phoneWarning.visibility = View.GONE
                phone.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }
        }

        if (birthday.text.toString() != "" && !patternDate.matcher(birthday.text.toString()).matches()){
            birthday.setBackgroundResource(R.drawable.outline_warning)
            birthdayWarning.visibility = View.VISIBLE
            correct = false
        } else { birthdayWarning.visibility = View.GONE
            birthday.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        return correct
    }
}
