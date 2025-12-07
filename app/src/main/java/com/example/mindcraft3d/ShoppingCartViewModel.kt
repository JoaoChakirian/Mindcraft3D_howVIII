package com.example.mindcraft3d

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShoppingCartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        _cartItems.value = _cartItems.value + product
    }

    fun removeFromCart(product: Product) {
        _cartItems.value = _cartItems.value - product
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}