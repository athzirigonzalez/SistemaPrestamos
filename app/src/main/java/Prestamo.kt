package com.example.sistemaprastamos

data class Prestamo(
    val idPrestamo: Long = 0,
    val idCliente: Long,
    val nombreCliente: String = "",
    val monto: Double,
    val plazoMeses: Int,
    val interesMonto: Double,
    val totalAPagar: Double,
    val pagoMensual: Double,
    val estado: String = "Activo",
    val fechaRegistro: String = ""
)