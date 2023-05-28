package com.example.interim.ui.compte

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.models.TemporaryWorker
import com.example.interim.services.UsersService
import java.util.regex.Pattern


class WorkerEditInfoFragment(): Fragment() {

    interface NestedFragmentCallback {
        fun onWorkerActionTriggered()
    }

    private lateinit var user: TemporaryWorker
    private lateinit var callback: NestedFragmentCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.worker_edit_fragment, container, false)

        val user_id = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getLong("user_id", -1)
        user = UsersService().getTemporaryWorker(user_id!!)!!

        bind_info(view)

        val confirm_button = view.findViewById<View>(R.id.confirm_button)
        confirm_button.setOnClickListener {
            editWorker(view)
        }


        return view
    }

    private fun bind_info(view: View?) {
        view?.findViewById<EditText>(R.id.worker_prenom_edit)?.setText(user.getFirstName())
        view?.findViewById<EditText>(R.id.worker_nom_edit)?.setText(user.getLastName())
        view?.findViewById<EditText>(R.id.worker_email_edit)?.setText(user.getEmail())
        view?.findViewById<EditText>(R.id.worker_phone_edit)?.setText(user.getPhone())
        view?.findViewById<EditText>(R.id.worker_ville_edit)?.setText(user.getCity())
        view?.findViewById<EditText>(R.id.worker_nationality_edit)?.setText(user.getNationality())
        view?.findViewById<EditText>(R.id.worker_anniversaire_edit)?.setText(user.getBirthday())
        view?.findViewById<EditText>(R.id.worker_commentaire_edit)?.setText(user.getCommentary())
    }

    private fun editWorker(view: View?) {
        val first_name = view?.findViewById<EditText>(R.id.worker_prenom_edit)?.text.toString()
        val last_name = view?.findViewById<EditText>(R.id.worker_nom_edit)?.text.toString()
        val email = view?.findViewById<EditText>(R.id.worker_email_edit)?.text.toString()
        val phone = view?.findViewById<EditText>(R.id.worker_phone_edit)?.text.toString()
        val ville = view?.findViewById<EditText>(R.id.worker_ville_edit)?.text.toString()
        val nationality = view?.findViewById<EditText>(R.id.worker_nationality_edit)?.text.toString()
        val birthday = view?.findViewById<EditText>(R.id.worker_anniversaire_edit)?.text.toString()
        val commentary = view?.findViewById<EditText>(R.id.worker_commentaire_edit)?.text.toString()

        if (checkSignUpInterim(view!!)){
            user.setFirstName(first_name)
            user.setLastName(last_name)
            user.setEmail(email)
            user.setPhone(phone)
            user.setCity(ville)
            user.setNationality(nationality)
            user.setBirthday(birthday)
            user.setCommentary(commentary)

            UsersService().updateTemporaryWorker(user)
            Toast.makeText(context, "Informations mises à jour", Toast.LENGTH_SHORT).show()
            triggerActionInParentFragment()
        }
    }

    private fun checkSignUpInterim(view: View) : Boolean{
        var correct : Boolean = true
        val lastName = view.findViewById<EditText>(R.id.worker_nom_edit)
        val firstName = view.findViewById<EditText>(R.id.worker_prenom_edit)
        val email = view.findViewById<EditText>(R.id.worker_email_edit)
        val phone = view.findViewById<EditText>(R.id.worker_phone_edit)
        val birthday = view.findViewById<EditText>(R.id.worker_anniversaire_edit)
        val city = view.findViewById<EditText>(R.id.worker_ville_edit)
        val nationality = view.findViewById<EditText>(R.id.worker_nationality_edit)

        val lastNameWarning = view.findViewById<TextView>(R.id.worker_nom_warning)
        val firstNameWarning = view.findViewById<TextView>(R.id.worker_prenom_warning)
        val emailWarning = view.findViewById<TextView>(R.id.worker_email_warning)
        val phoneWarning = view.findViewById<TextView>(R.id.worker_phone_warning)
        val birthdayWarning = view.findViewById<TextView>(R.id.worker_anniversaire_warning)
        val cityWarning = view.findViewById<TextView>(R.id.worker_ville_warning)
        val nationalityWarning = view.findViewById<TextView>(R.id.worker_nationality_warning)

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
            } else if (email.text.toString() != user.getEmail() && !UsersService().checkEmailUnique(email.text.toString())) {
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        if (parent != null && parent is NestedFragmentCallback) {
            callback = parent
        } else {
            throw RuntimeException("$parent must implement NestedFragmentCallback")
        }
    }

    // Call this method when you want to trigger the change in the parent fragment
    private fun triggerActionInParentFragment() {
        if (callback != null) {
            callback.onWorkerActionTriggered()
        }
    }

}