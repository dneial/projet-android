package com.example.interim.ui.dashboard

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.interim.R
import com.example.interim.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var startDateChanged = false
    private var endDateChanged = false


    @RequiresApi(Build.VERSION_CODES.O)
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
            else dashboardViewModel.filterByCity(ville)
        }


        if(ville != "") dashboardViewModel.filterByCity(ville)

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
        val user_role = sharedPref.getString("user_role", "")!!
        if(user_role == "employer")
            button.visibility = View.VISIBLE
        else
            button.visibility = View.GONE

        button.setOnClickListener{
            if(check_permissions())
                it.findNavController().navigate(
                    R.id.action_navigation_dashboard_to_create_offre
                )
        }

        val searchButton = binding.root.findViewById<Button>(R.id.searchButton)
        val searchInput = binding.root.findViewById<EditText>(R.id.searchBarEditText)
        searchButton.setOnClickListener {
            dashboardViewModel.filterByText(
                searchInput.text.toString()
            )
        }

        val advancedFiltersButton = binding.root.findViewById<TextView>(R.id.advancedFiltersTextView)
        val advancedFiltersLayout = binding.root.findViewById<View>(R.id.advancedFiltersLinearLayout)

        advancedFiltersButton.setOnClickListener(
            View.OnClickListener {
                if(advancedFiltersLayout.visibility == View.GONE){
                    advancedFiltersLayout.visibility = View.VISIBLE
                }else{
                    advancedFiltersLayout.visibility = View.GONE
                    endDateChanged = false
                    startDateChanged = false
                }
            }
        )

        val filteredSearchButton = binding.root.findViewById<Button>(R.id.advancedSearchButton)
        filteredSearchButton.setOnClickListener{
            filteredSearch(dashboardViewModel)
        }

        val datePickerStart = binding.root.findViewById<android.widget.DatePicker>(R.id.date_start)
        val datePickerEnd = binding.root.findViewById<android.widget.DatePicker>(R.id.date_end)
        datePickerStart.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            startDateChanged = true
        }
        datePickerEnd.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            endDateChanged = true
        }

        return root
    }

    private fun filteredSearch(viewModel: DashboardViewModel) {
        val values = ContentValues()

        val title = view?.findViewById<android.widget.EditText>(R.id.edit_title)?.text.toString()
        val metier = view?.findViewById<android.widget.EditText>(R.id.edit_profession)?.text.toString()
        val desc = view?.findViewById<android.widget.EditText>(R.id.edit_description)?.text.toString()
        val ville = view?.findViewById<android.widget.EditText>(R.id.edit_address)?.text.toString()
        val remuneration = view?.findViewById<android.widget.EditText>(R.id.edit_salary)?.text.toString()
        val date_debut = view?.findViewById<android.widget.DatePicker>(R.id.date_start)
        val date_fin = view?.findViewById<android.widget.DatePicker>(R.id.date_end)

        if(title != "") values.put("title", title)
        if(metier != "") values.put("metier", metier)
        if(desc != "") values.put("desc", desc)
        if(ville != "") values.put("ville", ville)
        if(remuneration != "") values.put("remuneration", remuneration)
        if(startDateChanged) {
            if (date_debut != null) values.put(
                "date_debut",
                "${date_debut?.year}-${date_debut?.month}-${date_debut?.dayOfMonth}"
            )
        }
        if(endDateChanged) {
            if (date_fin != null) values.put(
                "date_fin",
                "${date_fin?.year}-${date_fin?.month}-${date_fin?.dayOfMonth}"
            )
        }
        viewModel.filterByValues(values)

        val advancedFiltersLayout = binding.root.findViewById<View>(R.id.advancedFiltersLinearLayout)
        advancedFiltersLayout.visibility = View.GONE

    }

    fun check_permissions(): Boolean {
        val sharedPref = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)
        val user_id = sharedPref?.getLong("user_id", -1)!!

        if(user_id == -1L){
            Toast.makeText(requireContext(), "Veuillez vous connecter", Toast.LENGTH_SHORT).show()
            return false
        }

        return true;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val advancedFiltersLayout = binding.root.findViewById<View>(R.id.advancedFiltersLinearLayout)
        advancedFiltersLayout.visibility = View.GONE
        endDateChanged = false
        startDateChanged = false
        _binding = null

    }
}