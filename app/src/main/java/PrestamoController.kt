package com.example.sistemaprastamos

import android.content.ContentValues
import android.content.Context

class PrestamoController(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    // 1. CREATE: Registrar cliente y préstamo con cálculos automáticos
    fun registrarPrestamo(nombreCliente: String, monto: Double, plazo: Int): Long {
        val db = dbHelper.writableDatabase

        // Inserción en la tabla clientes
        val clienteValues = ContentValues().apply {
            put("nombre", nombreCliente)
        }
        val idCliente = db.insert("clientes", null, clienteValues)

        // Reglas de negocio (Cálculos financieros)
        val interes = monto * 0.15
        val total = monto + interes
        val pagoMensual = total / plazo

        // Inserción en la tabla prestamos
        val prestamoValues = ContentValues().apply {
            put("id_cliente", idCliente)
            put("monto", monto)
            put("plazo_meses", plazo)
            put("interes_monto", interes)
            put("total_a_pagar", total)
            put("pago_mensual", pagoMensual)
            put("estado", "Activo")
        }

        return db.insert("prestamos", null, prestamoValues)
    }

    // 2. READ: Consultas parametrizadas con Filtro, Búsqueda y Paginación
    fun obtenerPrestamos(busqueda: String = "", limit: Int = 20, offset: Int = 0): List<Prestamo> {
        val lista = mutableListOf<Prestamo>()
        val db = dbHelper.readableDatabase

        val query = """
            SELECT p.*, c.nombre FROM prestamos p
            INNER JOIN clientes c ON p.id_cliente = c.id_cliente
            WHERE c.nombre LIKE ? OR p.estado LIKE ?
            ORDER BY p.id_prestamo DESC
            LIMIT ? OFFSET ?
        """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf("%$busqueda%", "%$busqueda%", limit.toString(), offset.toString()))

        if (cursor.moveToFirst()) {
            do {
                val prestamo = Prestamo(
                    idPrestamo = cursor.getLong(cursor.getColumnIndexOrThrow("id_prestamo")),
                    idCliente = cursor.getLong(cursor.getColumnIndexOrThrow("id_cliente")),
                    nombreCliente = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    monto = cursor.getDouble(cursor.getColumnIndexOrThrow("monto")),
                    plazoMeses = cursor.getInt(cursor.getColumnIndexOrThrow("plazo_meses")),
                    interesMonto = cursor.getDouble(cursor.getColumnIndexOrThrow("interes_monto")),
                    totalAPagar = cursor.getDouble(cursor.getColumnIndexOrThrow("total_a_pagar")),
                    pagoMensual = cursor.getDouble(cursor.getColumnIndexOrThrow("pago_mensual")),
                    estado = cursor.getString(cursor.getColumnIndexOrThrow("estado")),
                    fechaRegistro = cursor.getString(cursor.getColumnIndexOrThrow("fecha_registro"))
                )
                lista.add(prestamo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }

    // 3. UPDATE: Actualizar estado del préstamo
    fun actualizarEstado(idPrestamo: Long, nuevoEstado: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("estado", nuevoEstado)
        }
        return db.update("prestamos", values, "id_prestamo = ?", arrayOf(idPrestamo.toString()))
    }

    // 4. DELETE: Eliminar préstamo
    fun eliminarPrestamo(idPrestamo: Long): Int {
        val db = dbHelper.writableDatabase
        return db.delete("prestamos", "id_prestamo = ?", arrayOf(idPrestamo.toString()))
    }
}