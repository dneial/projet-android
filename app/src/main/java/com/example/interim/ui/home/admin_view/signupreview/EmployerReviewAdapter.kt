package com.example.interim.ui.home.admin_view.signupreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.interim.R
import com.example.interim.models.Employer
import com.example.interim.services.UsersService


class EmployerReviewAdapter(private val values: List<Employer>) : RecyclerView.Adapter<EmployerReviewAdapter.ViewHolder>()  {

    var onUpdateButtonClickListener: OnUpdateButtonClickListener? = null

    interface OnUpdateButtonClickListener {
        fun onAcceptButtonClick(item: Employer)
        fun onRefuseButtonClick(item: Employer)
    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var onUpdateButtonClickListener: OnUpdateButtonClickListener? = null

        val cardView: CardView = view.findViewById(R.id.review_card_view)

        fun bind(item: Employer) {
            cardView.id = item.getId().toInt()
            cardView.findViewById<TextView>(R.id.company_name).text = item.getName()
            cardView.findViewById<TextView>(R.id.company_metier).text = item.getService()
            cardView.findViewById<TextView>(R.id.company_sous_metier).text = item.getSubService()
            cardView.findViewById<TextView>(R.id.company_siret).text = item.getSIRET()
            cardView.findViewById<TextView>(R.id.company_name1).text = item.getContact()
            cardView.findViewById<TextView>(R.id.company_email1).text = item.getEmail()
            cardView.findViewById<TextView>(R.id.company_phone1).text = item.getPhone()
            cardView.findViewById<TextView>(R.id.company_name2).text = item.getSubContact()
            cardView.findViewById<TextView>(R.id.company_email2).text = item.getSubEmail()
            cardView.findViewById<TextView>(R.id.company_phone2).text = item.getSubPhone()
            cardView.findViewById<TextView>(R.id.company_address).text = item.getAddress()
            cardView.findViewById<TextView>(R.id.company_comment).text = item.getCommentary()

            cardView.findViewById<Button>(R.id.accept_button).setOnClickListener {
                onUpdateButtonClickListener?.onAcceptButtonClick(item)
            }

            cardView.findViewById<Button>(R.id.reject_button).setOnClickListener {
                onUpdateButtonClickListener?.onRefuseButtonClick(item)
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.signup_request_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
        holder.onUpdateButtonClickListener = onUpdateButtonClickListener
    }

    override fun getItemCount(): Int {
        return values.size
    }
}