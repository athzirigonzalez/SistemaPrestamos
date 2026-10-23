package com.example.sistemaprestamos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoanFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_form)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etMonto = findViewById<EditText>(R.id.etMonto)
        val etPlazo = findViewById<EditText>(R.id.etPlazo)

        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            val nombre = etNombre.text.toString()
            val monto = etMonto.text.toString()
            val plazo = etPlazo.text.toString()

            if (nombre.isEmpty() || monto.isEmpty() || plazo.isEmpty()) {
                Toast.makeText(this, "Campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.i("LoanSystem_Log", "Nueva solicitud - Cliente: $nombre")

            val sharedPreferences = getSharedPreferences("LoanSession", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("ultimo_cliente", nombre)
            editor.putString("ultimo_monto", monto)
            editor.putString("ultimo_plazo", plazo)
            editor.apply()

            val intent = Intent(this, StatusActivity::class.java)
            intent.putExtra("MENSAJE", "¡Préstamo exitoso para $nombre!")
            startActivity(intent)
            finish()
        }
    }
}