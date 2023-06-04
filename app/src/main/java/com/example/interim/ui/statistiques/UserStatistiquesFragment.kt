package com.example.interim.ui.statistiques

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidplot.Plot
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
import com.example.interim.services.CandidatureService
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition


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

        val plot: XYPlot = binding.candidaturesStats

        val candidatures = CandidatureService().readAll()
        val metiers = candidatures.map { it.offre.metier }.distinct()
        val metiersCount = metiers.map { metier -> candidatures.count { it.offre.metier == metier } }

        val series1: XYSeries = SimpleXYSeries(
            metiersCount,
            SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
            "Metiers"
        )

        val bar = BarFormatter(Color.RED, Color.GREEN)
        bar.marginLeft = PixelUtils.dpToPix(1F);
        bar.marginRight = PixelUtils.dpToPix(1F);

        plot.setDomainStep(StepMode.INCREMENT_BY_VAL, 1.0)
        plot.setRangeStep(StepMode.INCREMENT_BY_VAL, 1.0)
        plot.addSeries(series1, bar)

        val renderer = plot.getRenderer(BarRenderer::class.java)
        renderer.barOrientation = BarRenderer.BarOrientation.SIDE_BY_SIDE
        renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_WIDTH, PixelUtils.dpToPix(25F));


        plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(object : Format() {
            override fun format(obj: Any, toAppendTo: StringBuffer, pos: FieldPosition?): StringBuffer? {
                val i = Math.round((obj as Number).toFloat())
                Log.d("i", i.toString())
                Log.d("metiers", metiers[i])
                return toAppendTo.append(metiers[i])
            }

            override fun parseObject(source: String?, pos: ParsePosition?): Any? {
                return null
            }
        })

        return root
    }

}
