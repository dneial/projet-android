package com.example.interim.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.interim.MainActivity
import com.example.interim.R
import java.io.IOException
import java.util.Locale

class LocationActivity: AppCompatActivity(), LocationListener {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    var manager: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        val ville = getSharedPreferences("interim", Context.MODE_PRIVATE).getString("ville", "")

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
            if(ville == "") {
                manager!!.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0f,
                    this
                )
            } else {
                Log.d("on request 1", "ville != null")
                startActivity(Intent(this, MainActivity::class.java))
            }
        } else {
            showRationaleDialog()
        }
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
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        manager!!.removeUpdates(this)
    }


    private fun showRationaleDialog() {
    val dialogBuilder = AlertDialog.Builder(this)
        .setTitle(resources.getString(R.string.popup_location_title))
        .setMessage(resources.getString(R.string.popup_location_message))
        .setPositiveButton(resources.getString(R.string.popup_location_grant)) { dialogInterface: DialogInterface, i: Int ->
            // Request the permission when the user clicks "Grant"
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            dialogInterface.dismiss()
        }
        .setNegativeButton(resources.getString(R.string.popup_location_deny)) { dialogInterface: DialogInterface, i: Int ->
            // Handle the denial case when the user clicks "Deny"
            startActivity(Intent(this, MainActivity::class.java))
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
                val ville = getSharedPreferences("interim", Context.MODE_PRIVATE).getString("ville", "")
                if(ville == "") {
                    manager!!.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        0f,
                        this
                    )
                } else {
                    Log.d("on reequest", "ville != null")
                    startActivity(Intent(this, MainActivity::class.java))
                }
            } else {
                // Permission denied
                // Handle the denial case
                // ...
            }
        }
    }
}