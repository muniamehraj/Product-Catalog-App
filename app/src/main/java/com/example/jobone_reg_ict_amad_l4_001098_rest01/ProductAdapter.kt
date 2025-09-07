package com.example.jobone_reg_ict_amad_l4_001098_rest01

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.jobone_reg_ict_amad_l4_001098_rest01.models.Product

class ProductAdapter :
    ListAdapter<Product, ProductAdapter.ProductVH>(DIFF) {

    object DIFF : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductVH(v)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivThumb: ImageView = itemView.findViewById(R.id.ivThumb)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)

        fun bind(p: Product) {
            tvName.text = p.title
            tvDesc.text = p.description
            tvPrice.text = "$${"%.2f".format(p.price)}"

            // Load each product's own image URL. If missing/bad, show a fallback.
            Glide.with(ivThumb.context)
                .load(p.thumbnail) // this is dto.images?.firstOrNull()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(ivThumb)
        }
    }
}
