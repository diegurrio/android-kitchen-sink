package com.diego.android.kitchen.rxkotlin.helpers

/**
 * For more on sealed classes
 * @see <a href="https://medium.com/androiddevelopers/sealed-with-a-class-a906f28ab7b5">What is a sealed class?</a>
 */
sealed class Droid : Throwable() {
    class OU812 : Droid()
    class R2D2 : Droid()
    class C3PO : Droid()
}

enum class Droids {
    C3PO, R2D2
}