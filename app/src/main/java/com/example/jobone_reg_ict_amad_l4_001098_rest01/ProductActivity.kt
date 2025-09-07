package com.example.jobone_reg_ict_amad_l4_001098_rest01

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobone_reg_ict_amad_l4_001098_rest01.databinding.ActivityProductBinding
import com.example.jobone_reg_ict_amad_l4_001098_rest01.models.Product
import com.example.jobone_reg_ict_amad_l4_001098_rest01.network.ProductDto
import com.example.jobone_reg_ict_amad_l4_001098_rest01.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private val adapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTitle.text = "Products"

        binding.rvProducts.layoutManager = LinearLayoutManager(this)
        binding.rvProducts.adapter = adapter

        binding.btnRetry.setOnClickListener { loadProducts() }

        loadProducts()
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.btnRetry.visibility = View.GONE
    }

    private fun showRetry() {
        binding.progressBar.visibility = View.GONE
        binding.btnRetry.visibility = View.VISIBLE
    }

    private fun loadProducts() {
        setLoading(true)

        RetrofitClient.api.getProducts(limit = 200, offset = 0)
            .enqueue(object : Callback<List<ProductDto>> {
                override fun onResponse(
                    call: Call<List<ProductDto>>,
                    response: Response<List<ProductDto>>
                ) {
                    setLoading(false)
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        val list = body.map { dto ->
                            Product(
                                id = dto.id,
                                title = dto.title,
                                price = dto.price,
                                description = dto.description,
                                thumbnail = dto.images?.firstOrNull().orEmpty()
                            )
                        }
                        adapter.submitList(list)
                    } else {
                        showRetry()
                    }
                }

                override fun onFailure(call: Call<List<ProductDto>>, t: Throwable) {
                    showRetry()
                }
            })
    }
}
