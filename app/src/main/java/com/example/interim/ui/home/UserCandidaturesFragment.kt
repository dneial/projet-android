package com.example.interim.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.interim.R
import com.example.interim.databinding.FragmentCandidaturesBinding

class UserCandidaturesFragment: Fragment() {

    private var _binding: FragmentCandidaturesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val user_id = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getLong("user_id", -1)!!
        val user_role = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getString("user_role", "worker")!!

        val factory = HomeViewModelFactory(user_id, user_role)

        val homeViewModel =
            ViewModelProvider(this, factory)[UserCandidaturesViewModel::class.java]

        _binding = FragmentCandidaturesBinding.inflate(inflater, container, false)
        val root: View = binding.root

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

        val searchButton = binding.root.findViewById<Button>(R.id.searchButton)
        val searchInput = binding.root.findViewById<EditText>(R.id.searchBarEditText)
        searchButton.setOnClickListener {
            homeViewModel.filterByText(
                searchInput.text.toString()
            )
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}