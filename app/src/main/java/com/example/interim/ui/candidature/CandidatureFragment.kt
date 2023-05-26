package com.example.interim.ui.candidature

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.database.CandidatureService
import com.example.interim.database.UsersService
import com.example.interim.models.Candidature
import com.example.interim.models.TemporaryWorker


class CandidatureFragment: Fragment() {

    private lateinit var candidature: Candidature

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_candidature, container, false)

        val candidature_id = arguments?.getLong("candidature_id")!!

        val candidatureService = CandidatureService()
        candidature = candidatureService.getCandidature(candidature_id!!)

        bind_view(view)

        val desistementButton = view.findViewById<android.widget.Button>(R.id.btn_desister)
        desistementButton.setOnClickListener {
            showConfirmationDialog()
        }

        val contactButton = view.findViewById<android.widget.Button>(R.id.btn_contacter)
        contactButton.setOnClickListener {
            showContactInfoDialog()
        }

        return view
    }

    private fun showContactInfoDialog() {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.dialog_contact_info)

        val email = candidature.offre.employer?.getEmail()
        val phone = candidature.offre.employer?.getPhone()
        dialog.findViewById<TextView>(R.id.email_contact).text = email
        dialog.findViewById<TextView>(R.id.phone_contact).text = phone

        val copyEmailButton = dialog.findViewById<android.widget.ImageButton>(R.id.copyEmail_button)
        copyEmailButton.setOnClickListener {
            copyToClipboard(email)
        }

        val copyPhoneButton = dialog.findViewById<android.widget.ImageButton>(R.id.copyPhone_button)
        copyPhoneButton.setOnClickListener {
            copyToClipboard(phone)
        }

        dialog.show();

    }

    private fun copyToClipboard(info: String?) {

        val clipboardManager: ClipboardManager =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val clipData = ClipData.newPlainText("info", info)

        clipboardManager.setPrimaryClip(clipData)

        Toast.makeText(requireContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun desister() {
        val candidatureService = CandidatureService()
        candidatureService.delete(candidature.id)
        activity?.onBackPressed()
    }


    private fun showConfirmationDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Desistement de candidature")
        builder.setMessage("Êtes-vous sûr de vouloir vous désister de cette candidature ?")
        builder.setPositiveButton("Oui",
            DialogInterface.OnClickListener { dialog, which -> desister() })
        builder.setNegativeButton("Non",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun bind_view(view: View) {
            val title = view.findViewById<android.widget.TextView>(R.id.offre_title)
            val offre = candidature.offre
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

