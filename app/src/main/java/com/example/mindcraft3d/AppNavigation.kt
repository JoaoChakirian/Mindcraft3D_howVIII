package com.example.mindcraft3d

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val shoppingCartViewModel: ShoppingCartViewModel = viewModel()
    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (searchActive && currentRoute == "productList") {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Pesquisar...") },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ),
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                navController.navigate("productList") {
                                    popUpTo("productList") { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.mindcraft_logo),
                                contentDescription = "MindCraft3D Logo",
                                modifier = Modifier.height(32.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "MindCraft3D",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color(0xFF6174CB)
                            )
                        }
                    }
                },
                actions = {
                    if (currentRoute == "productList") {
                        if (searchActive) {
                            IconButton(onClick = {
                                searchActive = false
                                searchQuery = ""
                            }) {
                                Icon(Icons.Default.Clear, contentDescription = "Fechar Pesquisa")
                            }
                        } else {
                            IconButton(onClick = { searchActive = true }) {
                                Icon(Icons.Default.Search, contentDescription = "Abrir Pesquisa")
                            }
                        }
                    }

                    IconButton(onClick = { navController.navigate("shoppingCart") }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrinho de Compras")
                    }

                    IconButton(onClick = { navController.navigate("auth") }) {
                        Icon(Icons.Default.Person, contentDescription = "Login ou Criar Conta")
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "productList",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("productList") {
                val filteredProducts = sampleProducts.filter {
                    it.name.contains(searchQuery, ignoreCase = true) ||
                            it.description.contains(searchQuery, ignoreCase = true)
                }
                ProductList(products = filteredProducts, onProductClick = { product ->
                    navController.navigate("productDetail/${product.id}")
                })
            }
            composable(
                route = "productDetail/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId")
                val product = sampleProducts.find { it.id == productId }
                if (product != null) {
                    ProductDetailScreen(product = product, onAddToCart = {
                        shoppingCartViewModel.addToCart(product)
                    })
                }
            }
            composable("shoppingCart") {
                val cartItems by shoppingCartViewModel.cartItems.collectAsState()
                ShoppingCartScreen(
                    cartItems = cartItems,
                    onRemoveFromCart = { product ->
                        shoppingCartViewModel.removeFromCart(product)
                    },
                    onClearCart = { shoppingCartViewModel.clearCart() }
                )
            }
            composable("auth") {
                AuthScreen(onAuthComplete = {
                    // Lógica após o login/criação
                    navController.popBackStack()
                })
            }
        }
    }
}