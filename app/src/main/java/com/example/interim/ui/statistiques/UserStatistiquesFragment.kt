package com.example.interim.ui.statistiques

import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidplot.util.PixelUtils
import com.androidplot.xy.CatmullRomInterpolator
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.SimpleXYSeries
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

        val plot: XYPlot = binding.plot

        val domainLabels = arrayOf<Number>(1, 2, 3, 6, 7, 8, 9, 10, 13, 14)
        val series1Numbers = arrayOf<Number>(1, 4, 2, 8, 4, 16, 8, 32, 16, 64)
        val series2Numbers = arrayOf<Number>(5, 2, 10, 5, 20, 10, 40, 20, 80, 40)

        val series1: XYSeries = SimpleXYSeries(
            Arrays.asList<Number>(*series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1"
        )
        val series2: XYSeries = SimpleXYSeries(
            Arrays.asList<Number>(*series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2"
        )

        val series1Format = LineAndPointFormatter(context, com.example.interim.R.xml.line_point_formatter_with_labels)

        val series2Format = LineAndPointFormatter(context,  com.example.interim.R.xml.line_point_formatter_with_labels_2)

        // add an "dash" effect to the series2 line:
        // add an "dash" effect to the series2 line:
        series2Format.linePaint.pathEffect = DashPathEffect(
            floatArrayOf( // always use DP when specifying pixel sizes, to keep things consistent across devices:
                PixelUtils.dpToPix(20f),
                PixelUtils.dpToPix(15f)
            ), 0f
        )

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.interpolationParams =
            CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal)

        series2Format.interpolationParams =
            CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal)

        // add a new series' to the xyplot:

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format)
        plot.addSeries(series2, series2Format)

        plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(object : Format() {
            override fun format(obj: Any, toAppendTo: StringBuffer, pos: FieldPosition?): StringBuffer? {
                val i = Math.round((obj as Number).toFloat())
                return toAppendTo.append(domainLabels[i])
            }

            override fun parseObject(source: String?, pos: ParsePosition?): Any? {
                return null
            }
        })



        return root
    }

}
