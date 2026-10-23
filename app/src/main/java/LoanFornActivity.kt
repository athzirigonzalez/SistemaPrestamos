package com.example.sistemaprastamos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoanFormActivity : AppCompatActivity() {

    private lateinit var controller: PrestamoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_form)

        controller = PrestamoController(this)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etMonto = findViewById<EditText>(R.id.etMonto)
        val etPlazo = findViewById<EditText>(R.id.etPlazo)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val montoStr = etMonto.text.toString().trim()
            val plazoStr = etPlazo.text.toString().trim()

            // 1. Validación de campos obligatorios
            if (nombre.isEmpty() || montoStr.isEmpty() || plazoStr.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val monto = montoStr.toDoubleOrNull()
            val plazo = plazoStr.toIntOrNull()

            // 2. Validación de rangos permitidos
            if (monto == null || monto <= 0) {
                Toast.makeText(this, "El monto debe ser un número mayor a $0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (plazo == null || plazo <= 0) {
                Toast.makeText(this, "El plazo debe ser de al menos 1 mes", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 3. Guardado en SQLite
            val idPrestamo = controller.registrarPrestamo(nombre, monto, plazo)

            if (idPrestamo > -1) {
                Toast.makeText(this, "Préstamo guardado correctamente en la Base de Datos", Toast.LENGTH_SHORT).show()

                // Redireccionar a la pantalla de confirmación
                val intent = Intent(this, StatusActivity::class.java)
                intent.putExtra("CLIENTE_NOMBRE", nombre)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al guardar el préstamo", Toast.LENGTH_SHORT).show()
            }
        }
    }
}