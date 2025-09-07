package com.example.jobone_reg_ict_amad_l4_001098_rest01.network

data class ProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val images: List<String>?
)
