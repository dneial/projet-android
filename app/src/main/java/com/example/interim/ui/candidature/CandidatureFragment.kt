package com.example.interim.ui.candidature

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.database.CandidatureService
import com.example.interim.services.UsersService
import com.example.interim.models.Candidature
import com.example.interim.models.Offre
import com.example.interim.models.TemporaryWorker

class CandidatureFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_candidature, container, false)
        val candidature_id = arguments?.getLong("candidature_id")

        val candidatureService = CandidatureService()
        val pair: Pair<Offre, Candidature> = candidatureService.getOffreByCandidatureId(candidature_id!!)
        val offre = pair.first
        val candidature = pair.second

        Log.d("status",candidature.status)
        bind_view(view, offre, candidature)

        return view
    }

    private fun bind_view(view: View, offre: Offre, candidature: Candidature) {
        val title = view.findViewById<android.widget.TextView>(R.id.offre_title)
        title.text = offre.title

        val user_id = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getLong("user_id", -1)
        val user: TemporaryWorker? = UsersService().getTemporaryWorker(user_id!!)

        if(user != null) {
            view.findViewById<EditText>(R.id.candidature_prenom_edit)?.setText(user.getFirstName())
            view.findViewById<EditText>(R.id.candidature_nom_edit)?.setText(user.getLastName())
            view.findViewById<EditText>(R.id.candidature_email_edit)?.setText(user.getEmail())
            view.findViewById<EditText>(R.id.candidature_phone_edit)?.setText(user.getPhone())
            view.findViewById<EditText>(R.id.candidature_ville_edit)?.setText(user.getCity())
            view.findViewById<EditText>(R.id.candidature_nationality_edit)?.setText(user.getNationality())
            view.findViewById<EditText>(R.id.candidature_anniversaire_edit)?.setText(user.getBirthday())
        }


        val description = view.findViewById<android.widget.TextView>(R.id.offre_desc)
        description.text = offre.description

        val start_date = view.findViewById<android.widget.TextView>(R.id.offre_date)
        start_date.text = offre.date_debut + " - " + offre.date_fin

        val remuneration = view.findViewById<android.widget.TextView>(R.id.offre_remuneration)
        remuneration.text = offre.remuneration.toString() + "€/h"

        val date = view.findViewById<android.widget.TextView>(R.id.dateCandidature)
        date.text = candidature.date

        val status = view.findViewById<android.widget.TextView>(R.id.etatCandidature)
        status.text = candidature.status
    }
}
