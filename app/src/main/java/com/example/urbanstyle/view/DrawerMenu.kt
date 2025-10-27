package com.example.urbanstyle.view
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable

fun DrawerMenu(
    username:String,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize())
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.primary)
        )

        {
            Text(
                text="Categorias: $username" ,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )
        }

        //  Items



        LazyColumn( modifier = Modifier.weight(1f)) {
            item{ // inicio item 1
                NavigationDrawerItem( // inicio DrawerItem
                    label = {Text("Torta Cuadrada de Chocolate")},
                    selected =false,
                    onClick = {
                        val nombre = Uri.encode("Torta Cuadrada de Chocolate")
                        val precio ="45000"
                        navController.navigate("ProductoFormScreen/$nombre/$precio")
                    }, // fin onclick
                    icon = {Icon(Icons.Default.Fastfood ,  contentDescription ="Clasica" )}

                ) // fin DrawerItem
            } // fin item 1


            item{ // inicio item 2
                NavigationDrawerItem( // inicio DrawerItem
                    label = {Text("Torta Cuadrada de Frutas")},
                    selected =false,
                    onClick = {
                        val nombre = Uri.encode("Torta Cuadrada de Frutas")
                        val precio ="50000"
                        navController.navigate("ProductoFormScreen/$nombre/$precio")
                    }, // fin onclick
                    icon = {Icon(Icons.Default.LunchDining ,  contentDescription ="BBQ" )}

                ) // fin DrawerItem
            } // fin item 2


            item{ // inicio item 3
                NavigationDrawerItem( // inicio DrawerItem
                    label = {Text("Torta Circular de Vainilla")},
                    selected =false,
                    onClick = { /*  accion */
                    }, // fin onclick
                    icon = {Icon(Icons.Default.Grass ,  contentDescription ="Veggie" )}

                ) // fin DrawerItem
            } // fin item 3

            item{ // inicio item 4
                NavigationDrawerItem( // inicio DrawerItem
                    label = {Text("Torta Circular de Manjar")},
                    selected =false,
                    onClick = { /*  accion */
                    }, // fin onclick
                    icon = {Icon(Icons.Default.LocalFireDepartment ,  contentDescription ="Picante" )}

                ) // fin DrawerItem
            } // fin item 4

            item{ // inicio item 5
                NavigationDrawerItem( // inicio DrawerItem
                    label = {Text("Mousse de Chocolate")},
                    selected =false,
                    onClick = { /*  accion */
                    }, // fin onclick
                    icon = {Icon(Icons.Default.Star ,  contentDescription ="Doble" )}

                ) // fin DrawerItem
            } // fin item 5


        } // fin Lazy

//  Footer del drawer
        Text(
            text ="@ 2025 Burger App",
            style = MaterialTheme.typography.bodySmall,

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        ) // fin footer

    } // termino columna

} // fin


@Preview(showBackground = true)
@Composable


fun DrawerMenuPreview(){
    val navController = androidx.navigation.compose.rememberNavController()
    DrawerMenu(username = "Usuario Prueba", navController = navController)
}
