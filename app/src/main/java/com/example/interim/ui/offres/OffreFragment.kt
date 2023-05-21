package com.example.interim.ui.offres

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.interim.R
import com.example.interim.databinding.FragmentOffreBinding
import com.example.interim.models.Candidature
import com.example.interim.models.Offre
import java.util.Date

class OffreFragment : Fragment() {

    companion object {
        fun newInstance() = OffreFragment()
    }

    private var _binding: FragmentOffreBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: OffreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val offreViewModel =
            ViewModelProvider(this)[OffreViewModel::class.java]

        _binding = FragmentOffreBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val offre: Offre = arguments?.getParcelable("offre")!!

        bind_offre(root, offre)

        val enregistrer = root.findViewById<Button>(R.id.saveButton)
        enregistrer.setOnClickListener {
            enregistrer_offre(offre)
        }

        val postuler = root.findViewById<Button>(R.id.applyButton)
        postuler.setOnClickListener {
            postuler_offre(offre)
        }

        return root

    }

    private fun postuler_offre(offre: Offre) {
        val Date = Date()
        val date = Date.toString().substring(0, 10)


        val user_id = activity?.getPreferences(Context.MODE_PRIVATE)?.getLong("user_id", 0)

        if(user_id == 0L) {
            Log.d("OffreFragment", "user_id not found")
            return
        }
        
        val candidature = Candidature(
            -1,
            offre.id,
            user_id!!,
            offre.id_entreprise,
            "En cours",
            date
        )
        Log.d("Candidature", candidature.toString())
    }

    private fun enregistrer_offre(offre: Offre) {
        TODO("Not yet implemented")
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