package com.example.interim.ui.candidature

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.interim.R
import com.example.interim.database.CandidatureService
import com.example.interim.database.OffreService
import com.example.interim.services.UsersService
import com.example.interim.models.Candidature
import com.example.interim.models.TemporaryWorker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern

class CandidatureFormFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_candidature_form, container, false)


        val offre_id = arguments?.getLong("offre_id")!!


        val postulerButton = view.findViewById<Button>(R.id.candidature_button)
        postulerButton.setOnClickListener {
        if (checkSignUpInterim(view))
            postuler(offre_id)
        }

        bind_info(view)
        
        return view
    }

    private fun bind_info(view: View?) {
        val user_id = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getLong("user_id", -1)
        val user: TemporaryWorker? = UsersService().getTemporaryWorker(user_id!!)

        if(user != null) {
            view?.findViewById<EditText>(R.id.candidature_prenom_edit)?.setText(user.getFirstName())
            view?.findViewById<EditText>(R.id.candidature_nom_edit)?.setText(user.getLastName())
            view?.findViewById<EditText>(R.id.candidature_nationality_edit)?.setText(user.getNationality())
            view?.findViewById<EditText>(R.id.candidature_birthday_edit)?.setText(user.getBirthday())
        }
    }

    private fun postuler(offreId: Long) {


        val Date = Date()
        val dateFormated = SimpleDateFormat("yyyy-MM-dd").format(Date)
        // get date in format 'yyyy-mm-dd'


        Log.d("Candidature", "Date: $dateFormated")


        val user_id =
            activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getLong("user_id", 0)

        val offre = OffreService().getOffre(offreId)
        val user = UsersService().getTemporaryWorker(user_id!!)

        var candidature = Candidature(
            0,
            offre!!,
            user!!,
            dateFormated,
            "En attente"
        )

        candidature = CandidatureService().create(candidature)

        val navController = findNavController()

        val fragInStack = navController.popBackStack(R.id.navigation_offre, false)

        if(fragInStack) {
            navController.navigate(
                R.id.navigation_offre,
                Bundle().apply {
                    putLong("offre_id", offre.id)
                }
            )
        } else {
            navController.navigate(
                R.id.navigation_offre,
                Bundle().apply {
                    putLong("offre_id", offre.id)
                }

            )
        }
    }

    private fun checkSignUpInterim(view: View) : Boolean{
        var correct : Boolean = true
        val lastName = view.findViewById<EditText>(R.id.candidature_nom_edit)
        val firstName = view.findViewById<EditText>(R.id.candidature_prenom_edit)
        val birthday = view.findViewById<EditText>(R.id.candidature_birthday_edit)
        val nationality = view.findViewById<EditText>(R.id.candidature_nationality_edit)

        val lastNameWarning = view.findViewById<TextView>(R.id.candidature_nom_warning)
        val firstNameWarning = view.findViewById<TextView>(R.id.candidature_prenom_warning)
        val birthdayWarning = view.findViewById<TextView>(R.id.candidature_birthday_warning)
        val nationalityWarning = view.findViewById<TextView>(R.id.candidature_nationality_warning)

        var pattern = Pattern.compile("^[\\p{L}\\s'-]+\$")
        if (firstName.text.toString() == "" ){
            firstName.setBackgroundResource(R.drawable.outline_warning)
            firstNameWarning.visibility = View.VISIBLE
            correct = false
        } else if (!pattern.matcher(firstName.text.toString()).matches()){
            firstNameWarning.visibility = View.VISIBLE
            correct = false
            firstName.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        } else {
            firstNameWarning.visibility = View.GONE
            firstName.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        }

        if (lastName.text.toString() == ""){
            lastNameWarning.visibility = View.VISIBLE
            lastName.setBackgroundResource(R.drawable.outline_warning)
            correct = false
        } else if (!pattern.matcher(lastName.text.toString()).matches()){
            lastNameWarning.visibility = View.VISIBLE
            lastName.setBackgroundResource(R.drawable.outline_warning)
            correct = false
        } else {
            lastNameWarning.visibility = View.GONE
            lastName.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        }

        if (nationality.text.toString() != "" && !pattern.matcher(nationality.text.toString()).matches()){
            nationality.setBackgroundResource(R.drawable.outline_warning)
            nationalityWarning.visibility = View.VISIBLE
        } else {
            nationalityWarning.visibility = View.GONE
            nationality.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        }

        pattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/((19|20)\\d\\d)\$")
        if (birthday.text.toString() != "" && !pattern.matcher(birthday.text.toString()).matches()){
            birthday.setBackgroundResource(R.drawable.outline_warning)
            birthdayWarning.visibility = View.VISIBLE
            correct = false
        } else { birthdayWarning.visibility = View.GONE
            birthday.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material) }

        return correct
    }
}