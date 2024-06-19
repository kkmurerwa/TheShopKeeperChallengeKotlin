package org.example

import kotlin.math.floor

class ChangeCalculator {
    private val listOfDenominations = arrayListOf(1000,500,200,100,50,20,10,5,1)

    /**
     * Calculates the denominations of the change to be given to the customer.
     *
     * @param change The amount of change to be given to the customer.
     * @return [String]
     */
    fun calculateChange(change: Int): String {
        var amount = change
        val changeSb = StringBuilder()

        for (i in listOfDenominations) {
            val count = floor(amount.toDouble() / i).toInt()
            amount %= i

            if (amount == 0 && changeSb.isNotEmpty()) {
                changeSb.delete(changeSb.length - 2, changeSb.length - 1)
                changeSb.append("and ")
            }

            if (count > 0) {
                changeSb.append("$count $i-shilling ${if (i >= 50) "note" else "coin"}${if (count > 1) "s" else ""}${if (amount > 0) ", " else "."}")
            }

            if (amount == 0) break
        }

        return changeSb.toString()
    }
}