package com.example.interim.ui.dashboard

import android.content.Context
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
import com.example.interim.models.Offre

class OffreRecycleAdapter(private val values: List<Offre>) : RecyclerView.Adapter<OffreRecycleAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.card_view)
        val nameView: TextView = view.findViewById(R.id.titre)
        val metierView: TextView = view.findViewById(R.id.metier)
        val remunerationView: TextView = view.findViewById(R.id.remuneration)

        fun bind(item: Offre) {
            cardView.id = item.id.toInt()
            nameView.text = item.title
            metierView.text = item.metier
            remunerationView.text = item.remuneration
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.offre_card, parent, false)


        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
        holder.cardView.setOnClickListener { it ->
            val offreId = it.id
            val offre = values.find { it.id.toInt() == offreId }

                it.findNavController().navigate(
                    R.id.action_navigation_dashboard_to_navigation_offres,

                    Bundle().apply {
                        putParcelable("offre", offre)
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