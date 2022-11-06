package com.android.hindara.booking.app.data

data class OnboardingImage(
    val imageDrawable: Int,
    var isSelected: Boolean = false,
    val position: Int,
    val title: Int,
    val description: Int
)