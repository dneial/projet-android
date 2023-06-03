package com.example.interim.ui.home.admin_view.signalements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.interim.R

class AdminSignalementsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_signalements, container, false)

        val viewModel = ViewModelProvider(this)[SignalementsViewModel::class.java]

        val list = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.report_listView)

        viewModel.reports.observe(viewLifecycleOwner) {
            val manager = androidx.recyclerview.widget.LinearLayoutManager(context)
            val adapter = SignalementRecycleAdapter(it)
            manager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
            list.layoutManager = manager
            list.adapter = adapter
            list.setPadding(0, 0, 0, 200)
            list.clipToPadding = false
        }

        return view
    }

}
