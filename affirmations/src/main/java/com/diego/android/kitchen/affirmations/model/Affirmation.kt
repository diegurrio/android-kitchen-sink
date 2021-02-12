package com.diego.android.kitchen.affirmations.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Simple data class to store a String resource ID.
 */
data class Affirmation(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)

