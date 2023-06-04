package com.example.interim.ui.statistiques

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.widget.ArrayAdapter
import android.widget.AdapterView
import com.example.interim.databinding.FragmentOffreStatistiquesBinding
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYPlot
import com.androidplot.xy.XYSeries
import android.graphics.DashPathEffect
import com.androidplot.util.PixelUtils
import com.androidplot.xy.CatmullRomInterpolator
import com.androidplot.xy.XYGraphWidget
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.Arrays


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

        val spinnerPeriod = binding.spinnerPeriod
        val periods = arrayOf("This Week", "This Month", "This Year")
        val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, periods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPeriod.adapter = adapter
        spinnerPeriod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.plotView.visibility = View.VISIBLE
                updateStatistics(periods[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.plotView.visibility = View.GONE
            }
        }

        return root
    }

    private fun updateStatistics(period: String) {
        val plot: XYPlot = binding.plotView

        val series2: XYSeries = SimpleXYSeries(
            Arrays.asList(*calculateStatistics(period)), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2"
        )

        val series2Format = LineAndPointFormatter(context,  com.example.interim.R.xml.line_point_formatter_with_labels_2)

        series2Format.linePaint.pathEffect = DashPathEffect(
            floatArrayOf(
                PixelUtils.dpToPix(20f),
                PixelUtils.dpToPix(15f)
            ), 0f
        )

        series2Format.interpolationParams =
            CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal)

        plot.addSeries(series2, series2Format)

        plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(object : Format() {
            override fun format(obj: Any, toAppendTo: StringBuffer, pos: FieldPosition?): StringBuffer? {
                val i = Math.round((obj as Number).toFloat())
                return toAppendTo.append(getDomain(period)[i])
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
            "This Week" -> arrayOf(1, 2, 3, 4, 5, 6, 7)
            "This Month" -> arrayOf(5, 10, 15, 20, 25, 30)
            "This Year" -> arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
            else -> emptyArray()
        }
    }

    private fun getDomain(period: String): Array<Number> {
        // Perform the necessary calculations and retrieve the statistics data for the selected period
        // This could involve querying your temporary worker and employer records, applying filters based on the selected period, and aggregating the data

        // Dummy data for demonstration
        return when (period) {
            "This Week" -> arrayOf(1, 2, 3, 4, 5, 6, 7)
            "This Month" -> arrayOf(5, 10, 15, 20, 25, 30)
            "This Year" -> arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
            else -> emptyArray()
        }
    }


}
