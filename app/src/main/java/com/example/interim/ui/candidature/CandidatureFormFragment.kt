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
import androidx.navigation.fragment.findNavController
import com.example.interim.R
import com.example.interim.database.CandidatureService
import com.example.interim.database.OffreService
import com.example.interim.services.UsersService
import com.example.interim.models.Candidature
import com.example.interim.models.TemporaryWorker
import java.text.SimpleDateFormat
import java.util.Date

class CandidatureFormFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_candidature_form, container, false)


        val offre_id = arguments?.getLong("offre_id")!!


        val postulerButton = view.findViewById<Button>(R.id.candidature_button)
        postulerButton.setOnClickListener { postuler(offre_id) }

        bind_info(view)
        
        return view
    }

    private fun bind_info(view: View?) {
        val user_id = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getLong("user_id", -1)
        val user: TemporaryWorker? = UsersService().getTemporaryWorker(user_id!!)

        if(user != null) {
            view?.findViewById<EditText>(R.id.candidature_prenom_edit)?.setText(user.getFirstName())
            view?.findViewById<EditText>(R.id.candidature_nom_edit)?.setText(user.getLastName())
            view?.findViewById<EditText>(R.id.candidature_email_edit)?.setText(user.getEmail())
            view?.findViewById<EditText>(R.id.candidature_phone_edit)?.setText(user.getEmail())
            view?.findViewById<EditText>(R.id.candidature_ville_edit)?.setText(user.getCity())
            view?.findViewById<EditText>(R.id.candidature_nationality_edit)?.setText(user.getNationality())
            view?.findViewById<EditText>(R.id.candidature_anniversaire_edit)?.setText(user.getBirthday())
        }
    }

    private fun postuler(offreId: Long) {


        val Date = Date()
        val dateFormated = SimpleDateFormat("yyyy-MM-dd").format(Date)
        // get date in format 'yyyy-mm-dd'


        Log.d("Candidature", "Date: $dateFormated")


        val user_id =
            activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getLong("user_id", 0)

        val offre = OffreService().getOffre(offreId)
        val user = UsersService().getTemporaryWorker(user_id!!)

        var candidature = Candidature(
            0,
            offre!!,
            user!!,
            dateFormated,
            "En attente"
        )

        candidature = CandidatureService().create(candidature)

        val navController = findNavController()

        val fragInStack = navController.popBackStack(R.id.navigation_candidature, false)


        if(fragInStack) {
            Log.d("Candidature", "Popped candidature from back stack")
            navController.navigate(
                R.id.action_navigation_candidature_to_navigation_offres,
                Bundle().apply {
                    putLong("offre_id", offre.id)
                }
            )
        } else {
            Log.d("Candidature", "not popped")
            navController.navigate(
                R.id.action_navigation_candidature_to_navigation_offres,
                Bundle().apply {
                    putLong("offre_id", offre.id)
                }

            )
        }
    }
}