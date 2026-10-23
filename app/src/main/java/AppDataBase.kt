package com.example.sistemaprastamos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class LoanFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_form)

        // Asignaciones e inicializaciones dentro de onCreate
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val nombre = etNombre.text.toString()
    }
}

@AppDatabase(
    entities = [UserEntity::class, PrestamoEntity::class, PagoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun prestamoDao(): PrestamoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sistema_prestamos_db"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateInitialData(database.prestamoDao())
                }
            }
        }

        suspend fun populateInitialData(dao: PrestamoDao) {
            // Seeder de usuarios de prueba
            val clienteId = dao.insertUser(
                UserEntity(nombre = "Athziri González", email = "athziri.gonzalez@cuvalles.udg.mx", rol = "cliente")
            )[cite: 11]
            dao.insertUser(
                UserEntity(nombre = "Gerencia CUValles", email = "admin@cuvalles.udg.mx", rol = "admin")
            )

            // Seeder de préstamo inicial con cálculo de interés fijo al 15%
            val monto = 10000.0
            val plazo = 12
            val tasa = 15.0[cite: 18]
            val interes = monto * (tasa / 100.0)[cite: 18]
            val total = monto + interes[cite: 18]
            val pagoMensual = total / plazo[cite: 18]

            dao.insertPrestamo(
                PrestamoEntity(
                    userId = clienteId,
                    monto = monto,
                    plazoMeses = plazo,
                    tasaInteres = tasa,
                    totalAPagar = total,
                    pagoMensual = pagoMensual,
                    estatus = "aprobado"
                )
            )
        }
    }
}