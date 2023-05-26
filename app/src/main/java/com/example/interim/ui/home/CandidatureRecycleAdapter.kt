package com.example.interim.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import com.example.interim.R
import com.example.interim.database.CandidatureService
import com.example.interim.models.Candidature
import com.example.interim.models.Offre

class CandidatureRecycleAdapter(private val values: List<Candidature>) : RecyclerView.Adapter<CandidatureRecycleAdapter.ViewHolder>() {

    var CandidatureService: CandidatureService = CandidatureService()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.candidature_card_view)

        fun bind(item: Candidature) {
            Log.d("CandidatureRecycleAdapter", "bind: ${item.id}")
            cardView.id = item.id.toInt()
            cardView.findViewById<TextView>(R.id.titre).text = item.offre.title
            cardView.findViewById<TextView>(R.id.date).text = item.date
            cardView.findViewById<TextView>(R.id.status).text = item.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.candidature_card, parent, false)


        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
        holder.cardView.setOnClickListener {
            val candidatureId = it.id

            it.findNavController().navigate(
                R.id.action_navigation_home_to_navigation_candidature_detail,

                Bundle().apply {
                    putLong("candidature_id", candidatureId.toLong())
                },

                navOptions { // Use the Kotlin DSL for building NavOptions
                    anim {
                        enter = android.R.animator.fade_in
                        exit = android.R.animator.fade_out
                    }
                }
            )
        }
    }
}