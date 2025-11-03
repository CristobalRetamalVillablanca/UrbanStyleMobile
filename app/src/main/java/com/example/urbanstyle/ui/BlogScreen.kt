package com.example.urbanstyle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.urbanstyle.R
import com.example.urbanstyle.data.repository.BlogRepository
import com.example.urbanstyle.navigation.Rutas
import com.example.urbanstyle.ui.components.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogScreen(navController: NavHostController) {
    val repo = remember { BlogRepository() }
    val posts = remember { repo.listar() }
    val pacifico = FontFamily(Font(R.font.pacifico))

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Blog",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontFamily = pacifico
                    )
                }
            )
        },
        bottomBar = { BottomBar(navController) }
    ) { inner ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(inner)
        ) {
            items(posts, key = { it.id }) { post ->
                ElevatedCard(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.elevatedCardElevation(2.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = post.imagenRes),
                        contentDescription = post.titulo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Column(Modifier.padding(14.dp)) {
                        Text(post.titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Text("${post.fecha} • ${post.autor}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            post.extracto,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(Modifier.height(10.dp))
                        Button(
                            onClick = { navController.navigate("blog/${post.id}") },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Leer más") }
                    }
                }
            }
        }
    }
}
