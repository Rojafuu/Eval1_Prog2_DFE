package com.dfe.restaurant.modelo

data class ItemMesa(
    val itemMenu: ItemMenu,
    var cantidad: Int
) {
    // Calcula el subtotal de este platillo (cantidad * precio)
    fun calcularSubtotal(): Int {
        return itemMenu.precio.toInt() * cantidad
    }
}
