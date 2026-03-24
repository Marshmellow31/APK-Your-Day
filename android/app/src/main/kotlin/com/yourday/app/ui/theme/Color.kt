package com.yourday.app.ui.theme

import androidx.compose.ui.graphics.Color

// ── Theme Palette ────────────────────────────────────────────────────────────
val Background = Color(0xFF0A0A0A)
val Surface = Color(0xFF111111)
val CardBackground = Color(0xFF151515)
val BorderColor = Color(1f, 1f, 1f, 0.06f)

val Accent = Color(0xFF6C63FF)
val AccentDark = Color(0xFF4A43D6)
val AccentLight = Color(0xFF9E9AFF)

// ── Text Colors ──────────────────────────────────────────────────────────────
val TextPrimary = Color(0xFFEAEAEA)
val TextSecondary = Color(0xFF9A9A9A)
val TextSubtitle = Color(0xFF777777)

// ── Material 3 Mapping ───────────────────────────────────────────────────────
val Primary = Accent
val OnPrimary = Color.White
val PrimaryContainer = Accent.copy(alpha = 0.1f)
val OnPrimaryContainer = Accent
val PrimaryLight = AccentLight
val PrimaryDark = AccentDark

val Secondary = TextSecondary
val OnSecondary = Background
val SecondaryContainer = Color(0xFF333333)
val OnSecondaryContainer = Color.White

val Tertiary = Color(0xFF00C4B4)
val OnTertiary = Color.White
val TertiaryContainer = Color(0xFF004D46)
val OnTertiaryContainer = Color(0xFFB2EEE9)

val ErrorColor = Color(0xFFFF5252)
val OnError = Color.White
val ErrorContainer = Color(0xFFB00020)
val OnErrorContainer = Color(0xFFFFDAD6)

val Success = Color(0xFF4CAF50)

val BackgroundColor = Background
val OnBackground = TextPrimary

val SurfaceColor = Surface
val OnSurface = TextPrimary
val SurfaceVariant = CardBackground
val OnSurfaceVariant = TextSecondary
val SurfaceContainer = Color(0xFF1A1A1A)
val SurfaceContainerHigh = Color(0xFF242424)

val Outline = BorderColor
val OutlineVariant = Color(1f, 1f, 1f, 0.05f)

// ── Priority Styles ─────────────────────────────────────────────────────────
val PriorityHigh = Color(0xFFFF5252)
val PriorityMedium = Color(0xFFFFC107)
val PriorityLow = Color(0xFF4CAF50)

val PriorityHighBorder = PriorityHigh.copy(alpha = 0.4f)
val PriorityHighBackground = PriorityHigh.copy(alpha = 0.05f)
val PriorityMediumBorder = PriorityMedium.copy(alpha = 0.3f)
val PriorityLowBorder = PriorityLow.copy(alpha = 0.3f)

// ── Subject Colors ──────────────────────────────────────────────────────────
val subjectColors = listOf(
    Color(0xFFFF5252), // Red
    Color(0xFFFF4081), // Pink
    Color(0xFFE040FB), // Purple
    Color(0xFF7C4DFF), // Deep Purple
    Color(0xFF536DFE), // Indigo
    Color(0xFF448AFF), // Blue
    Color(0xFF40C4FF), // Light Blue
    Color(0xFF18FFFF), // Cyan
    Color(0xFF64FFDA), // Teal
    Color(0xFF69F0AE), // Green
    Color(0xFFB2FF59), // Light Green
    Color(0xFFEEFF41), // Lime
    Color(0xFFFFFF00), // Yellow
    Color(0xFFFFD740), // Amber
    Color(0xFFFFAB40), // Orange
    Color(0xFFFF6E40)  // Deep Orange
)

// ── Gradients ────────────────────────────────────────────────────────────────
val MainBgGradient = listOf(Color(0xFF0A0A0A), Color(0xFF050505))
val FABGradient = listOf(Accent, AccentDark)
val UsernameGradient = listOf(Color.White, Color(0xFFB0B0B0))
