package com.example.interim.ui.statistiques

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.widget.ArrayAdapter
import android.widget.AdapterView
import com.example.interim.databinding.FragmentOffreStatistiquesBinding
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYPlot
import com.androidplot.xy.XYSeries
import android.util.Log
import androidx.annotation.RequiresApi
import com.androidplot.util.PixelUtils
import com.androidplot.xy.BarFormatter
import com.androidplot.xy.BarRenderer
import com.androidplot.xy.BoundaryMode
import com.androidplot.xy.StepMode
import com.androidplot.xy.XYGraphWidget
import com.example.interim.models.Offre
import com.example.interim.services.OffreService
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.time.format.DateTimeFormatter
import java.util.Arrays
import java.util.Date


class OffreStatistiquesFragment : Fragment() {


    private var _binding: FragmentOffreStatistiquesBinding? = null
    private val binding get() = _binding!!

    lateinit var offres: List<Offre>
    lateinit var metiers: List<String>
    lateinit var metiersCount: List<Int>
    lateinit var employeurs: List<String>
    lateinit var employeursCount: List<Int>
    lateinit var villes: List<String>
    lateinit var villesCount: List<Int>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOffreStatistiquesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinnerPeriod = binding.spinnerPeriod
        val selection = arrayOf("Metier", "Employeur", "Ville", "Week", "Month", "Year")
        val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, selection)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPeriod.adapter = adapter
        spinnerPeriod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.plotView.visibility = View.VISIBLE
                updateStatistics(selection[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.plotView.visibility = View.GONE
            }
        }

        offres = OffreService().readAll()
        Log.d("dates", offres.map{it.created_at }.toString())
        metiers = offres.map { it.metier }.distinct()
        metiersCount = metiers.map { metier -> offres.count { it.metier == metier } }

        employeurs = offres.map { it.employer.getName() }.distinct()
        employeursCount = employeurs.map { employeur -> offres.count { it.employer.getName() == employeur } }

        villes = offres.map { it.ville }.distinct()
        villesCount = villes.map { ville -> offres.count { it.ville == ville } }

        val now = Date()
        val week = 7
        val month = 30
        val year = 365
        val currentDate = Date(now.time)
        val dateFormatter = DateTimeFormatter.ofPattern("YYYY-MM-DDTHH:MM:SS")





        Log.d("metiers", metiers.toString())
        Log.d("metiersCount", metiersCount.toString())
        Log.d("employeurs", employeurs.toString())
        Log.d("employeursCount", employeursCount.toString())
        Log.d("villes", villes.toString())
        Log.d("villesCount", villesCount.toString())
        return root
    }

    private fun updateStatistics(selection: String) {
        val plot: XYPlot = binding.plotView
        plot.clear();


        val series: XYSeries = SimpleXYSeries(
            Arrays.asList(*calculateStatistics(selection)), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, selection
        )

        val bar = BarFormatter(Color.RED, Color.BLACK)
        bar.marginLeft = PixelUtils.dpToPix(1F);
        bar.marginRight = PixelUtils.dpToPix(1F);


        plot.setDomainStep(StepMode.INCREMENT_BY_VAL, 1.0)
        plot.setRangeStep(StepMode.INCREMENT_BY_VAL, 1.0)
        plot.setRangeBoundaries(0, BoundaryMode.FIXED, 10, BoundaryMode.GROW)
        plot.addSeries(series, bar)


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
            "Metier" -> {
                metiersCount.toTypedArray()
            }
            "Employeur" -> {
                employeursCount.toTypedArray()
            }
            "Ville" -> {
                villesCount.toTypedArray()
            }
            else -> emptyArray()
        }
    }

    private fun getDomain(period: String): Array<String> {
        // Perform the necessary calculations and retrieve the statistics data for the selected period
        // This could involve querying your temporary worker and employer records, applying filters based on the selected period, and aggregating the data

        // Dummy data for demonstration
        return when (period) {
            "Metier" -> {
                metiers.toTypedArray()
            }
            "Employeur" -> {
                employeurs.toTypedArray()
            }
            "Ville" -> {
                villes.toTypedArray()
            }
            else -> emptyArray()
        }
    }


}
