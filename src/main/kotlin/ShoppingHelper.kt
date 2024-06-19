package org.example

class ShoppingHelper {
    private val inventoryManager = InventoryManager.getInstance()
    private val inputHelper = InputHelper()
    private val changeCalculator = ChangeCalculator()
    private var totalAmountSpent = 0
    private val cartMap = mutableMapOf<Int, Int>()

    /**
     * Starts the shopping process
     *
     * @param failureCount The number of times the user has entered an invalid option
     */
    fun startShopping(failureCount: Int = 0) {
        myprintln()
        inventoryManager.printCurrentInventory()

        val itemToBuy = inputHelper.getConsoleInput("Please enter the ID of the item you want to buy")
        val quantity = inputHelper.getConsoleInput("Please enter the quantity of the item you want to buy")

        if (itemToBuy == -1 || quantity == -1) {
            failureCountValidation(
                failureCount = failureCount,
                block = {
                    println("Please enter valid ${if (itemToBuy == -1) "item" else "quantity"}.")
                    startShopping(failureCount = failureCount + 1)
                }
            )
        }

        val itemPurchased = inventoryManager.purchaseItem(itemToBuy, quantity)

        if (itemPurchased) {
            totalAmountSpent += inventoryManager.getInventoryItemPrice(itemToBuy) * quantity
            cartMap[itemToBuy] = cartMap.getOrDefault(itemToBuy, 0) + quantity
            println("Item purchased successfully. Total amount spent: $totalAmountSpent shillings. Do you want to buy another item? (yes/no)")
            shouldBuyAnotherItem()
        } else {
            println("Do you want to buy another item? (yes/no)")
            shouldBuyAnotherItem()
        }
    }

    private fun printShoppingCart() {
        myprintln("Items in cart:")
        println("---------------------------------")
        for ((itemId, quantity) in cartMap) {
            val item = inventoryManager.getInventoryItem(itemId)
            println("${item!!.itemName}: ${item.price} shillings x $quantity")
        }
        println("---------------------------------")
        println("Total: $totalAmountSpent shillings.")
        println("---------------------------------")
    }

    /**
     * Asks the user if they want to buy another item
     *
     * @param failureCount The number of times the user has entered an invalid option
     */
    private fun shouldBuyAnotherItem(failureCount: Int = 0) {
        val wantToBuyAnotherItem = readln()

        when (wantToBuyAnotherItem) {
            "yes" -> {
                startShopping()
            }
            "no" -> {
                println("Total amount due is $totalAmountSpent shillings.")
                processPayment()
            }
            else -> {
                failureCountValidation(
                    failureCount = failureCount,
                    block = {
                        println("Invalid input. Please enter 'yes' or 'no'.")
                        shouldBuyAnotherItem(failureCount = failureCount + 1)
                    }
                )
            }
        }
    }

    /**
     * Process the payment for the items purchased
     *
     * @param failureCount The number of times the user has entered an invalid option
     */
    private fun processPayment(failureCount: Int = 0) {
        printShoppingCart()

        val amountPaid = inputHelper.getConsoleInput("Please enter the amount of money you are paying with.")

        if (amountPaid == -1) {
            failureCountValidation(
                failureCount = failureCount,
                block = {
                    println("Please enter a valid amount.")
                    processPayment(failureCount = failureCount + 1)
                }
            )
        }

        if (amountPaid < totalAmountSpent) {
            println("Insufficient amount. Please try again with a valid amount.")
            processPayment()
            return
        }

        val change = amountPaid - totalAmountSpent
        println("Thank you for shopping with us. Your change of $change will be handed to you as ${changeCalculator.calculateChange(change)}")
    }

    /**
     * Validates the failure count and executes the block if the failure count is less than 2.
     *
     * @param failureCount The number of times the user has entered an invalid option
     * @param block The block of code to execute if the failure count is less than 2
     */
    private fun failureCountValidation(failureCount: Int, block: () -> Unit) {
        if (failureCount >= 2) {
            println("You have entered an invalid option too many times. Exiting shopping.")
            return
        }

        block.invoke()
    }
}