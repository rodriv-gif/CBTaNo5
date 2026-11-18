package com.example.cbtano5.Vista

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Hace que el encabezado verde quede debajo de la barra de estado
 * (hora, batería) y que el contenido no se tape con la barra de abajo
 * del teléfono. Se llama una vez en onCreate de cada pantalla.
 *
 * raiz        = el layout principal (id "raiz")
 * encabezado  = el bloque verde de arriba (id "encabezado")
 */
fun AppCompatActivity.ajustarBarrasDelSistema(
    raiz: View,
    encabezado: View
) {

    // La app dibuja detrás de las barras del sistema
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.TRANSPARENT
    window.navigationBarColor = Color.TRANSPARENT

    // Iconos blancos arriba (sobre verde) e iconos oscuros abajo (sobre blanco)
    val controlador = WindowCompat.getInsetsController(window, raiz)
    controlador.isAppearanceLightStatusBars = false
    controlador.isAppearanceLightNavigationBars = true

    val paddingSuperiorOriginal = encabezado.paddingTop

    ViewCompat.setOnApplyWindowInsetsListener(raiz) { _, insets ->

        val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())

        // El encabezado crece lo que mide la barra de estado
        encabezado.setPadding(
            encabezado.paddingLeft,
            paddingSuperiorOriginal + barras.top,
            encabezado.paddingRight,
            encabezado.paddingBottom
        )

        // El resto sube lo que mide la barra de abajo
        raiz.setPadding(
            barras.left,
            0,
            barras.right,
            barras.bottom
        )

        insets
    }
}
