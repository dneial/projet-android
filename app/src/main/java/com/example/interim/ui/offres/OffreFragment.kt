package com.example.interim.ui.offres

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.cardview.widget.CardView
import com.example.interim.R
import com.example.interim.databinding.FragmentDashboardBinding
import com.example.interim.databinding.OffreCardBinding
import com.example.interim.ui.dashboard.DashboardViewModel
import com.example.interim.ui.dashboard.OffreAdapter

class OffreFragment : Fragment() {

    companion object {
        fun newInstance() = OffreFragment()
    }

    private var _binding: OffreCardBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: OffreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val offreViewModel =
            ViewModelProvider(this)[OffreViewModel::class.java]

        _binding = OffreCardBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val view:  CardView = binding.cardView
        binding.titre.text = arguments?.getString("offre_title")
        binding.metier.text = arguments?.getString("offre_description")
        binding.remuneration.text = arguments?.getString("offre_remuneration")



        return root


    }



}