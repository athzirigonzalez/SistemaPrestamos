package com.example.sistemaprestamos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StatusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        val mensajeRequest = intent.getStringExtra("MENSAJE")
        val sharedPreferences = getSharedPreferences("LoanSession", Context.MODE_PRIVATE)
        val cliente = sharedPreferences.getString("ultimo_cliente", "Desconocido")

        findViewById<TextView>(R.id.tvMensajeExito).text = "$mensajeRequest\n\n(Sesión actual: $cliente)"

        findViewById<Button>(R.id.btnVolverInicio).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}