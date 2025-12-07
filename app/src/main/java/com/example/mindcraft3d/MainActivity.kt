package com.example.mindcraft3d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mindcraft3d.ui.theme.Mindcraft3DTheme

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    @DrawableRes val imageRes: List<Int>,
    val isFeatured: Boolean = false
)

// Lembre-se de adicionar suas imagens na pasta res/drawable
val sampleProducts = listOf(
    Product(1, "Kit Vasos Decorativos Espiral", "Um vaso elegante para suas plantas", 49.00, listOf(R.drawable.vasoa, R.drawable.vasob, R.drawable.vasoc), isFeatured = true),
    Product(2, "Tie Fighter Star Wars Kit Cartão", "Modelo Colecionavel de Star Wars com tie fighter  ", 20.00, listOf(R.drawable.star_a, R.drawable.star_b, R.drawable.star_c, R.drawable.star_d)),
    Product(3, "Chaveiro de Capivara", "Um chaveiro de Capivara", 10.00, listOf(R.drawable.cap_a, R.drawable.cap_b, R.drawable.cap_c, R.drawable.cap_d), isFeatured = true),
    Product(4, "Miniaturas Pokémon Eevee e Evoluções", "Uma coleção de miniaturas Pokémon", 35.00, listOf(R.drawable.pokemon_a, R.drawable.pokemon_b, R.drawable.pokemon_c, R.drawable.pokemon_d)),
    Product(5, "Deck Box TGC Game Boy", "Deck Box para guardar suas cartas de Pokémon", 65.00, listOf(R.drawable.deck_a, R.drawable.deck_b, R.drawable.deck_c, R.drawable.deck_d)),
    Product(6, "Deck Box MTG Red Dragon", "Deck Box para guardar suas cartas de Magic", 65.00, listOf(R.drawable.red_a, R.drawable.red_b, R.drawable.red_c, R.drawable.red_d,R.drawable.red_e), isFeatured = true),
    Product(7, "Suporte Bob Esponja Decoração", "Suporte para sua Esponja de limpeza", 20.00, listOf(R.drawable.bob_a, R.drawable.bob_b, R.drawable.bob_c, R.drawable.bob_d)),
    )
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mindcraft3DTheme {
                AppNavigation()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedProductCard(product: Product, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClick,
        modifier = modifier.width(180.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = product.imageRes.first()),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium, maxLines = 1, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "R$ ${String.format("%.2f", product.price)}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Text(text = "em 5x de R$ ${String.format("%.2f", product.price / 5)}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(product: Product, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = product.imageRes.first()),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = product.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 2)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "R$ ${String.format("%.2f", product.price)}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Text(text = "em 5x de R$ ${String.format("%.2f", product.price / 5)}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun ProductList(products: List<Product>, onProductClick: (Product) -> Unit, modifier: Modifier = Modifier) {
    val featuredProducts = products.filter { it.isFeatured }
    val regularProducts = products.filter { !it.isFeatured }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        if (featuredProducts.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Destaques",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(featuredProducts) { product ->
                            FeaturedProductCard(product = product, onClick = { onProductClick(product) })
                        }
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxHeight()
                            .width(50.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background // Corrigido para a cor de fundo
                                    ),
                                    startX = 0f
                                )
                            )
                    )
                }
            }
        }

        if (regularProducts.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Todos os Produtos",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp, top = 24.dp, end = 8.dp, bottom = 8.dp)
                )
            }
            items(regularProducts) { product ->
                ProductCard(product = product, onClick = { onProductClick(product) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    Mindcraft3DTheme {
        ProductList(products = sampleProducts, onProductClick = {})
    }
}
