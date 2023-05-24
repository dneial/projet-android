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
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        // demander authorisation pour la localisation
        manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("permission", "granted")
            manager!!.requestSingleUpdate(LocationManager.GPS_PROVIDER,this, null)


        } else {
            // Permission is not granted
            // Check if the rationale for permission should be shown
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show a custom rationale message explaining why the permission is needed
                Log.d("permission", "rationale")
                showRationaleDialog()

            } else {
                Log.d("permission", "request")
                // Request the permission without showing the rationale message
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
        var enabled = manager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onLocationChanged(location: Location) {
        Log.d("location", location.toString())
        val latitude = location.latitude
        val longitude = location.longitude

        // Reverse geocoding
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address> =
                geocoder.getFromLocation(latitude, longitude, 1) as List<Address>
            if (addresses.isNotEmpty()) {
                val cityName = addresses[0].locality
                // Do something with the city name
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


    private fun showRationaleDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Location Permission")
            .setMessage("This app requires access to your location to provide offers based on your locationf.")
            .setPositiveButton("Grant") { dialogInterface: DialogInterface, i: Int ->
                // Request the permission when the user clicks "Grant"
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
                dialogInterface.dismiss()
            }
            .setNegativeButton("Deny") { dialogInterface: DialogInterface, i: Int ->
                // Handle the denial case when the user clicks "Deny"
                dialogInterface.dismiss()
            }
            .setCancelable(false)

        dialogBuilder.create().show()
    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                // Request location updates
                manager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
            } else {
                // Permission denied
                // Handle the denial case
                // ...
                Log.d("permission", "denied")
            }
        }
    }
}