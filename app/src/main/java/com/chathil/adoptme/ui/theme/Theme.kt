package com.chathil.adoptme.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.jetsnack.ui.utils.SysUiController

private val DarkColorPalette = AdoptmeColorPalette(
    primary = DeepOrange300,
    primaryVariant = DeepOrange500,
    secondary = DeepOrange900,
    btnLike = Red500,
    uiBackground = Black,
    uiBorder = Grey200,
    textPrimary = White,
    textSecondary = Grey200,
    btnActive = DeepOrange300,
    btnInActive = Grey700,
    btnContent = White,
    isDark = true
)

private val LightColorPalette = AdoptmeColorPalette(
    primary = DeepOrange500,
    primaryVariant = DeepOrange300,
    secondary = DeepOrange900,
    btnLike = Red500,
    uiBackground = White,
    uiBorder = Grey700,
    textPrimary = Black,
    textSecondary = Grey700,
    btnActive = DeepOrange100,
    btnInActive = Grey200,
    btnContent = DeepOrange500,
    isDark = false
)

object AdoptmeTheme {
    @Composable
    val colors: AdoptmeColorPalette
        get() = AdoptmeColorAmbient.current
}

@Stable
class AdoptmeColorPalette(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    btnLike: Color,
    uiBackground: Color,
    uiBorder: Color,
    textPrimary: Color,
    textSecondary: Color,
    btnActive: Color,
    btnInActive: Color,
    btnContent: Color,
    isDark: Boolean
) {
    var primary by mutableStateOf(primary)
        private set
    var primaryVariant by mutableStateOf(primaryVariant)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var btnLike by mutableStateOf(btnLike)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var uiBorder by mutableStateOf(uiBorder)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var btnActive by mutableStateOf(btnActive)
        private set
    var btnInActive by mutableStateOf(btnInActive)
        private set
    var btnContent by mutableStateOf(btnContent)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: AdoptmeColorPalette) {
        primary = other.primary
        primaryVariant = other.primaryVariant
        secondary = other.secondary
        btnLike = other.btnLike
        uiBackground = other.uiBackground
        uiBorder = other.uiBorder
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        btnActive = other.btnActive
        btnInActive = other.btnInActive
        btnContent = other.btnContent
        isDark = other.isDark
    }
}

@Composable
fun ProvideAdoptmeColors(
    colors: AdoptmeColorPalette,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    Providers(AdoptmeColorAmbient provides colorPalette, children = content)
}

private val AdoptmeColorAmbient = staticAmbientOf<AdoptmeColorPalette> {
    error("No JetsnackColorPalette provided")
}


@Composable
fun AdoptmeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val sysUiController = SysUiController.current
    onCommit(sysUiController, colors.uiBackground) {
        sysUiController.setSystemBarsColor(
            color = colors.uiBackground.copy(AlphaNearTransparent)
        )
    }

    ProvideAdoptmeColors(colors) {
        MaterialTheme(
            colors = debugColors(darkTheme),
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Magenta
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)