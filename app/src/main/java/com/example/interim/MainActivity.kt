package com.example.interim

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.interim.databinding.ActivityMainBinding

import android.location.Address
import android.location.Geocoder
import android.util.Log
import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var binding: ActivityMainBinding

    var manager: LocationManager? = null


    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard,
                R.id.navigation_home,
                R.id.navigation_notifications,
                R.id.navigation_compte
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    clearStackAndNavigateTo(R.id.navigation_home)
                    true
                }
                R.id.navigation_dashboard -> {
                    clearStackAndNavigateTo(R.id.navigation_dashboard)
                    true
                }
                R.id.navigation_notifications -> {
                    clearStackAndNavigateTo(R.id.navigation_notifications)
                    true
                }
                R.id.navigation_compte -> {
                    clearStackAndNavigateTo(R.id.navigation_compte)
                    true
                }
                else -> false
            }
        }

        // demander authorisation pour la localisation
    }

    override fun onLocationChanged(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude

        // Reverse geocoding
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address> =
                geocoder.getFromLocation(latitude, longitude, 1) as List<Address>
            if (addresses.isNotEmpty()) {
                val cityName = addresses[0].locality
                val sharedPref = getSharedPreferences("interim", Context.MODE_PRIVATE) ?: return
                with (sharedPref.edit()) {
                    putString("ville", cityName)
                    commit()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        manager!!.removeUpdates(this)
    }

    private fun clearStackAndNavigateTo(destinationId: Int) {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.popBackStack(navController.graph.startDestinationId, false);
        navController.navigate(destinationId)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}