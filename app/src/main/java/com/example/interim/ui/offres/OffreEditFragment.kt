package com.example.interim.ui.offres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.interim.R
import com.example.interim.services.OffreService
import com.example.interim.models.Offre

class OffreEditFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.offre_edit_fragment, container, false)
        val offre_id = arguments?.getLong("offre_id")!!
        val offre = OffreService().getOffre(offre_id)!!

        bind_offre(view, offre)

        val confirm_btn = view.findViewById<View>(R.id.submit_button)

        confirm_btn.setOnClickListener {
            update_offre(offre, view)
            findNavController().navigate(
                R.id.navigation_offre,
                Bundle().apply {
                    putLong("offre_id", offre_id)
                }
            )

        }

        return view
    }

    private fun update_offre(offre: Offre, view: View) {
        val title = view.findViewById<TextView>(R.id.edit_title).text.toString()
        val metier = view.findViewById<TextView>(R.id.edit_profession).text.toString()
        val description = view.findViewById<TextView>(R.id.edit_description).text.toString()
        val ville = view.findViewById<TextView>(R.id.edit_address).text.toString()
        val remuneration = view.findViewById<TextView>(R.id.edit_salary).text.toString()
        val date_debut = view.findViewById<android.widget.DatePicker>(R.id.date_start)
        val date_fin = view.findViewById<android.widget.DatePicker>(R.id.date_end)

        val debut_date = date_debut.year.toString() + "-" +
                (date_debut.month + 1).toString() + "-" +
                date_debut.dayOfMonth.toString()

        val fin_date = date_fin.year.toString() + "-" +
                (date_fin.month + 1).toString() + "-" +
                date_fin.dayOfMonth.toString()

        if(title         == "" ||
            metier       == "" ||
            ville        == "" ||
            description  == "" ||
            remuneration == "" ||
            debut_date   == "" ||
            fin_date     == ""){
            Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }

        offre.title = title
        offre.metier = metier
        offre.description = description
        offre.ville = ville
        offre.remuneration = remuneration
        offre.date_debut = debut_date
        offre.date_fin = fin_date

        OffreService().update(offre)


    }

    private fun bind_offre(root: View, offre: Offre) {
        // fill the view
        root.findViewById<android.widget.EditText>(R.id.edit_title)?.setText(offre.title)
        root.findViewById<android.widget.EditText>(R.id.edit_profession)?.setText(offre.metier)
        root.findViewById<android.widget.EditText>(R.id.edit_description)?.setText(offre.description)
        root.findViewById<android.widget.EditText>(R.id.edit_address)?.setText(offre.ville)
        root.findViewById<android.widget.EditText>(R.id.edit_salary)?.setText(offre.remuneration)

        val offre_date_debut = root.findViewById<android.widget.DatePicker>(R.id.date_start)
        val offre_date_fin = root.findViewById<android.widget.DatePicker>(R.id.date_end)

        val debut_date = offre.date_debut.split("-")
        val fin_date = offre.date_fin.split("-")

        offre_date_debut.updateDate(
            debut_date[0].toInt(),
            debut_date[1].toInt(),
            debut_date[2].toInt()
        )
        offre_date_fin.updateDate(
            fin_date[0].toInt(),
            fin_date[1].toInt(),
            fin_date[2].toInt()
        )

    }
}