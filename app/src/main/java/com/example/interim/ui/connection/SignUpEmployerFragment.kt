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
import java.util.regex.Pattern


class SignUpEmployerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up_employer, container, false)

        val button = view.findViewById<Button>(R.id.employerSignUpButton)

        button.setOnClickListener { signUpEmployer(view) }

        return view
    }

    private fun signUpEmployer(view: View){
        val name = view.findViewById<EditText>(R.id.employerSignUpEditTextCompanyName)
        val service = view.findViewById<EditText>(R.id.employerSignUpEditTextDepartmentName)
        val subService = view.findViewById<EditText>(R.id.employerSignUpEditTextSubDepartmentName)
        val SIRET = view.findViewById<EditText>(R.id.employerSignUpEditTextEntityNumber)
        val contact = view.findViewById<EditText>(R.id.employerSignUpEditTextContactPerson1)
        val subContact = view.findViewById<EditText>(R.id.employerSignUpEditTextContactPerson2)
        val email = view.findViewById<EditText>(R.id.employerSignUpEditTextEmail1)
        val secondEmail = view.findViewById<EditText>(R.id.employerSignUpEditTextEmail2)
        val phone = view.findViewById<EditText>(R.id.employerSignUpEditTextPhone1)
        val subPhone = view.findViewById<EditText>(R.id.employerSignUpEditTextPhone2)
        val address = view.findViewById<EditText>(R.id.employerSignUpEditTextAddress)
        val password = view.findViewById<EditText>(R.id.employerSignUpEditTextPassword)
        val commentary = view.findViewById<EditText>(R.id.employerSignUpEditTextCommentary)

        if (checkSignUpEmployer(view)){
            val user = Bundle()
            user.putString("name", name.text.toString())
            user.putString("service", service.text.toString())
            user.putString("subService", subService.text.toString())
            user.putString("SIRET", SIRET.text.toString())
            user.putString("contact", contact.text.toString())
            user.putString("subContact", subContact.text.toString())
            user.putString("email", email.text.toString())
            user.putString("secondEmail", secondEmail.text.toString())
            user.putString("phone", phone.text.toString())
            user.putString("subPhone", subPhone.text.toString())
            user.putString("address", address.text.toString())
            user.putString("password", password.text.toString())
            user.putString("commentary", commentary.text.toString())

            val targetFragment = SignUpAuthentification()
            targetFragment.arguments = user

            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.connectionFragmentContainer, targetFragment).commit()

            Log.d("signUpEmployer", "Send authentification")
        } else {
            Log.d("signUpEmployer", "Inscription invalide")
        }
    }

    private fun checkSignUpEmployer(view: View): Boolean{
        var correct : Boolean = true
        val name = view.findViewById<EditText>(R.id.employerSignUpEditTextCompanyName)
        val service = view.findViewById<EditText>(R.id.employerSignUpEditTextDepartmentName)
        val subService = view.findViewById<EditText>(R.id.employerSignUpEditTextSubDepartmentName)
        val SIRET = view.findViewById<EditText>(R.id.employerSignUpEditTextEntityNumber)
        val contact = view.findViewById<EditText>(R.id.employerSignUpEditTextContactPerson1)
        val subContact = view.findViewById<EditText>(R.id.employerSignUpEditTextContactPerson2)
        val email = view.findViewById<EditText>(R.id.employerSignUpEditTextEmail1)
        val secondEmail = view.findViewById<EditText>(R.id.employerSignUpEditTextEmail2)
        val phone = view.findViewById<EditText>(R.id.employerSignUpEditTextPhone1)
        val subPhone = view.findViewById<EditText>(R.id.employerSignUpEditTextPhone2)
        val address = view.findViewById<EditText>(R.id.employerSignUpEditTextAddress)
        val password = view.findViewById<EditText>(R.id.employerSignUpEditTextPassword)

        val nameWarning = view.findViewById<TextView>(R.id.employerSignUpTextViewWarningCompanyName)
        val serviceWarning = view.findViewById<TextView>(R.id.employerSignUpTextViewWarningDepartmentName)
        val subServiceWarning = view.findViewById<TextView>(R.id.employerSignUpTextViewWarningSubDepartmentName)
        val SIRETWarning = view.findViewById<TextView>(R.id.employerSignUpTextViewWarningEntityNumber)
        val contactWarning = view.findViewById<TextView>(R.id.employerSignUpTextViewWarningContactPerson1)
        val subContactWarning = view.findViewById<TextView>(R.id.employerSignUpTextViewWarningContactPerson2)
        val emailWarning = view.findViewById<TextView>(R.id.employerSignUpTextViewWarningEmail1)
        val secondEmailWarning = view.findViewById<TextView>(R.id.employerSignUpTextViewWarningEmail2)
        val phoneWarning = view.findViewById<TextView>(R.id.employerSignUpTextViewWarningPhone1)
        val subPhoneWarning = view.findViewById<TextView>(R.id.employerSignUpTextViewWarningPhone2)
        val addressWarning = view.findViewById<TextView>(R.id.employerSignUpTextViewWarningAddress)
        val passwordWarning = view.findViewById<TextView>(R.id.employerSignUpTextViewWarningPassword)

        val textPattern = Pattern.compile("^[\\p{L}\\s'-]+\$")
        val emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
        val siretPattern = Pattern.compile("^\\d{14}$")
        val addressPattern = Pattern.compile("^\\d*[\\w\\s',.-]+ \\d+ [\\w\\s',.-]+$")
        val phonePattern = Pattern.compile("^\\d{10}$")


        if (name.text.toString() == "") {
            name.setBackgroundResource(R.drawable.outline_warning)
            nameWarning.visibility = View.VISIBLE
            correct = false
        } else { nameWarning.visibility = View.GONE
            name.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        if (service.text.toString() != "" && !textPattern.matcher(service.text.toString()).matches()){
            service.setBackgroundResource(R.drawable.outline_warning)
            serviceWarning.visibility = View.VISIBLE
            correct = false
        } else { serviceWarning.visibility = View.GONE
            service.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        if (subService.text.toString() != "" && !textPattern.matcher(subService.text.toString()).matches()){
            subService.setBackgroundResource(R.drawable.outline_warning)
            subServiceWarning.visibility = View.VISIBLE
            correct = false
        } else { subServiceWarning.visibility = View.GONE
            subService.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        if (SIRET.text.toString() == "") {
            SIRET.setBackgroundResource(R.drawable.outline_warning)
            SIRETWarning.visibility = View.VISIBLE
            correct = false
        } else if (!siretPattern.matcher(SIRET.text.toString()).matches()) {
            SIRET.setBackgroundResource(R.drawable.outline_warning)
            SIRETWarning.visibility = View.VISIBLE
            correct = false
        } else { SIRETWarning.visibility = View.GONE
            SIRET.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        if (contact.text.toString() != "" && !textPattern.matcher(contact.text.toString()).matches()){
            contact.setBackgroundResource(R.drawable.outline_warning)
            contactWarning.visibility = View.VISIBLE
            correct = false
        } else { contactWarning.visibility = View.GONE
            contact.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        if (subContact.text.toString() != "" && !textPattern.matcher(subContact.text.toString()).matches()){
            subContact.setBackgroundResource(R.drawable.outline_warning)
            subContactWarning.visibility = View.VISIBLE
            correct = false
        } else { subContactWarning.visibility = View.GONE
            subContact.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        if (email.text.toString() == "") {
            email.setBackgroundResource(R.drawable.outline_warning)
            emailWarning.visibility = View.VISIBLE
            correct = false
        } else if (!emailPattern.matcher(email.text.toString()).matches()) {
            email.setBackgroundResource(R.drawable.outline_warning)
            emailWarning.visibility = View.VISIBLE
            correct = false
        } else { emailWarning.visibility = View.GONE
            email.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        if (secondEmail.text.toString() != "" && !emailPattern.matcher(secondEmail.text.toString()).matches()) {
            secondEmail.setBackgroundResource(R.drawable.outline_warning)
            secondEmailWarning.visibility = View.VISIBLE
            correct = false
        } else { secondEmailWarning.visibility = View.GONE
            secondEmail.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        if (phone.text.toString() != "" && !phonePattern.matcher(phone.text.toString()).matches()){
            phone.setBackgroundResource(R.drawable.outline_warning)
            phoneWarning.visibility = View.VISIBLE
            correct = false
        } else { phoneWarning.visibility = View.GONE
            phone.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        if (subPhone.text.toString() != "" && !textPattern.matcher(subPhone.text.toString()).matches()){
            subPhone.setBackgroundResource(R.drawable.outline_warning)
            subPhoneWarning.visibility = View.VISIBLE
            correct = false
        } else { subPhoneWarning.visibility = View.GONE
            subPhone.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        if (address.text.toString() == "") {
            address.setBackgroundResource(R.drawable.outline_warning)
            addressWarning.visibility = View.VISIBLE
            correct = false
        } else if (!addressPattern.matcher(address.text.toString()).matches()) {
            addressWarning.visibility = View.VISIBLE
            address.setBackgroundResource(R.drawable.outline_warning)
            correct = false
        } else { addressWarning.visibility = View.GONE
            address.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        if (password.text.toString() == "") {
            password.setBackgroundResource(R.drawable.outline_warning)
            passwordWarning.visibility = View.VISIBLE
            correct = false
        } else { passwordWarning.visibility = View.GONE
            password.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        return correct
    }

}