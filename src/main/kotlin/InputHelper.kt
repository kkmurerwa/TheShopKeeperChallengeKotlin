package org.example

class InputHelper {
    /**
     * Gets non-negative input from the console and returns the value as an integer. If no valid input is provided,
     * the function returns -1.
     *
     * @return [Int]
     */
    fun getConsoleInput(message: String?): Int {
        if (message != null) println(message)

        val input = readln()
        if (isValidIntegerInput(input)) {
            return input.toInt()
        }

        return -1
    }

    private fun isValidIntegerInput(inputString: String): Boolean {
        val intFromString = inputString.toIntOrNull()
        return intFromString != null && intFromString > 0
    }
}