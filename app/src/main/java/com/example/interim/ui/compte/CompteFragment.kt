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
import com.example.interim.models.AdminProfileFragment
import com.example.interim.models.TemporaryWorker
import com.example.interim.services.UsersService
import com.example.interim.ui.candidature.EmployerCandidatureFragment
import com.example.interim.ui.candidature.UserCandidatureFragment
import com.example.interim.ui.connection.ConnectionActivity

class CompteFragment : Fragment(), WorkerEditInfoFragment.NestedFragmentCallback, EmployerEditInfoFragment.NestedFragmentCallback {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)!!
        val user_id = sharedPref.getLong("user_id", -1)
        val user_role =  sharedPref.getString("user_role", "worker")

        if(user_id == -1L) {
            val intent = Intent(activity, ConnectionActivity::class.java)
            startActivity(intent)
        }

        val view = inflater.inflate(R.layout.fragment_compte, container, false)

        val deconnect_button = view.findViewById<View>(R.id.btnDeconnexion) as Button
        deconnect_button.setOnClickListener {
            with(sharedPref.edit()) {
                this?.putLong("user_id", -1)
                this?.apply()
            }

            val intent = Intent(activity, ConnectionActivity::class.java)
            startActivity(intent)
        }

        val btn_modifier = view.findViewById<Button>(R.id.btn_modifier)

        lateinit var fragment: Fragment

        when(user_role){
            "worker" -> {
                fragment = WorkerProfileFragment()
                btn_modifier.visibility = View.VISIBLE
            }
            "employer" -> {
                fragment = EmployerProfileFragment()
                btn_modifier.visibility = View.VISIBLE
            }
            "admin" -> {
                fragment = AdminProfileFragment()
                btn_modifier.visibility = View.GONE
            }
        }

        var fragmentContainer = view.findViewById<ViewGroup>(R.id.fragment_container)

        var fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(fragmentContainer.id, fragment)
        fragmentTransaction.commit()

        btn_modifier.setOnClickListener {
            if(btn_modifier.visibility == View.VISIBLE) {
                btn_modifier.visibility = View.GONE
            }
            if(deconnect_button.visibility == View.VISIBLE) {
                deconnect_button.visibility = View.GONE
            }
            when(user_role){
                "worker" -> {
                    fragment = WorkerEditInfoFragment()
                }
                "employer" -> {
                    fragment = EmployerEditInfoFragment()
                }
            }

            fragmentContainer = view.findViewById(R.id.fragment_container)
            fragmentTransaction = childFragmentManager.beginTransaction()
            fragmentTransaction.replace(fragmentContainer.id, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }

    override fun onWorkerActionTriggered() {
        recoverView(WorkerProfileFragment())
    }

    override fun onEmployerActionTriggered() {
        recoverView(EmployerProfileFragment())
    }

    private fun recoverView(fragment: Fragment) {
        val btn_modifier = view?.findViewById<Button>(R.id.btn_modifier)!!
        val btn_deconnect = view?.findViewById<Button>(R.id.btnDeconnexion)!!

        if (btn_modifier.visibility == View.GONE) {
            btn_modifier.visibility = View.VISIBLE
        }
        if (btn_deconnect.visibility == View.GONE) {
            btn_deconnect.visibility = View.VISIBLE
        }
        val fragmentContainer = view?.findViewById<ViewGroup>(R.id.fragment_container)
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(fragmentContainer!!.id, fragment)
        fragmentTransaction.commit()
    }


}