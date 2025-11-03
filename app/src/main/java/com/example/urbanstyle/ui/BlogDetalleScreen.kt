package com.example.urbanstyle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.urbanstyle.R
import com.example.urbanstyle.data.model.BlogPost
import com.example.urbanstyle.ui.components.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogDetalleScreen(
    navController: NavHostController,
    post: BlogPost?
) {
    val pacifico = FontFamily(Font(R.font.pacifico))

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = post?.titulo ?: "Detalle del blog",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = pacifico,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        },
        bottomBar = { BottomBar(navController) }
    ) { inner ->
        if (post == null) {
            Box(Modifier.padding(inner).fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Entrada no encontrada")
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(id = post.imagenRes),
                contentDescription = post.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Text("${post.fecha} • ${post.autor}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)

            post.contenido.forEach { p ->
                Text(p, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
