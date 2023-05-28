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
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.interim.R
import com.example.interim.database.CandidatureService
import com.example.interim.models.Candidature
import org.w3c.dom.Text


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

        bind_worker_info(info_layout)
        return view
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

