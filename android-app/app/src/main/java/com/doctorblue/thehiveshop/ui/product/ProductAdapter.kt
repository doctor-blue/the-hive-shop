package com.doctorblue.thehiveshop.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doctorblue.thehiveshop.model.Product

class ProductAdapter(private val openProductDetail: (Product) -> Unit) :
    RecyclerView.Adapter<ProductViewHolder>() {

    var products: List<Product> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductViewHolder.create(inflater, parent, openProductDetail)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.onBind(products[position])
    }

    override fun getItemCount(): Int = products.size

}