package com.dfe.restaurant

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dfe.restaurant.modelo.CuentaMesa
import com.dfe.restaurant.modelo.ItemMenu

class MainActivity : AppCompatActivity() {

    private lateinit var cuentaMesa: CuentaMesa
    private lateinit var textViewSubtotalPastel: TextView
    private lateinit var textViewSubtotalCazuela: TextView
    private lateinit var textViewTotal: TextView
    private lateinit var textViewPropina: TextView
    private lateinit var textViewResultComida: TextView
    private lateinit var switchPropina: Switch
    private lateinit var editTextCantidadPastel: EditText
    private lateinit var editTextCantidadCazuela: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuración de la vista de padding para el sistema de barras
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización de la cuenta de la mesa
        cuentaMesa = CuentaMesa(mesa = 1)

        // Inicializar las vistas
        textViewSubtotalPastel = findViewById(R.id.textViewValorPastel)
        textViewSubtotalCazuela = findViewById(R.id.textViewValorCazuela)
        textViewTotal = findViewById(R.id.textViewResultTotal)
        textViewPropina = findViewById(R.id.textViewResultPropina)
        textViewResultComida = findViewById(R.id.textViewResultComida) // Inicializado aquí
        switchPropina = findViewById(R.id.switchPropina)
        editTextCantidadPastel = findViewById(R.id.editTextNumber)
        editTextCantidadCazuela = findViewById(R.id.editTextNumber2)

        // Menú de platillos
        val pastelMenu = ItemMenu("Pastel de Choclo", "12000")
        val cazuelaMenu = ItemMenu("Cazuela", "10000")

        // Agregar los ítems a la cuenta
        cuentaMesa.agregarItem(pastelMenu, 0)  // Inicializamos con cantidad 0
        cuentaMesa.agregarItem(cazuelaMenu, 0) // Inicializamos con cantidad 0

        // Observadores para cambiar la cantidad de los platillos
        editTextCantidadPastel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                cuentaMesa.obtenerItems()[0].cantidad = cantidad
                actualizarTotales()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        editTextCantidadCazuela.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                cuentaMesa.obtenerItems()[1].cantidad = cantidad
                actualizarTotales()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Cambiar la aceptación de la propina
        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            actualizarTotales()
        }
    }

    // Función para actualizar los totales en la UI
    private fun actualizarTotales() {
        // Obtener los ítems de la cuenta
        val items = cuentaMesa.obtenerItems()

        // Actualizar el subtotal de cada platillo
        textViewSubtotalPastel.text = cuentaMesa.formatearMonto(items[0].calcularSubtotal())
        textViewSubtotalCazuela.text = cuentaMesa.formatearMonto(items[1].calcularSubtotal())

        // Calcular el total sin propina y actualizar el TextView correspondiente
        val totalSinPropina = cuentaMesa.calcularTotalSinPropina()
        textViewResultComida.text = cuentaMesa.formatearMonto(totalSinPropina)

        // Calcular la propina
        val propina = cuentaMesa.calcularPropina()
        textViewPropina.text = cuentaMesa.formatearMonto(propina)

        // Calcular el total con propina
        val totalConPropina = cuentaMesa.calcularTotalConPropina()
        textViewTotal.text = cuentaMesa.formatearMonto(totalConPropina)
    }
}
