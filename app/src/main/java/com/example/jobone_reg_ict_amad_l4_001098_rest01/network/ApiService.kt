package com.example.jobone_reg_ict_amad_l4_001098_rest01.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    fun getProducts(
        @Query("limit") limit: Int = 200,
        @Query("offset") offset: Int = 0
    ): Call<List<ProductDto>>
}
