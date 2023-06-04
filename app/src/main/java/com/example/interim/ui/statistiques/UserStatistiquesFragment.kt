package com.example.interim.ui.statistiques

import android.R
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.androidplot.util.PixelUtils
import com.androidplot.xy.BarFormatter
import com.androidplot.xy.BarRenderer
import com.androidplot.xy.BoundaryMode
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.StepMode
import com.androidplot.xy.XYGraphWidget
import com.androidplot.xy.XYPlot
import com.androidplot.xy.XYSeries
import com.example.interim.databinding.FragmentUserStatistiquesBinding
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.Arrays


class UserStatistiquesFragment : Fragment() {

    private var _binding: FragmentUserStatistiquesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserStatistiquesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val spinnerPeriod = binding.spinnerPeriod
        val selection = arrayOf("Semaine", "Mois", "Année")
        val adapter = ArrayAdapter(context!!, R.layout.simple_spinner_item, selection)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPeriod.adapter = adapter
        spinnerPeriod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.usersPlotView.visibility = View.VISIBLE
                updateStatistics(selection[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.usersPlotView.visibility = View.GONE
            }
        }


        return root
    }


    private fun updateStatistics(selection: String) {
        val plot: XYPlot = binding.usersPlotView
        plot.clear();


        val series1: XYSeries = SimpleXYSeries(
            Arrays.asList(*calculateStatistics(selection)), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, selection
        )



        val bar = BarFormatter(Color.RED, Color.BLACK)
        bar.marginLeft = PixelUtils.dpToPix(1F);
        bar.marginRight = PixelUtils.dpToPix(1F);



        plot.setDomainStep(StepMode.INCREMENT_BY_VAL, 1.0)
        plot.setRangeStep(StepMode.INCREMENT_BY_VAL, 1.0)
        plot.setRangeBoundaries(0, BoundaryMode.FIXED, 10, BoundaryMode.GROW)

        plot.addSeries(series1, bar)


        val renderer = plot.getRenderer(BarRenderer::class.java)
        renderer.barOrientation = BarRenderer.BarOrientation.SIDE_BY_SIDE
        renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_WIDTH, PixelUtils.dpToPix(25F))


        plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(object : Format() {
            override fun format(obj: Any, toAppendTo: StringBuffer, pos: FieldPosition?): StringBuffer? {
                val i = Math.round((obj as Number).toFloat())
                return toAppendTo.append(getDomain(selection)[i])
            }

            override fun parseObject(source: String?, pos: ParsePosition?): Any? {
                return null
            }
        })
        plot.redraw()
    }

    private fun calculateStatistics(period: String): Array<Number> {
        // Perform the necessary calculations and retrieve the statistics data for the selected period
        // This could involve querying your temporary worker and employer records, applying filters based on the selected period, and aggregating the data

        // Dummy data for demonstration
        return when (period) {
            "Semaine" -> {
               arrayOf(1, 3)
            }
            "Mois" -> {
                arrayOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
            }
            "Année" -> {
                arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            }
            else -> emptyArray()
        }
    }

    private fun getDomain(period: String): Array<String> {
        // Perform the necessary calculations and retrieve the statistics data for the selected period
        // This could involve querying your temporary worker and employer records, applying filters based on the selected period, and aggregating the data

        // Dummy data for demonstration
        return when (period) {
            "Semaine" -> {
                arrayOf("Employer", "Temporary Worker")
            }
            "Mois" -> {
                arrayOf("Employeur 1", "Employeur 2", "Employeur 3", "Employeur 4", "Employeur 5", "Employeur 6", "Employeur 7", "Employeur 8", "Employeur 9", "Employeur 10")
            }
            "Année" -> {
               arrayOf("Ville 1", "Ville 2", "Ville 3", "Ville 4", "Ville 5", "Ville 6", "Ville 7", "Ville 8", "Ville 9", "Ville 10")
            }
            else -> emptyArray()
        }
    }

}
