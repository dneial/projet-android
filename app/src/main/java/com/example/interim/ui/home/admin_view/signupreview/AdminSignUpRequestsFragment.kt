package com.example.interim.ui.home.admin_view.signupreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.interim.R
import com.example.interim.models.Employer

class AdminSignUpRequestsFragment : Fragment(), EmployerReviewAdapter.OnUpdateButtonClickListener {


    lateinit var viewModel: SignUpReviewViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.fragment_requests,
            container,
            false
        )


        viewModel = ViewModelProvider(this)[SignUpReviewViewModel::class.java]

        val list = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.request_listView)

        viewModel.users.observe(viewLifecycleOwner) {
            val manager = androidx.recyclerview.widget.LinearLayoutManager(context)
            val adapter = EmployerReviewAdapter(it)
            adapter.onUpdateButtonClickListener = this
            manager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
            list.layoutManager = manager
            list.adapter = adapter
            list.setPadding(0, 0, 0, 200)
            list.clipToPadding = false
        }

        return view
    }

    override fun onAcceptButtonClick(item: Employer) {
        viewModel.verifyUser(item)
    }

    override fun onRefuseButtonClick(item: Employer) {
        viewModel.refuseUser(item)
    }

}
