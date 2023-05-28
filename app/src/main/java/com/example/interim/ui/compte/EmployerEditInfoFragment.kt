package com.example.interim.ui.compte

import android.content.ContentValues
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
import com.example.interim.models.Employer
import com.example.interim.models.TemporaryWorker
import com.example.interim.services.UsersService
import java.util.regex.Pattern


class EmployerEditInfoFragment(): Fragment() {

    interface NestedFragmentCallback {
        fun onEmployerActionTriggered()
    }

    private lateinit var user: Employer
    private lateinit var callback: NestedFragmentCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.employer_edit_fragment, container, false)

        val user_id = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getLong("user_id", -1)
        user = UsersService().getEmployer(user_id!!)!!

        val confirm_button = view.findViewById<View>(R.id.confirm_button)
        confirm_button.setOnClickListener {
            editWorker(view)
        }

        bind_info(view)

        return view
    }

    private fun bind_info(view: View?) {
        view?.findViewById<EditText>(R.id.company_name_edit)?.setText(user.getName())
        view?.findViewById<EditText>(R.id.company_metier_edit)?.setText(user.getService())
        view?.findViewById<EditText>(R.id.company_sous_metier_edit)?.setText(user.getSubService())
        view?.findViewById<EditText>(R.id.company_siret_edit)?.setText(user.getSIRET())
        view?.findViewById<EditText>(R.id.company_name1_edit)?.setText(user.getContact())
        view?.findViewById<EditText>(R.id.company_email1_edit)?.setText(user.getEmail())
        view?.findViewById<EditText>(R.id.company_phone1_edit)?.setText(user.getPhone())
        view?.findViewById<EditText>(R.id.company_name2_edit)?.setText(user.getSubContact())
        view?.findViewById<EditText>(R.id.company_email2_edit)?.setText(user.getSubEmail())
        view?.findViewById<EditText>(R.id.company_phone2_edit)?.setText(user.getSubPhone())
        view?.findViewById<EditText>(R.id.company_address_edit)?.setText(user.getAddress())
        view?.findViewById<EditText>(R.id.company_comment_edit)?.setText(user.getCommentary())
    }

    private fun editWorker(view: View?) {
        val company_name = view?.findViewById<TextView>(R.id.company_name_edit)?.text.toString()
        val company_service = view?.findViewById<TextView>(R.id.company_metier_edit)?.text.toString()
        val company_sub_service = view?.findViewById<TextView>(R.id.company_sous_metier_edit)?.text.toString()
        val company_siret = view?.findViewById<TextView>(R.id.company_siret_edit)?.text.toString()
        val employer1_name = view?.findViewById<TextView>(R.id.company_name1_edit)?.text.toString()
        val employer1_email = view?.findViewById<TextView>(R.id.company_email1_edit)?.text.toString()
        val employer1_phone = view?.findViewById<TextView>(R.id.company_phone1_edit)?.text.toString()
        val employer2_name = view?.findViewById<TextView>(R.id.company_name2_edit)?.text.toString()
        val employer2_email = view?.findViewById<TextView>(R.id.company_email2_edit)?.text.toString()
        val employer2_phone = view?.findViewById<TextView>(R.id.company_phone2_edit)?.text.toString()
        val company_address = view?.findViewById<TextView>(R.id.company_address_edit)?.text.toString()
        val company_commentary = view?.findViewById<TextView>(R.id.company_comment_edit)?.text.toString()

        if(checkInfo(view!!, employer1_email)) {
            user.setName(company_name)
            user.setService(company_service)
            user.setSubService(company_sub_service)
            user.setSIRET(company_siret)
            user.setContact(employer1_name)
            user.setEmail(employer1_email)
            user.setPhone(employer1_phone)
            user.setSubContact(employer2_name)
            user.setSubEmail(employer2_email)
            user.setSubPhone(employer2_phone)
            user.setAddress(company_address)
            user.setCommentary(company_commentary)

            UsersService().updateEmployer(user)
            Toast.makeText(context, "Informations mises à jour", Toast.LENGTH_SHORT).show()
            triggerActionInParentFragment()
        }
    }

    private fun checkInfo(view: View, currentEmail: String): Boolean{
        var correct : Boolean = true

        val name = view.findViewById<EditText>(R.id.company_name_edit)
        val service = view.findViewById<EditText>(R.id.company_metier_edit)
        val subService = view.findViewById<EditText>(R.id.company_sous_metier_edit)
        val SIRET = view.findViewById<EditText>(R.id.company_siret_edit)
        val contact = view.findViewById<EditText>(R.id.company_name1_edit)
        val subContact = view.findViewById<EditText>(R.id.company_name2_edit)
        val email = view.findViewById<EditText>(R.id.company_email1_edit)
        val secondEmail = view.findViewById<EditText>(R.id.company_email2_edit)
        val phone = view.findViewById<EditText>(R.id.company_phone1_edit)
        val subPhone = view.findViewById<EditText>(R.id.company_phone2_edit)
        val address = view.findViewById<EditText>(R.id.company_address_edit)

        val nameWarning = view.findViewById<TextView>(R.id.company_name_error)
        val serviceWarning = view.findViewById<TextView>(R.id.company_metier_error)
        val subServiceWarning = view.findViewById<TextView>(R.id.company_sous_metier_error)
        val SIRETWarning = view.findViewById<TextView>(R.id.company_siret_error)
        val contactWarning = view.findViewById<TextView>(R.id.company_name1_error)
        val subContactWarning = view.findViewById<TextView>(R.id.company_name2_error)
        val emailWarning = view.findViewById<TextView>(R.id.company_email1_error)
        val secondEmailWarning = view.findViewById<TextView>(R.id.company_email2_error)
        val phoneWarning = view.findViewById<TextView>(R.id.company_phone1_error)
        val subPhoneWarning = view.findViewById<TextView>(R.id.company_phone2_error)
        val addressWarning = view.findViewById<TextView>(R.id.company_address_error)

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

        if (SIRET.text.toString() == "" || !siretPattern.matcher(SIRET.text.toString()).matches()) {
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

        if (email.text.toString() == "" || !emailPattern.matcher(email.text.toString()).matches()) {
            email.setBackgroundResource(R.drawable.outline_warning)
            emailWarning.setText(R.string.warning_form)
            emailWarning.visibility = View.VISIBLE
            correct = false
        } else if (currentEmail != email.text.toString() && !UsersService().checkEmailUnique(email.text.toString())) {
            email.setBackgroundResource(R.drawable.outline_warning)
            emailWarning.setText(R.string.warning_mail)
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

        if (subPhone.text.toString() != "" && !phonePattern.matcher(subPhone.text.toString()).matches()){
            subPhone.setBackgroundResource(R.drawable.outline_warning)
            subPhoneWarning.visibility = View.VISIBLE
            correct = false
        } else { subPhoneWarning.visibility = View.GONE
            subPhone.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        if (address.text.toString() == "" || !addressPattern.matcher(address.text.toString()).matches()) {
            address.setBackgroundResource(R.drawable.outline_warning)
            addressWarning.visibility = View.VISIBLE
            correct = false
        } else { addressWarning.visibility = View.GONE
            address.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

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
            callback.onEmployerActionTriggered()
        }
    }



}