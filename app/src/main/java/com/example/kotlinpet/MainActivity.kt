package com.example.kotlinpet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kotlinpet.view.PacienteScreen
import com.example.kotlinpet.view.theme.KotlinPetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinPetTheme {
                PacienteScreen()
            }
        }
    }
}
