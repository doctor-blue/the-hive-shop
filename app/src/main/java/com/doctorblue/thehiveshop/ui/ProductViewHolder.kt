package com.doctorblue.thehiveshop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.databinding.ItemProductBinding
import com.doctorblue.thehiveshop.model.Product
import com.doctorblue.thehiveshop.utils.ImageRequester

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val onClick: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var product: Product? = null

    init {
        binding.layoutItemProduct.setOnClickListener {
            product?.let {
                onClick(it)
            }
        }
    }

    fun onBind(product: Product) {
        this.product = product
        ImageRequester.setImageFromUrl(binding.productImage, product.url)
        binding.txtProductTitle.text = product.title
        binding.txtProductPrice.text = ("$${product.price}")

    }

    companion object {
        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup,
            onClick: (Product) -> Unit
        ): ProductViewHolder {
            val binding: ItemProductBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_product, parent, false)
            return ProductViewHolder(binding, onClick)
        }

    }

}