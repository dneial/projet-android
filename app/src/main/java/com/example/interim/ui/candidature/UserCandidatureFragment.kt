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
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.services.CandidatureService
import com.example.interim.models.Candidature


class UserCandidatureFragment: Fragment() {

    private lateinit var candidature: Candidature

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)!!

        val view = inflater.inflate(R.layout.user_candidature_layout, container, false)

        val candidature_id = arguments?.getLong("candidature_id")

        val candidatureService = CandidatureService()
        candidature = candidatureService.getCandidature(candidature_id!!)

        val desistementButton = view.findViewById<android.widget.Button>(R.id.btn_desister)
        desistementButton.setOnClickListener {
            showConfirmationDialog()
        }

        val contactButton = view.findViewById<android.widget.Button>(R.id.btn_contacter)
        contactButton.setOnClickListener {
            showContactInfoDialog()
        }


        val acceptButton = view.findViewById<android.widget.Button>(R.id.btn_accepter)
        if(candidature.status == "Acceptée") {
            acceptButton.visibility = View.VISIBLE
            acceptButton.setOnClickListener {
                activity?.onBackPressed()
            }
        }
        return view
    }

    private fun showContactInfoDialog() {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.dialog_contact_info)

        val name = candidature.offre.employer?.getName()
        val email = candidature.offre.employer?.getEmail()
        val phone = candidature.offre.employer?.getPhone()

        dialog.findViewById<TextView>(R.id.tv_nom_entreprise).text = name
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



}

