package com.example.interim.ui.connection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.interim.MainActivity
import com.example.interim.R
import com.example.interim.database.DataBase
import com.example.interim.ui.LocationActivity

class ConnectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        val database: Unit = DataBase.init(this)
        val sharedPref = getSharedPreferences("interim", Context.MODE_PRIVATE)

        val userId = sharedPref.getLong("user_id", -1)
        val storedExpirationTime = sharedPref.getLong("expirationTime", 0)

        if(userId != -1L && storedExpirationTime > System.currentTimeMillis()){
            val intent = Intent(this, LocationActivity::class.java)
            startActivity(intent)
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.connectionFragmentContainer, ConnectionFragment())
                .commit()
        }

    }

}