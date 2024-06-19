package org.example

import org.example.models.InventoryItem

class InventoryManager {
    private val inventory: HashMap<Int, InventoryItem> = hashMapOf()

    fun printCurrentInventory() {
        for (i in inventory.values) {
            println(i.toString())
        }
    }

    /**
     * Get an item from the inventory by its id. If the item is found, the function returns an [InventoryItem].
     * If the item is not found, the function returns `null`.
     *
     * @param itemId The id of the item to get
     * @return [InventoryItem]
     */
    fun getInventoryItem(itemId: Int): InventoryItem? {
        return inventory[itemId]
    }

    /**
     * Purchase an item from the inventory
     *
     * @param itemId The id of the item to purchase
     * @param quantity The quantity of the item to purchase
     * @return [Boolean]
     */
    fun purchaseItem(itemId: Int, quantity: Int): Boolean {
        val item = inventory[itemId]

        if (isItemAvailable(itemId, quantity)) {
            updateInventoryItemQuantity(itemId, item!!.quantity - quantity)
            return true
        }

        if (quantity > item!!.quantity && item.quantity > 0) {
            println("Could not purchase item. We only have ${item.quantity} ${item.itemName} in stock.")
            return false
        }

        println("Could not purchase item. Item is out of stock")
        return false
    }

    /**
     * Get an item from the inventory by its id
     *
     * @param itemId The id of the item to get
     * @return [Boolean]
     */
    private fun isItemAvailable(itemId: Int, quantity: Int): Boolean {
        val item = inventory[itemId]
        return item != null && item.quantity > 0 && item.quantity >= quantity
    }

    /**
     * Get the price of an item in the inventory
     *
     * @param itemId The id of the item to get the price for
     * @return [Int]
     */
    fun getInventoryItemPrice(itemId: Int): Int {
        val item = inventory[itemId]
        return item?.price ?: -1
    }

    /**
     * Update the quantity of an item in the inventory
     *
     * @param itemId The id of the item to update
     * @param quantity The new quantity of the item
     */
    private fun updateInventoryItemQuantity(itemId: Int, quantity: Int) {
        val item = inventory[itemId]
        if (item != null) {
            item.quantity = quantity
        }
    }

    private fun prepopulateInventory() {
        val item1 = InventoryItem(id = 1, itemName = "Milk", quantity = 58, price = 44)
        inventory[item1.id] = item1

        val item2 = InventoryItem(id = 2, itemName = "Honey", quantity = 105, price = 162)
        inventory[item2.id] = item2

        val item3 = InventoryItem(id = 3, itemName = "Groundnuts", quantity = 4, price = 357)
        inventory[item3.id] = item3

        val item4 = InventoryItem(id = 4, itemName = "Bread", quantity = 78, price = 41)
        inventory[item4.id] = item4

        val item5 = InventoryItem(id = 5, itemName = "Spinach", quantity = 32, price = 42)
        inventory[item5.id] = item5

        val item6 = InventoryItem(id = 6, itemName = "Towel", quantity = 17, price = 236)
        inventory[item6.id] = item6

        val item7 = InventoryItem(id = 7, itemName = "Soda", quantity = 9, price = 65)
        inventory[item7.id] = item7
    }

    companion object Factory {
        private var instance: InventoryManager? = null
        fun getInstance(): InventoryManager {
            if (instance == null) {
                instance = InventoryManager()
                return instance!!
            }

            return instance!!
        }
    }

    init {
        prepopulateInventory()
    }
}