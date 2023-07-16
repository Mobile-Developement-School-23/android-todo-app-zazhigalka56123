package i.need.drugs.todoapp.ui.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


val Red = Color(0xFFFF3B30)
val Blue = Color(0xFF007AFF)
val BlueTranslucent = Color(0x4D007AFF)

val LightPrimary = Color(0xF7F6F2)
val LightSecondary = Color(0x007AFF)
val LightSupportSeparator = Color(0x33000000)
val LightSupportOverlay = Color(0x0F000000)
val LightLabelPrimary = Color(0xFF000000)
val LightLabelSecondary = Color(0x99000000)
val LightLabelTertiary = Color(0x4D000000)
val LightLabelDisable = Color(0x26000000)
val LightBackPrimary = Color(0xFFF7F6F2)
val LightBackSecondary = Color(0xFFFFFFFF)
val LightBackElevated = Color(0xFFFFFFFF)

val DarkPrimary = Color(0x161618)
val DarkSecondary = Color(0x0A84FF)
val DarkSupportSeparator = Color(0x33FFFFFF)
val DarkSupportOverlay = Color(0x52000000)
val DarkLabelPrimary = Color(0xFFFFFFFF)
val DarkLabelSecondary = Color(0x99FFFFFF)
val DarkLabelTertiary = Color(0x66FFFFFF)
val DarkLabelDisable = Color(0x26FFFFFF)
val DarkBackPrimary = Color(0xFF161618)
val DarkBackSecondary = Color(0xFF252528)
val DarkBackElevated = Color(0xFF3C3C3F)

val Colors = staticCompositionLocalOf {
    ThemeColors()
}

val colors: ThemeColors
    @Composable
    get() = Colors.current


@Immutable
data class ThemeColors(
    val primary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val supportSeparator: Color = Color.Unspecified,
    val supportOverlay: Color = Color.Unspecified,
    val labelPrimary: Color = Color.Unspecified,
    val labelSecondary: Color = Color.Unspecified,
    val labelTertiary: Color = Color.Unspecified,
    val labelDisable: Color = Color.Unspecified,
    val backPrimary: Color = Color.Unspecified,
    val backSecondary: Color = Color.Unspecified,
    val backElevated: Color = Color.Unspecified
)

val lightThemeColors = ThemeColors(
    primary = LightPrimary,
    secondary = LightSecondary,
    supportSeparator = LightSupportSeparator,
    supportOverlay = LightSupportOverlay,
    labelPrimary = LightLabelPrimary,
    labelSecondary = LightLabelSecondary,
    labelTertiary = LightLabelTertiary,
    labelDisable = LightLabelDisable,
    backPrimary = LightBackPrimary,
    backSecondary = LightBackSecondary,
    backElevated = LightBackElevated
)

val darkThemeColors = ThemeColors(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    supportSeparator = DarkSupportSeparator,
    supportOverlay = DarkSupportOverlay,
    labelPrimary = DarkLabelPrimary,
    labelSecondary = DarkLabelSecondary,
    labelTertiary = DarkLabelTertiary,
    labelDisable = DarkLabelDisable,
    backPrimary = DarkBackPrimary,
    backSecondary = DarkBackSecondary,
    backElevated = DarkBackElevated
)