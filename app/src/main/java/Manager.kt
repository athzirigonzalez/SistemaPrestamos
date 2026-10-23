package com.example.sistemaprastamos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ManagerActivity : AppCompatActivity() {

    private lateinit var controller: PrestamoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)

        controller = PrestamoController(this)

        // Cargar registros almacenados
        consultarRegistros()
    }

    private fun consultarRegistros(filtro: String = "") {
        // Ejecuta la consulta paginada (Limit 10, Offset 0)
        val listaPrestamos = controller.obtenerPrestamos(busqueda = filtro, limit = 10, offset = 0)

        if (listaPrestamos.isNotEmpty()) {
            val primerRegistro = listaPrestamos[0]
            // Vincular datos a elementos visuales de la actividad
        }
    }

    // Operación UPDATE: Cambiar estado
    fun marcarComoPagado(idPrestamo: Long) {
        val actualizados = controller.actualizarEstado(idPrestamo, "Pagado")
        if (actualizados > 0) {
            Toast.makeText(this, "Préstamo marcado como Pagado", Toast.LENGTH_SHORT).show()
            consultarRegistros()
        }
    }

    // Operación DELETE: Eliminar solicitud
    fun eliminarRegistro(idPrestamo: Long) {
        val eliminados = controller.eliminarPrestamo(idPrestamo)
        if (eliminados > 0) {
            Toast.makeText(this, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show()
            consultarRegistros()
        }
    }
}