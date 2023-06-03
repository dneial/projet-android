package com.example.interim.ui.statistiques

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.interim.databinding.FragmentOffreStatistiquesBinding

class OffreStatistiquesFragment : Fragment() {


    private var _binding: FragmentOffreStatistiquesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOffreStatistiquesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewModel: OffreStatistiquesViewModel =
            ViewModelProvider(this)[OffreStatistiquesViewModel::class.java]

        return root
    }


}
