package com.example.sistemaprestamos

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)

        val sharedPreferences = getSharedPreferences("LoanSession", Context.MODE_PRIVATE)
        val cliente = sharedPreferences.getString("ultimo_cliente", "Ninguno") ?: "Ninguno"

        val monto = (sharedPreferences.getString("ultimo_monto", "0") ?: "0").toDoubleOrNull() ?: 0.0
        val plazo = (sharedPreferences.getString("ultimo_plazo", "1") ?: "1").toIntOrNull() ?: 1

        val interesGenerado = monto * 0.15
        val totalAPagar = monto + interesGenerado
        val pagoMensual = totalAPagar / plazo
        val nuevaCarteraTotal = 150000.0 + monto

        findViewById<TextView>(R.id.tvMgrCliente).text = "Cliente: $cliente"
        findViewById<TextView>(R.id.tvMgrMontoBase).text = "Capital: $${String.format("%.2f", monto)}"
        findViewById<TextView>(R.id.tvMgrPlazo).text = "Plazo: $plazo meses"
        findViewById<TextView>(R.id.tvMgrInteres).text = "Interés Generado (15%): $${String.format("%.2f", interesGenerado)}"
        findViewById<TextView>(R.id.tvMgrTotalPago).text = "Total a Pagar: $${String.format("%.2f", totalAPagar)}"
        findViewById<TextView>(R.id.tvMgrMensualidad).text = "Pago Mensual: $${String.format("%.2f", pagoMensual)}"
        findViewById<TextView>(R.id.tvMgrNuevoSaldo).text = "Nueva Cartera Total: $${String.format("%.2f", nuevaCarteraTotal)}"

        findViewById<Button>(R.id.btnMgrVolver).setOnClickListener {
            finish()
        }
    }
}