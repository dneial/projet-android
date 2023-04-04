package com.example.interim.ui.dashboard

import android.content.Context
import android.database.DataSetObserver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.interim.R
import com.example.interim.models.Offre

class OffreAdapter(context: Context,
                   private val layoutResource: Int,
                   private val viewId: Int,
                   private val values: List<Offre>) : ArrayAdapter<Offre>(context, viewId, values) {

    override fun getItem(position: Int): Offre = values[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = createViewFromResource(convertView, parent, layoutResource)

        return bindData(getItem(position), view)
    }

    private fun createViewFromResource(convertView: View?, parent: ViewGroup, layoutResource: Int): CardView {
        Log.d("parent", parent.toString())
        val context = parent.context
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutResource, parent, false)
        return try {
            if (viewId == 0) view as CardView
            else {
                view.findViewById(viewId) ?:
                throw RuntimeException("Failed to find view with ID " +
                        "${context.resources.getResourceName(viewId)} in item layout")
            }
        } catch (ex: ClassCastException){
            Log.e("CustomArrayAdapter", "You must supply a resource ID for a TextView")
            throw IllegalStateException(
                "ArrayAdapter requires the resource ID to be a TextView", ex)
        }
    }

    private fun bindData(value: Offre, view: CardView): CardView {
        view.findViewById<TextView>(R.id.titre).text = value.name
        view.findViewById<TextView>(R.id.metier).text = value.metier
        view.findViewById<TextView>(R.id.remuneration).text = value.remuneration

        return view
    }


}
