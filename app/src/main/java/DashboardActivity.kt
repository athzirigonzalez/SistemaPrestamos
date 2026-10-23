package com.example.sistemaprestamos

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val sharedPreferences = getSharedPreferences("LoanSession", Context.MODE_PRIVATE)

        findViewById<TextView>(R.id.tvDashNombre).text = sharedPreferences.getString("ultimo_cliente", "Sin datos")
        findViewById<TextView>(R.id.tvDashMonto).text = "Monto Autorizado: $${sharedPreferences.getString("ultimo_monto", "0.00")}"
        findViewById<TextView>(R.id.tvDashPlazo).text = "Plazo: ${sharedPreferences.getString("ultimo_plazo", "0")} meses"

        findViewById<Button>(R.id.btnDashVolver).setOnClickListener {
            finish()
        }
    }
}