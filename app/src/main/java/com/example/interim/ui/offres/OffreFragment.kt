package com.example.interim.ui.offres

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.interim.R
import com.example.interim.database.CandidatureService
import com.example.interim.database.OffreService
import com.example.interim.databinding.FragmentOffreBinding
import com.example.interim.models.Candidature
import com.example.interim.models.Offre
import com.example.interim.ui.connection.ConnectionActivity
import java.util.Date

class OffreFragment : Fragment() {

    private var _binding: FragmentOffreBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentOffreBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val offreId: Long = arguments?.getLong("offre_id")!!
        val offre = OffreService().getOffre(offreId)!!

        bind_offre(root, offre)

        val enregistrer = root.findViewById<Button>(R.id.saveButton)
        enregistrer.setOnClickListener {
            enregistrer_offre(offre)
        }

        val postuler = root.findViewById<Button>(R.id.applyButton)
        postuler.setOnClickListener {
            postuler_offre(offre, it, root)
        }

        return root

    }

    private fun postuler_offre(offre: Offre, anchorView: View, root: View) {
        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)

        val user_id = sharedPref?.getLong("user_id", -1)

        if(user_id == -1L) {
            val intent = Intent(activity, ConnectionActivity::class.java)
            startActivity(intent)
            return
        }

        findNavController().navigate(
            R.id.action_navigation_offres_to_navigation_candidature,
            Bundle().apply {
                putLong("offre_id", offre.id)
            },
        )

    }

    private fun enregistrer_offre(offre: Offre) {
        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)
        val user_id = sharedPref?.getLong("user_id", -1)
        if(user_id == -1L) {
            val intent = Intent(activity, ConnectionActivity::class.java)
            startActivity(intent)
            return
        }

        val enregistree = OffreService().enregistrer(user_id!!, offre.id)

        var msg: String
        if(enregistree == -1L)
            msg = "L'offre a déjà été enregistrée"
        else
            msg = "L'offre a bien été enregistrée"

        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()

    }

    private fun bind_offre(root: View, offre: Offre) {
        // fill the view
        val title = root.findViewById<android.widget.TextView>(R.id.offre_title)
        title.text = offre.title

        val metier = root.findViewById<android.widget.TextView>(R.id.offre_metier)
        metier.text = offre.metier

        val description = root.findViewById<android.widget.TextView>(R.id.offre_desc)
        description.text = offre.description

        val start_date = root.findViewById<android.widget.TextView>(R.id.offre_date)
        start_date.text = offre.date_debut + " - " + offre.date_fin

        val remuneration = root.findViewById<android.widget.TextView>(R.id.offre_remuneration)
        remuneration.text = offre.remuneration.toString() + "€/h"

    }


}