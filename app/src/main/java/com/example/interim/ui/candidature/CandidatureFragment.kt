package com.example.interim.ui.candidature

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.database.CandidatureService
import com.example.interim.database.UsersService
import com.example.interim.models.Candidature
import java.util.Date

class CandidatureFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_candidature, container, false)


        val offre_id = arguments?.getLong("offre_id")!!


        val postulerButton = view.findViewById<Button>(R.id.candidature_button)
        postulerButton.setOnClickListener { postuler(offre_id) }

        bind_info(view)
        
        return view
    }

    private fun bind_info(view: View?) {
        val user_id = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getLong("user_id", -1)
        val user = UsersService().getUser(user_id!!)

        if(user != null) {
            view?.findViewById<EditText>(R.id.candidature_prenom_edit)?.setText(user.firstName)
            view?.findViewById<EditText>(R.id.candidature_nom_edit)?.setText(user.lastName)
            view?.findViewById<EditText>(R.id.candidature_email_edit)?.setText(user.email)
            view?.findViewById<EditText>(R.id.candidature_phone_edit)?.setText(user.phone)
            view?.findViewById<EditText>(R.id.candidature_ville_edit)?.setText(user.ville)
            view?.findViewById<EditText>(R.id.candidature_nationality_edit)?.setText(user.nationality)
            view?.findViewById<EditText>(R.id.candidature_anniversaire_edit)?.setText(user.birthday)

        }
    }

    private fun postuler(offreId: Long) {


        val Date = Date()
        val date = Date.toString().substring(0, 10)

        val user_id =
            activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getLong("user_id", 0)

        var candidature = Candidature(
            0,
            offreId,
            user_id!!,
            date,
            "En cours"
        )

        candidature = CandidatureService().create(candidature)

        Log.d("Candidature", "Candidature created: ${candidature.toString()}")
    }
}