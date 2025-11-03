package com.example.urbanstyle.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.urbanstyle.R
import com.example.urbanstyle.ui.components.BottomBar
import androidx.compose.foundation.lazy.LazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NosotrosScreen(navController: NavHostController) {
    val pacifico = FontFamily(Font(R.font.pacifico))
    val cafe = Color(0xFF5D4037)
    val cremaOverlay = Color(0xFFFFF6E8).copy(alpha = 0.82f)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Nosotros",
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
            modifier = Modifier
                .padding(inner)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ---------- Hero / Banner ----------
            item {
                ElevatedCard(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.elevatedCardElevation(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.blog_record),
                            contentDescription = "50 años endulzando momentos",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        // Overlay crema para mejorar legibilidad
                        Surface(
                            color = cremaOverlay,
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp, horizontal = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "50 Años Endulzando Momentos",
                                    fontFamily = pacifico,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = cafe,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    "Desde 1974, creando los mejores sabores para tus celebraciones",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = cafe.copy(alpha = 0.9f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }


            item {
                ElevatedCard(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.elevatedCardElevation(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Nuestra Historia",
                            style = MaterialTheme.typography.titleLarge,
                            fontFamily = pacifico,
                            color = cafe
                        )
                        Text(
                            "Pastelería Mil Sabores celebra su 50 aniversario como un referente en la repostería chilena. Famosa por su participación en un récord Guinness en 1995, cuando colaboró en la creación de la torta más grande del mundo."
                        )
                        Text(
                            "Nuestro compromiso con la calidad y el cariño en cada preparación nos ha permitido acompañar miles de cumpleaños, matrimonios y momentos inolvidables."
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            MetricItem("50+", "Años de\nExperiencia", Modifier.weight(1f))
                            MetricItem("1",   "Récord\nGuinness",   Modifier.weight(1f))
                            MetricItem("10k+","Clientes\nSatisfechos",Modifier.weight(1f))
                        }

                    }
                }
            }


            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoCard(
                        titulo = "Nuestra Misión",
                        emoji = "🎯",
                        texto = "Ofrecer una experiencia dulce y memorable a nuestros clientes, con productos de alta calidad elaborados con ingredientes frescos y técnicas tradicionales combinadas con innovación.",
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        titulo = "Nuestra Visión",
                        emoji = "👁️",
                        texto = "Ser la tienda líder de repostería en Chile, reconocida por su innovación, calidad y aporte positivo a la comunidad, expandiendo nuestro dulce sabor de norte a sur.",
                        modifier = Modifier.weight(1f)
                    )
                }
            }


            item {
                ElevatedCard(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.elevatedCardElevation(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Nuestro Equipo",
                            style = MaterialTheme.typography.titleLarge,
                            fontFamily = pacifico,
                            color = cafe
                        )
                        Text("Los artistas detrás de cada dulce creación",
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            TeamCard("👩‍🍳", "Chef María González", "Pastelera Principal",
                                "25 años de experiencia en repostería francesa y chilena.",
                                Modifier.weight(1f))
                            TeamCard("👨‍🍳", "Pastelero Juan Pérez", "Especialista en Decoración",
                                "Maestro en técnicas modernas de decoración.",
                                Modifier.weight(1f))
                            TeamCard("🎓", "Estudiantes Duoc UC", "Futuros Pasteleros",
                                "Talentos comprometidos con la calidad y la innovación.",
                                Modifier.weight(1f))
                        }
                    }
                }
            }

            // ---------- Valores ----------
            item {
                ElevatedCard(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.elevatedCardElevation(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Nuestros Valores",
                            style = MaterialTheme.typography.titleLarge,
                            fontFamily = pacifico,
                            color = cafe
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ValorItem("⭐","Calidad","Ingredientes de primera en cada producto.", Modifier.weight(1f))
                            ValorItem("❤️","Pasión","Amamos lo que hacemos y se nota en cada creación.", Modifier.weight(1f))
                            ValorItem("🔒","Tradición","Respetamos las recetas clásicas mientras innovamos.", Modifier.weight(1f))
                            ValorItem("🌱","Innovación","Buscamos nuevas técnicas, sabores y experiencias.", Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

/* ======= Componentes reutilizables ======= */

@Composable
private fun MetricItem(numero: String, texto: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.elevatedCardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(numero, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(texto, style = MaterialTheme.typography.labelMedium, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun InfoCard(titulo: String, emoji: String, texto: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(emoji, fontSize = 22.sp)
            Text(titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(texto, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun TeamCard(emoji: String, nombre: String, rol: String, desc: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(1.dp)
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(emoji, fontSize = 26.sp)
            Text(nombre, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text(rol, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
            Text(desc, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun ValorItem(emoji: String, titulo: String, texto: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(1.dp)
    ) {
        Column(
            Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, fontSize = 24.sp)
            Text(titulo, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text(texto, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
    }
}
