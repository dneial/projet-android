package com.example.interim.ui.offres

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.interim.R
import com.example.interim.services.OffreService
import com.example.interim.databinding.FragmentOffreBinding
import com.example.interim.models.Offre
import com.example.interim.ui.connection.ConnectionActivity
import java.util.regex.Pattern
import android.app.AlertDialog
import android.text.InputType
import android.widget.EditText
import com.example.interim.models.Report
import com.example.interim.services.ReportService
import com.example.interim.services.UsersService


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
        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)
        val offreId: Long = arguments?.getLong("offre_id")!!

        val user_id = sharedPref?.getLong("user_id", -1)
        val user_role = sharedPref?.getString("user_role", "worker")

        val offre = OffreService().getOffre(offreId)!!

        bind_offre(root, offre)

        val user_btns = root.findViewById<View>(R.id.user_offre_buttons)
        val enregistrer = root.findViewById<Button>(R.id.saveButton)
        check_enregistrement_and_set_listener(offre, enregistrer)


        val postuler = root.findViewById<Button>(R.id.applyButton)
        postuler.setOnClickListener {
            postuler_offre(offre, it, root)
        }

        val employer_btns = root.findViewById<View>(R.id.entreprise_offre_buttons)
        val modifier = root.findViewById<Button>(R.id.btn_modifier)
        modifier.setOnClickListener {
            modifier_offre(offre, it, root)
        }

        val supprimer = root.findViewById<Button>(R.id.btn_delete)
        supprimer.setOnClickListener {
            supprimer_offre(offre, it, root)
        }

        val admin_btns = root.findViewById<View>(R.id.admin_offre_buttons)
        val delete = root.findViewById<Button>(R.id.btn_delete_admin)
        delete.setOnClickListener {
            supprimer_offre(offre, it, root)
        }

        val reportButton = root.findViewById<View>(R.id.reportButton)
        reportButton.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(resources.getText(R.string.report_offer))

            val input = EditText(context)
            input.hint = resources.getText(R.string.et_commentary)
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            builder.setView(input)

            builder.setPositiveButton(resources.getText(R.string.report)) { dialog, which ->
                val reportReason = input.text.toString()
                ReportService().create(Report(0, UsersService().getUser(user_id!!, user_role!!)!!.getEmail(), offre, reportReason))
                Toast.makeText(context, resources.getText(R.string.report_done), Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton(resources.getText(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        when(user_role){
            "worker" -> {
                user_btns.visibility = View.VISIBLE
                employer_btns.visibility = View.GONE
                admin_btns.visibility = View.GONE
                reportButton.visibility = View.VISIBLE
            }
            "employer" -> {
                if(offre.employer.getId() == user_id){
                    user_btns.visibility = View.GONE
                    employer_btns.visibility = View.VISIBLE
                    admin_btns.visibility = View.GONE
                    reportButton.visibility = View.VISIBLE
                }
            }
            "admin" -> {
                user_btns.visibility = View.GONE
                employer_btns.visibility = View.GONE
                admin_btns.visibility = View.VISIBLE
                reportButton.visibility = View.GONE
            }
            else -> {
                user_btns.visibility = View.GONE
                employer_btns.visibility = View.GONE
                admin_btns.visibility = View.GONE
            }
        }

        return root

    }

    private fun check_enregistrement_and_set_listener(offre: Offre, enregistrer: Button) {
        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)
        val user_id = sharedPref?.getLong("user_id", -1)

        val enregistree = OffreService().isEnregistree(user_id!!, offre.id)

        if(enregistree) {
            enregistrer?.text = context?.getString(R.string.unsave)
            enregistrer.setOnClickListener {
                desenregistrer_offre(offre)
                check_enregistrement_and_set_listener(offre, enregistrer)
            }
        }
        else {
            enregistrer?.text = context?.getString(R.string.enregistrer)
            enregistrer.setOnClickListener {
                enregistrer_offre(offre)
                check_enregistrement_and_set_listener(offre, enregistrer)
            }
        }
    }

    private fun desenregistrer_offre(offre: Offre) {

        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)
        val user_id = sharedPref?.getLong("user_id", -1)

        val desenregistree = OffreService().unsave(offre.id, user_id)

        var msg: String

        if(!desenregistree)
            msg = "L'offre n'a pas pu être désenregistrée"
        else
            msg = "L'offre a bien été supprimée des enregistrements"

        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    private fun supprimer_offre(offre: Offre, it: View?, root: View) {

        val deleted = OffreService().delete(offre.id)

        var msg: String
        if(!deleted)
            msg = "L'offre n'a pas pu être supprimée"
        else
            msg = "L'offre a bien été supprimée"

        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.navigation_dashboard)
    }

    private fun modifier_offre(offre: Offre, it: View?, root: View) {
        findNavController().navigate(
            R.id.action_navigation_offre_to_navigation_edit_offre,
            Bundle().apply {
                putLong("offre_id", offre.id)
            },
        )
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
        val title = root.findViewById<TextView>(R.id.offre_title)
        title.text = offre.title

        val metier = root.findViewById<TextView>(R.id.offre_metier)
        metier.text = offre.metier

        val ville = root.findViewById<TextView>(R.id.offre_ville)
        ville.text = offre.ville

        val description = root.findViewById<TextView>(R.id.offre_desc)
        description.text = offre.description

        val start_date = root.findViewById<TextView>(R.id.offre_date)
        start_date.text = format_date_to_view(offre.date_debut) + " - " + format_date_to_view(offre.date_fin)

        val remuneration = root.findViewById<TextView>(R.id.offre_remuneration)
        remuneration.text = offre.remuneration.toString() + "€/h"

    }

    fun format_date_to_view(date: String): String {
        val pattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})")
        val matcher = pattern.matcher(date)

        if(matcher.find()) {
            return matcher.group(3) + "/" + matcher.group(2) + "/" + matcher.group(1)
        }

        return date
    }


}