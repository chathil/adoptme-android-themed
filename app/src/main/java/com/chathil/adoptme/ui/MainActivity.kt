package com.chathil.adoptme.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Providers
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.setContent
import androidx.core.view.WindowCompat
import com.chathil.adoptme.ui.theme.AdoptmeTheme
import com.example.jetsnack.ui.utils.SysUiController
import com.example.jetsnack.ui.utils.SystemUiController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            Providers(SysUiController provides systemUiController) {
                AdoptmeApp(onBackPressedDispatcher)
            }
        }
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
}

