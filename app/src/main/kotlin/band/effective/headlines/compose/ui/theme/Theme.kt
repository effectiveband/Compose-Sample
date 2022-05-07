package band.effective.headlines.compose.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = Melrose,
    primaryContainer = Gigas,
    secondary = LavenderGray,
    secondaryContainer = MulledWine,
    tertiary = BeautyBush,
    tertiaryContainer = Eggplant,
    surface = BalticSea,
    surfaceVariant = ShipGray,
    background = BalticSea,
    error = MandysPink,
    errorContainer = FaluRed,
    onPrimary = Meteorite,
    onPrimaryContainer = BlueChalk,
    onSecondary = Blackcurrant,
    onSecondaryContainer = MoonRaker,
    onTertiary = LividBrown,
    onTertiaryContainer = PastelPink,
    onSurface = BonJour,
    onSurfaceVariant = GraySuit,
    onError = DarkTan,
    onErrorContainer = Cherub,
    onBackground = BonJour,
    outline = MountainMist,
    surfaceTint = Melrose,
    inverseSurface = BonJour,
    inverseOnSurface = DarkCharcoal,
    inversePrimary = ButterflyBush
)

private val LightColorScheme = lightColorScheme(
    primary = ButterflyBush,
    primaryContainer = BlueChalk,
    secondary = BlackCoral,
    secondaryContainer = MoonRaker,
    tertiary = Ferra,
    tertiaryContainer = PastelPink,
    surface = Tutu,
    surfaceVariant = Snuff,
    background = Tutu,
    error = RoofTerracotta,
    errorContainer = Cherub,
    onPrimary = Color.White,
    onPrimaryContainer = Paua,
    onSecondary = Color.White,
    onSecondaryContainer = Mirage,
    onTertiary = Color.White,
    onTertiaryContainer = Tamarind,
    onSurface = BalticSea,
    onSurfaceVariant = ShipGray,
    onError = Color.White,
    onErrorContainer = VanCleef,
    onBackground = BalticSea,
    outline = Mobster,
    surfaceTint = Snuff,
    inverseSurface = DarkCharcoal,
    inverseOnSurface = Prim,
    inversePrimary = Melrose
)

@Composable
fun HeadlinesComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
