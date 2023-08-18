package com.ersubhadip.branchinternationalassignment.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Black = "#000000".toColor()
val White = "#ffffff".toColor()
val Transparent = "#00000000".toColor()
val GrayDark = "#242424".toColor()
val Orange = "#FF6600".toColor()
val BluePrimary = "#FF03A9F4".toColor()


fun String.toColor() = Color(android.graphics.Color.parseColor(this))