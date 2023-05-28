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
import com.example.interim.models.TemporaryWorker

class CandidatureFragment : Fragment() {

    private lateinit var fragment: Fragment

    private lateinit var candidature: Candidature

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_candidature, container, false)

        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)!!
        val user_role =  sharedPref.getString("user_role", "worker")

        val candidature_id = arguments?.getLong("candidature_id")!!

        val candidatureService = CandidatureService()

        candidature = candidatureService.getCandidature(candidature_id!!)

        bind_view(view)

        when(user_role){
            "worker" -> {
                fragment = UserCandidatureFragment()
            }
            "employer" -> {
                fragment = EmployerCandidatureFragment()
            }
        }
        fragment.arguments = arguments
        val fragmentContainer = view.findViewById<ViewGroup>(R.id.fragment_container)
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(fragmentContainer.id, fragment)
        fragmentTransaction.commit()

        return view
    }


    private fun bind_view(view: View) {
        val title = view.findViewById<android.widget.TextView>(R.id.offre_title)
        val offre = candidature.offre
        title.text = offre.title

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