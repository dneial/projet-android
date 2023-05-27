package com.example.interim.ui.compte

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.models.TemporaryWorker
import com.example.interim.services.UsersService


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

        val confirm_button = view.findViewById<View>(R.id.confirm_button)
        confirm_button.setOnClickListener {
            editWorker(view)
        }

        bind_info(view)

        return view
    }

    private fun bind_info(view: View?) {
        view?.findViewById<EditText>(R.id.worker_prenom_edit)?.setText(user.getFirstName())
        view?.findViewById<EditText>(R.id.worker_nom_edit)?.setText(user.getLastName())
        view?.findViewById<EditText>(R.id.worker_email_edit)?.setText(user.getEmail())
        view?.findViewById<EditText>(R.id.worker_phone_edit)?.setText(user.getEmail())
        view?.findViewById<EditText>(R.id.worker_ville_edit)?.setText(user.getCity())
        view?.findViewById<EditText>(R.id.worker_nationality_edit)?.setText(user.getNationality())
        view?.findViewById<EditText>(R.id.worker_anniversaire_edit)?.setText(user.getBirthday())
    }

    private fun editWorker(view: View?) {
        val first_name = view?.findViewById<EditText>(R.id.worker_prenom_edit)?.text.toString()
        val last_name = view?.findViewById<EditText>(R.id.worker_nom_edit)?.text.toString()
        val email = view?.findViewById<EditText>(R.id.worker_email_edit)?.text.toString()
        val phone = view?.findViewById<EditText>(R.id.worker_phone_edit)?.text.toString()
        val ville = view?.findViewById<EditText>(R.id.worker_ville_edit)?.text.toString()
        val nationality = view?.findViewById<EditText>(R.id.worker_nationality_edit)?.text.toString()
        val birthday = view?.findViewById<EditText>(R.id.worker_anniversaire_edit)?.text.toString()

        user.setFirstName(first_name)
        user.setLastName(last_name)
        user.setEmail(email)
        user.setPhone(phone)
        user.setCity(ville)
        user.setNationality(nationality)
        user.setBirthday(birthday)

        UsersService().updateTemporaryWorker(user)
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
            callback.onWorkerActionTriggered()
        }
    }

}