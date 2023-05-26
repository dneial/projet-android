package com.example.interim.ui.offres

import android.content.Context
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
            postuler_offre(offre, it, root)
        }

        return root

    }

    private fun postuler_offre(offre: Offre, anchorView: View, root: View) {
        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)

        val user_id = sharedPref?.getLong("user_id", -1)
        Log.d("user_id", user_id.toString())

        if(user_id == -1L) {
            findNavController().navigate(
                R.id.action_navigation_offres_to_navigation_inapp_connection,
            )
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

    private fun showLoginPopup(anchorView: View, root: View) {
        Log.d("popup", "showLoginPopup")
        // Inflate the popup layout
        val popupView = layoutInflater.inflate(R.layout.popup_login, null)

        // Create the popup window
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            true
        )

        // Set an elevation value for the popup window
        popupWindow.elevation = 10f

        // Find the login and sign-up buttons
        val loginButton = popupView.findViewById<Button>(R.id.connectionButton)
        val signupButton = popupView.findViewById<TextView>(R.id.signUpTextView)

        // Set click listeners for the buttons
        loginButton.setOnClickListener {
            // Handle login button click
            popupWindow.dismiss()
            // Perform login action
        }

        signupButton.setOnClickListener {
            // Handle sign-up button click
            popupWindow.dismiss()
            // Perform sign-up action
        }

        // Show the popup window anchored to the specified view
        popupWindow.showAtLocation(
            anchorView,
            Gravity.CENTER,
            0,
            0
        )
    }


}