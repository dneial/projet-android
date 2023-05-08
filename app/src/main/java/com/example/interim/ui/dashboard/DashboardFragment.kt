package com.example.interim.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.interim.R
import com.example.interim.database.MaBaseOpenHelper
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

        var refresh = binding.root.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)

        refresh.setOnRefreshListener {
            refresh.isRefreshing = false
            dashboardViewModel.refresh()
        }

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
        button.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_navigation_dashboard_to_create_offre
            )
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}