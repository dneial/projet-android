package com.example.interim.ui.offres

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.database.OffreService
import com.example.interim.models.Offre
import com.example.interim.services.UsersService

class OffreFormFragment: Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.offre_form, container, false)

        var submit_button = view.findViewById<Button>(R.id.submit_button)

        submit_button.setOnClickListener(this)

        return view
    }

    override fun onClick(p0: View?) {
        val user_id = activity?.getSharedPreferences("interim",Context.MODE_PRIVATE)?.getLong("user_id", -1)!!


        val offre_title = view?.findViewById<android.widget.EditText>(R.id.edit_title)?.text.toString()
        val offre_metier = view?.findViewById<android.widget.EditText>(R.id.edit_profession)?.text.toString()
        val offre_desc = view?.findViewById<android.widget.EditText>(R.id.edit_description)?.text.toString()
        val offre_ville = view?.findViewById<android.widget.EditText>(R.id.edit_address)?.text.toString()
        val offre_remuneration = view?.findViewById<android.widget.EditText>(R.id.edit_salary)?.text.toString()
        val offre_date_debut = view?.findViewById<android.widget.DatePicker>(R.id.date_start)!!
        val offre_date_fin = view?.findViewById<android.widget.DatePicker>(R.id.date_end)!!

        val debut_date = offre_date_debut.year.toString() + "-" +
                (offre_date_debut.month + 1).toString() + "-" +
                offre_date_debut.dayOfMonth.toString()

        val fin_date = offre_date_fin.year.toString() + "-" +
                (offre_date_fin.month + 1).toString() + "-" +
                offre_date_fin.dayOfMonth.toString()

        
        if(offre_title         == "" ||
            offre_metier       == "" ||
            offre_desc         == "" ||
            offre_ville        == "" ||
            offre_remuneration == "" ||
            debut_date         == "" ||
            fin_date           == ""){
            Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }


        val employer = UsersService().getEmployer(user_id)

        val offre = Offre(
            title=offre_title,
            metier=offre_metier,
            description=offre_desc,
            date_debut=debut_date,
            date_fin=fin_date,
            remuneration=offre_remuneration,
            id=0,
            ville=offre_ville,
            employer=employer!!
        )


        var offreService = OffreService()

        if(offreService.create(offre)){
            Toast.makeText(requireContext(), "Offre créée", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }
        else
            Toast.makeText(requireContext(), "Erreur lors de la création de l'offre", Toast.LENGTH_SHORT).show()



    }
}