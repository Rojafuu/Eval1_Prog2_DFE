package com.dfe.restaurant.modelo

class CuentaMesa(val mesa: Int, var aceptaPropina: Boolean = true) {
    // Items privados, no accesibles directamente
    var items: MutableList<ItemMesa> = mutableListOf()

    // Funcion para agregar un ItemMesa
    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val itemMesa = ItemMesa(itemMenu, cantidad)
        items.add(itemMesa)
    }

    // Funcion para acceder a la lista de items
    fun obtenerItems(): List<ItemMesa> {
        return items
    }

    // Funcion para calcular total sin propina
    fun calcularTotalSinPropina(): Int {
        var total = 0
        for (item in items) {
            total += item.calcularSubtotal()
        }
        return total
    }

    // Funcion para calcular propina
    fun calcularPropina(): Int {
        return if (aceptaPropina) calcularTotalSinPropina() * 10 / 100 else 0
    }

    // Funcion para calcular total con propina
    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }

    // Funcion para formatear montos
    fun formatearMonto(monto: Int): String {
        return "$${monto}"
    }
}
