package com.yourday.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val YourDayShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp), // For stats cards
    large = RoundedCornerShape(18.dp),  // For task cards and schedule
    extraLarge = RoundedCornerShape(20.dp) // For progress card
)
