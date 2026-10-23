package com.example.sistemaprastamos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "SistemaPrestamos.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Migración 1: Tabla Clientes
        val createClientesTable = """
            CREATE TABLE clientes (
                id_cliente INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                correo TEXT,
                telefono TEXT
            );
        """.trimIndent()

        // Migración 2: Tabla Préstamos con Integridad Referencial
        val createPrestamosTable = """
            CREATE TABLE prestamos (
                id_prestamo INTEGER PRIMARY KEY AUTOINCREMENT,
                id_cliente INTEGER NOT NULL,
                monto REAL NOT NULL,
                plazo_meses INTEGER NOT NULL,
                interes_monto REAL NOT NULL,
                total_a_pagar REAL NOT NULL,
                pago_mensual REAL NOT NULL,
                estado TEXT NOT NULL DEFAULT 'Activo',
                fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY(id_cliente) REFERENCES clientes(id_cliente) ON DELETE CASCADE
            );
        """.trimIndent()

        db.execSQL(createClientesTable)
        db.execSQL(createPrestamosTable)

        // Seeders: Registros iniciales de prueba
        db.execSQL("INSERT INTO clientes (nombre, correo, telefono) VALUES ('Cliente Demo', 'demo@correo.com', '3300000000');")
        db.execSQL("INSERT INTO prestamos (id_cliente, monto, plazo_meses, interes_monto, total_a_pagar, pago_mensual, estado) VALUES (1, 1000.0, 12, 150.0, 1150.0, 95.83, 'Activo');")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS prestamos")
        db.execSQL("DROP TABLE IF EXISTS clientes")
        onCreate(db)
    }
}