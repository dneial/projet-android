package com.example.interim.ui.compte


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.interim.MainActivity
import com.example.interim.R
import com.example.interim.models.TemporaryWorker
import com.example.interim.services.UsersService
import com.example.interim.ui.connection.ConnectionActivity

class EmployerProfileFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.employer_profile_fragment, container, false)

        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)!!
        val user_id = sharedPref.getLong("user_id", -1)

        val user_info_layout = view.findViewById<View>(R.id.employer_info)!!
        bind_user_info(user_info_layout, user_id)

        return view
    }

    private fun bind_user_info(userInfoLayout: View, user_id: Long) {
        val user = UsersService().getEmployer(user_id)
        if(user != null){

            val company_name = userInfoLayout.findViewById<TextView>(R.id.company_name)
            val company_service = userInfoLayout.findViewById<TextView>(R.id.company_metier)
            val company_sub_service = userInfoLayout.findViewById<TextView>(R.id.company_sous_metier)
            val company_siret = userInfoLayout.findViewById<TextView>(R.id.company_siret)
            val employer1_name = userInfoLayout.findViewById<TextView>(R.id.company_name1)
            val employer1_email = userInfoLayout.findViewById<TextView>(R.id.company_email1)
            val employer1_phone = userInfoLayout.findViewById<TextView>(R.id.company_phone1)
            val employer2_name = userInfoLayout.findViewById<TextView>(R.id.company_name2)
            val employer2_email = userInfoLayout.findViewById<TextView>(R.id.company_email2)
            val employer2_phone = userInfoLayout.findViewById<TextView>(R.id.company_phone2)
            val company_address = userInfoLayout.findViewById<TextView>(R.id.company_address)
            val company_commentary = userInfoLayout.findViewById<TextView>(R.id.company_comment)

            company_name.text = user.getName()
            company_service.text = user.getService()
            company_sub_service.text = user.getSubService()
            company_siret.text = user.getSIRET()
            employer1_name.text = user.getContact()
            employer1_email.text = user.getEmail()
            employer1_phone.text = user.getPhone()
            employer2_name.text = user.getSubContact()
            employer2_email.text = user.getSubEmail()
            employer2_phone.text = user.getSubPhone()
            company_address.text = user.getAddress()
            company_commentary.text = user.getCommentary()
        }

    }

}