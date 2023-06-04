package com.example.interim.ui.home.admin_view.signalements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.interim.R
import com.example.interim.models.Employer
import com.example.interim.models.Report
import com.example.interim.services.ReportsService
import com.example.interim.ui.home.CandidatureRecycleAdapter
import com.example.interim.ui.home.admin_view.signupreview.EmployerReviewAdapter

class SignalementRecycleAdapter(private val values: List<Report>) : RecyclerView.Adapter<SignalementRecycleAdapter.ViewHolder>() {

    interface OnReportClickListener {
        fun onReportClick(item: Report)
    }

    lateinit var onReportClickListener: OnReportClickListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var onReportClickListener: OnReportClickListener

        val cardView: View = view.findViewById(R.id.report_card)

        fun bind(item: Report) {
            cardView.id = item.id.toInt()
            cardView.findViewById<android.widget.TextView>(R.id.report_card_content).text = item.comment
            cardView.findViewById<android.widget.TextView>(R.id.report_card_user).text = item.email

            cardView.findViewById<Button>(R.id.report_card_go_to_offer).setOnClickListener {
                it.findNavController().navigate(
                    R.id.navigation_offre,
                    Bundle().apply {
                        putLong("offre_id", item.id)
                    }
                )
            }

            cardView.findViewById<Button>(R.id.report_ok).setOnClickListener {
                onReportClickListener.onReportClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.report_card, parent, false)


        return SignalementRecycleAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
        holder.onReportClickListener = onReportClickListener
    }
}