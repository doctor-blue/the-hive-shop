package com.doctorblue.thehiveshop.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.databinding.ItemInCartBinding
import com.doctorblue.thehiveshop.model.ItemInCart
import com.doctorblue.thehiveshop.utils.ImageRequester

class CartViewHolder(
    private val binding: ItemInCartBinding,
    private val onItemClick: (ItemInCart) -> Unit,
    private val updateAmount: (ItemInCart) -> Unit

) :
    RecyclerView.ViewHolder(binding.root) {

    private var itemInCart: ItemInCart? = null

    init {
        binding.btnIncrease.setOnClickListener {
            itemInCart?.let {
                it.amount++
                binding.txtProductAmount.text = it.amount.toString()
                updateAmount(it)

            }

        }

        binding.btnReduce.setOnClickListener {
            itemInCart?.let {
                it.amount = if (it.amount > 0) it.amount - 1 else it.amount

                binding.txtProductAmount.text = it.amount.toString()
                updateAmount(it)

            }
        }
        binding.layoutItemInCart.setOnClickListener {
            itemInCart?.let {
                onItemClick(it)
            }
        }
    }

    fun onBind(itemInCart: ItemInCart) {
        this.itemInCart = itemInCart
        binding.txtProductTitle.text = itemInCart.title
        binding.txtProductAmount.text = itemInCart.amount.toString()
        binding.txtProductPrice.text = ("${itemInCart.price}$")
        ImageRequester.setImageFromUrl(binding.productImage, itemInCart.url)

    }

    companion object {
        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup,
            onItemClick: (ItemInCart) -> Unit,
            updateAmount: (ItemInCart) -> Unit
        ): CartViewHolder {
            val binding: ItemInCartBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_in_cart, parent, false)
            return CartViewHolder(binding, onItemClick, updateAmount)
        }
    }

}