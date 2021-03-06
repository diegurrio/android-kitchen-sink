package com.diego.android.kitchen.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener {
            rollDice()
        }

        // Do a dice roll when the app starts. This is so that we have a dice displaying on the
        // screen when the activity first starts.
        rollDice()
    }

    /**
     * Roll the dice and update the screen with the result.
     */
    private fun rollDice() {
        // Create new Dice object with 6 sides and roll it
        val dice = Dice(6)
        val diceRoll = dice.roll()

        // Update the screen with the dice roll
        val diceImage: ImageView = findViewById(R.id.imageView)

        // Note: On Kotlin all expressions can return a value.
        val drawableResource = when (diceRoll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6 // *When* expressions must be exhaustive, and handle all cases.
        }

        // Update the ImageView with the correct drawable resource ID
        diceImage.setImageResource(drawableResource)

        // Update the content description
        diceImage.contentDescription = diceRoll.toString()
    }
}

/**
 * Simple dice class.
 */
class Dice(val numSides: Int) {

    fun roll(): Int {
        /**
         * Return a random number using IntRage.
         * @see <a href="https://kotlinlang.org/docs/reference/ranges.html">Using ranges</a>
         * @see <a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/-int-range/">IntRange</a>
         */
        return (1..numSides).random()
    }
}