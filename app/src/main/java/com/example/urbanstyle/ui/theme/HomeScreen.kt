package com.example.urbanstyle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.urbanstyle.R
import com.example.urbanstyle.ui.components.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Pastelería Mil Sabores",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // 🧁 Logo o imagen de portada
                Image(
                    painter = painterResource(id = R.drawable.logo), // usa tu logo.png o logo.webp
                    contentDescription = "Logo Pastelería Mil Sabores",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(top = 32.dp)
                )

                // Texto de bienvenida
                Text(
                    text = "Bienvenido a nuestra pastelería, donde cada dulce cuenta una historia. 🍰",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )

                Divider(
                    color = MaterialTheme.colorScheme.secondary,
                    thickness = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(vertical = 8.dp)
                )

                // Sección informativa o destacada
                Text(
                    text = "Descubre nuestras tortas, postres y panes artesanales elaborados con amor y dedicación.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para ir a productos
                Button(
                    onClick = { navController.navigate("productos") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = "Ver productos",
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
