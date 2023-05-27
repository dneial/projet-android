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
import com.example.interim.models.Employer
import com.example.interim.models.TemporaryWorker
import com.example.interim.services.UsersService


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