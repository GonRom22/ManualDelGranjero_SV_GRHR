package com.example.agendacontactosgrhr.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.agendacontactosgrhr.R

val StardewFontFamily = FontFamily(
    Font(R.font.stardew_valley, FontWeight.Normal)
)

val AppTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = StardewFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = StardewFontFamily
    )
)
