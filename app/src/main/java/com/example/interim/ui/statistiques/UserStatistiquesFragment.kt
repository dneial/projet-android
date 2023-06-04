package com.example.interim.ui.statistiques

import android.R
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
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
import com.example.interim.models.User
import com.example.interim.services.UsersService
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Arrays
import java.util.Locale


class UserStatistiquesFragment : Fragment() {

    private var _binding: FragmentUserStatistiquesBinding? = null
    private val binding get() = _binding!!

    lateinit var usersFromLastWeek: List<User>
    lateinit var usersFromLastMonth: List<User>
    lateinit var usersFromLastYear: List<User>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentUserStatistiquesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinnerPeriod = binding.spinnerPeriod
        val selection = arrayOf("Week", "Month", "Year")
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

        val usersService = UsersService()
        usersFromLastWeek = usersService.getUsersFrom("week")
        usersFromLastMonth = usersService.getUsersFrom("month")
        usersFromLastYear = usersService.getUsersFrom("year")

        return root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateStatistics(selection: String) {
        val plot: XYPlot = binding.usersPlotView
        plot.clear();


        val series1: XYSeries = SimpleXYSeries(
            Arrays.asList(*calculateWorkerStatistics(selection)), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, selection
        )

        val series2: XYSeries = SimpleXYSeries(
            Arrays.asList(*calculateEmployerStatistics(selection)), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, selection
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
            @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateWorkerStatistics(period: String): Array<Number> {
        // Perform the necessary calculations and retrieve the statistics data for the selected period
        // This could involve querying your temporary worker and employer records, applying filters based on the selected period, and aggregating the data

        // Dummy data for demonstration
        return when (period) {
            "Week" -> {
               getUserByWeekDay(usersFromLastWeek).toTypedArray()
            }
            "Month" -> {
                getUsersByMonth(usersFromLastMonth).toTypedArray()
            }
            "Year" -> {
                arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            }
            else -> emptyArray()
        }
    }

    private fun calculateEmployerStatistics(period: String): Array<Number> {
        // Perform the necessary calculations and retrieve the statistics data for the selected period
        // This could involve querying your temporary worker and employer records, applying filters based on the selected period, and aggregating the data

        // Dummy data for demonstration
        return when (period) {
            "Week" -> {
                arrayOf(1, 3)
            }
            "Month" -> {
                arrayOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
            }
            "Year" -> {
                arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            }
            else -> emptyArray()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDomain(period: String): Array<String> {
        // Perform the necessary calculations and retrieve the statistics data for the selected period
        // This could involve querying your temporary worker and employer records, applying filters based on the selected period, and aggregating the data

        // Dummy data for demonstration
        return when (period) {
            "Week" -> {
                val today = LocalDate.now()
                val weekdays = (0 until 7).map { i ->
                    val date = today.minusDays(i.toLong())
                    date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
                }.reversed()
                weekdays.toTypedArray()
            }
            "Year" -> {
                val today = LocalDate.now()
                val months = (0 until 12).map { i ->
                    val date = today.minusMonths(i.toLong())
                    date.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
                }.reversed()
                months.toTypedArray()
            }
            "Month" -> {
               arrayOf("Ville 1", "Ville 2", "Ville 3", "Ville 4", "Ville 5", "Ville 6", "Ville 7", "Ville 8", "Ville 9", "Ville 10")
            }
            else -> emptyArray()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getUserByWeekDay(users: List<User>): List<Int> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss")
        val userCountByDayOfWeek = Array(7) { 0 }
        for(user in users){
            val date = LocalDateTime.parse(user.getDateCreation(), formatter)
            val dayOfWeek = date.dayOfWeek.value % 7
            userCountByDayOfWeek[dayOfWeek]++;
        }
        return userCountByDayOfWeek.toList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUsersByMonth(users: List<User>): List<Int> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss")
        val userCountByMonth = Array(12) { 0 }
        for(user in users){
            val date = LocalDateTime.parse(user.getDateCreation(), formatter)
            val month = date.monthValue - 1
            userCountByMonth[month]++;
        }
        return userCountByMonth.toList()
    }

}
