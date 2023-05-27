package com.example.interim.ui.home.user_view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.interim.databinding.FragmentOffresEnregistreesBinding
import com.example.interim.ui.dashboard.OffreRecycleAdapter
import com.example.interim.ui.home.HomeViewModelFactory

class UserOffresEnregistreesFragment: Fragment() {

    private var _binding: FragmentOffresEnregistreesBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)
        val user_id = sharedPref?.getLong("user_id", -1)!!
        val user_role = sharedPref.getString("user_role", "worker")!!

        val factory = HomeViewModelFactory(user_id, user_role)
        val homeViewModel =
            ViewModelProvider(this, factory)[OffresEnregistreesViewModel::class.java]

        _binding = FragmentOffresEnregistreesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView: RecyclerView = binding.offresListView

        homeViewModel.offres.observe(viewLifecycleOwner) {
            val manager = androidx.recyclerview.widget.LinearLayoutManager(context)
            manager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
            listView.adapter = OffreRecycleAdapter(it)
            listView.layoutManager = manager
            listView.setPadding(0, 0, 0, 200)
            listView.clipToPadding = false
        }

        homeViewModel.refresh()
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}