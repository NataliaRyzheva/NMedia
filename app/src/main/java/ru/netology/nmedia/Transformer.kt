package ru.netology.nmedia

import java.text.DecimalFormat

object Transformer {

    object Transform {
        fun intTransform(action: Int): String {
            return when (action) {
                in 0..999 -> action.toString()
                in 1000..1099 -> "1K"
                in 1100..9999 -> calculation(action, 1) + "K"
                in 10000..999999 -> calculation(action, 0) + "K"
                in 1000000..999999999 -> calculation(action / 1000, 1) + "M"
                else -> "More billion"
            }
        }

        private fun calculation(action: Int, places: Int): String {
            val decimalFormat: DecimalFormat = if (places == 1) {
                DecimalFormat("###.#")
            } else {
                DecimalFormat("###")
            }
            val actions: Double = action.toDouble() / 1000
            return decimalFormat.format(actions)
        }
    }
}
