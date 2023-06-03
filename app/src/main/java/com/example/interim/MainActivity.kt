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
import androidx.core.view.get
import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        val sharedPref = getSharedPreferences("interim", Context.MODE_PRIVATE) ?: return
        val role = sharedPref.getString("user_role", "null")
        if(role != "admin") navView.menu.removeItem(R.id.navigation_statistiques)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard,
                R.id.navigation_home,
                R.id.navigation_notifications,
                R.id.navigation_statistiques,
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
                R.id.navigation_statistiques -> {
                    clearStackAndNavigateTo(R.id.navigation_statistiques)
                    true
                }
                else -> {
                    Log.d("navigattion id", "onCreate: " + it.itemId)
                    false
                }
            }
        }

        // demander authorisation pour la localisation
    }


    private fun clearStackAndNavigateTo(destinationId: Int) {
        Log.d("clearStackAndNavigateTo", "clearStackAndNavigateTo: " + destinationId)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.popBackStack(navController.graph.startDestinationId, false);
        navController.navigate(destinationId)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}