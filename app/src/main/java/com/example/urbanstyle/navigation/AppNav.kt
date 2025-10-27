package com.example.urbanstyle.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.urbanstyle.login.LoginScreen

import com.example.urbanstyle.view.DrawerMenu

@Composable

fun AppNav(){
    val navController = rememberNavController()

    NavHost( navController=navController, startDestination = "login"){
        composable("login"){
            LoginScreen( navController=navController)
        }

        composable(
            route="DrawerMenu/{username}",
            arguments = listOf(
                navArgument("username"){
                    type= NavType.StringType
                }
            )
        )

        {
                backStackEntry ->
            val username=backStackEntry.arguments?.getString("username").orEmpty()
            DrawerMenu(username=username,navController=navController  )

        }

    }

}