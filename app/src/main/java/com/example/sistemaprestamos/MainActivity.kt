package com.example.sistemaprestamos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnNuevoPrestamo).setOnClickListener {
            startActivity(Intent(this, LoanFormActivity::class.java))
        }

        findViewById<Button>(R.id.btnVerDashboard).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        findViewById<Button>(R.id.btnVerManager).setOnClickListener {
            startActivity(Intent(this, ManagerActivity::class.java))
        }
    }
}