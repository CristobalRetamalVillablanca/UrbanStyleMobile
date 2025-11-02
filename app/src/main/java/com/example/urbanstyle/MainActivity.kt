package com.example.urbanstyle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.urbanstyle.navigation.AppNav
import com.example.urbanstyle.ui.theme.PasteleriaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasteleriaTheme {
                val navController = rememberNavController()
                AppNav(navController = navController)
            }
        }
    }
}
