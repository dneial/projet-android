package com.example.interim.ui.connection

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.interim.R
import com.example.interim.database.DataBase

class ConnectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        val database: Unit = DataBase.init(this)


        supportFragmentManager.beginTransaction()
            .add(R.id.connectionFragmentContainer, ConnectionFragment())
            .commit()


    }

}