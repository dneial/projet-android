package com.example.interim.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.interim.R
import com.example.interim.database.MaBaseOpenHelper
import com.example.interim.database.UsersService
import com.example.interim.databinding.FragmentDashboardBinding
import com.example.interim.ui.offres.OffreFormFragment

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val factory = MyViewModelFactory(requireContext())
        val dashboardViewModel =
            ViewModelProvider(this, factory)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)
        val ville = sharedPref?.getString("ville", "")!!

        var refresh = binding.root.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)

        refresh.setOnRefreshListener {
            refresh.isRefreshing = false
            if(ville == "") dashboardViewModel.refresh()
            else dashboardViewModel.filter(ville)
        }



        if(ville != "") dashboardViewModel.filter(ville)

        val listView: RecyclerView = binding.listView
        dashboardViewModel.offres.observe(viewLifecycleOwner) {
            val manager = androidx.recyclerview.widget.LinearLayoutManager(context)
            manager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
            listView.adapter = OffreRecycleAdapter(it)
            listView.layoutManager = manager
            listView.setPadding(0, 0, 0, 200)
            listView.clipToPadding = false
        }

        val button: Button = binding.fixedButton

        val user_id = sharedPref.getLong("user_id", -1)
        var role = ""
        if(user_id != -1L){
            role = UsersService().getRole(user_id)
        }
        if(role != "employeur")
            button.visibility = View.GONE
        else
            button.visibility = View.VISIBLE



        button.setOnClickListener{
            if(check_permissions())
                it.findNavController().navigate(
                    R.id.action_navigation_dashboard_to_create_offre
                )
        }

        return root
    }

    fun check_permissions(): Boolean {
        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)
        Log.d("sharedPref", sharedPref?.all.toString())
        val user_id = sharedPref?.getLong("user_id", -1)!!
        Log.d("user_id", user_id.toString())

        if(user_id == -1L){
            Toast.makeText(requireContext(), "Veuillez vous connecter", Toast.LENGTH_SHORT).show()
            return false
        }

        val role = user_id?.let { UsersService().getRole(it) }
        Log.d("role", role.toString())

        if(role != "employeur"){
            Toast.makeText(requireContext(), "Vous n'avez pas le droit de créér une offre'", Toast.LENGTH_SHORT).show()
            return false
        }

        return true;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}