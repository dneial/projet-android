package com.example.interim.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.interim.databinding.FragmentCandidaturesBinding
import com.example.interim.ui.dashboard.OffreRecycleAdapter

class UserCandidaturesFragment: Fragment() {

    private var _binding: FragmentCandidaturesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d("UserCandidaturesFragment", "onCreateView")

        val user_id = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getLong("user_id", -1)!!
        val factory = HomeViewModelFactory(user_id)
        val homeViewModel =
            ViewModelProvider(this, factory)[CandidaturesViewModel::class.java]

        _binding = FragmentCandidaturesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.d("owner", viewLifecycleOwner.toString())
        val listView: RecyclerView = binding.candidatureListView
        homeViewModel.candidatures.observe(viewLifecycleOwner) {
            val manager = androidx.recyclerview.widget.LinearLayoutManager(context)
            manager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
            listView.adapter = CandidatureRecycleAdapter(it)
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