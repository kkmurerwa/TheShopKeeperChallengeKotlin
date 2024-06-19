package org.example.models

data class InventoryItem(
    val id: Int,
    val itemName: String,
    var quantity: Int,
    val price: Int
) {
    override fun toString(): String {
        return "$id. $itemName, price: $price shillings, quantity: $quantity"
    }
}
