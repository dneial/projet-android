package com.example.interim.ui.candidature


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.services.CandidatureService
import com.example.interim.models.Candidature


class EmployerCandidatureFragment: Fragment() {

    private lateinit var candidature: Candidature

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)!!

        val view = inflater.inflate(R.layout.employer_candidature_layout, container, false)

        val candidature_id = arguments?.getLong("candidature_id")!!

        val candidatureService = CandidatureService()
        candidature = candidatureService.getCandidature(candidature_id!!)

        val info_btn = view.findViewById<Button>(R.id.info_worker_btn)
        val info_layout = view.findViewById<View>(R.id.worker_info_layout)

        info_btn.setOnClickListener {
            if(info_layout.visibility == View.GONE) {
                info_layout.visibility = View.VISIBLE
                info_btn.text = context?.getString(R.string.hide_info_worker)
            } else {
                info_layout.visibility = View.GONE
                info_btn.text = context?.getString(R.string.info_worker)
            }
        }

        val acceptButton = view.findViewById<Button>(R.id.interim_accept)
        val refuseButton = view.findViewById<Button>(R.id.interim_refuse)
        if(candidature.status == "Acceptée" || candidature.status == "Refusée") {
            acceptButton.visibility = View.GONE
            refuseButton.visibility = View.GONE
        }

        acceptButton.setOnClickListener {
            createInfoDialog("accept")
        }
        refuseButton.setOnClickListener {
            createInfoDialog("refuse")
        }

        bind_worker_info(info_layout)
        return view
    }

    private fun accept_candidature() {

            val candidatureService = CandidatureService()
            candidatureService.accept(candidature)
            Toast.makeText(context, resources.getString(R.string.candidature_accepted), Toast.LENGTH_SHORT).show()
            view?.findViewById<Button>(R.id.interim_accept)?.visibility = View.GONE
            view?.findViewById<Button>(R.id.interim_refuse)?.visibility = View.GONE

    }

    private fun refuse_candidature() {
            val candidatureService = CandidatureService()
            candidatureService.refuse(candidature)
            Toast.makeText(context, resources.getString(R.string.candidature_refused), Toast.LENGTH_SHORT).show()
            view?.findViewById<Button>(R.id.interim_accept)?.visibility = View.GONE
            view?.findViewById<Button>(R.id.interim_refuse)?.visibility = View.GONE
    }


    private fun createInfoDialog(s: String) {
        val msg = if (s == "accept") resources.getString(R.string.popup_accept_message) else resources.getString(R.string.popup_refuse_message)
        val dialogBuilder = AlertDialog.Builder(context)
            .setTitle(resources.getString(R.string.popup_candidature_dialog_title))
            .setMessage(msg)
            .setPositiveButton(resources.getString(R.string.yes_btn)) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
                if(s == "accept") accept_candidature()
                else refuse_candidature()
            }
            .setNegativeButton(resources.getString(R.string.no_btn)) { dialogInterface: DialogInterface, i: Int ->
                // Handle the denial case when the user clicks "Deny"
                dialogInterface.dismiss()
            }
            .setCancelable(false)

        dialogBuilder.create().show()

    }

    private fun bind_worker_info(infoLayout: View?) {
        val worker_name = infoLayout?.findViewById<TextView>(R.id.interim_prenom)
        val worker_last_name = infoLayout?.findViewById<TextView>(R.id.interim_last_name)
        val worker_email = infoLayout?.findViewById<TextView>(R.id.interim_email)
        val worker_phone = infoLayout?.findViewById<TextView>(R.id.interim_phone)
        val worker_birthday = infoLayout?.findViewById<TextView>(R.id.interim_birthday)
        val worker_city = infoLayout?.findViewById<TextView>(R.id.interim_ville)
        val worker_nationality = infoLayout?.findViewById<TextView>(R.id.interim_nationality)

        worker_name?.text = candidature.user?.getFirstName()
        worker_last_name?.text = candidature.user?.getLastName()
        worker_email?.text = candidature.user?.getEmail()
        worker_phone?.text = candidature.user?.getPhone()
        worker_birthday?.text = candidature.user?.getBirthday()
        worker_city?.text = candidature.user?.getCity()
        worker_nationality?.text = candidature.user?.getNationality()
    }



}

