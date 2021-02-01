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
    private val updateAmount: (ItemInCart, () -> Unit) -> Unit

) :
    RecyclerView.ViewHolder(binding.root) {

    private var itemInCart: ItemInCart? = null
    private var isReduce = false

    init {
        binding.btnIncrease.setOnClickListener {
            itemInCart?.let {
                isReduce = false
                it.amount++
                binding.txtProductAmount.text = it.amount.toString()
                updateAmount(it, onError)

            }

        }

        binding.btnReduce.setOnClickListener {
            itemInCart?.let {
                isReduce = true
                it.amount = if (it.amount >1) it.amount - 1 else it.amount

                binding.txtProductAmount.text = it.amount.toString()
                updateAmount(it, onError)

            }
        }

        binding.layoutItemInCart.setOnClickListener {
            itemInCart?.let {
                onItemClick(it)
            }
        }
    }

    private val onError: () -> Unit = {
        itemInCart?.let {
            if (isReduce) {
                it.amount++
            } else {
                it.amount--
            }
            binding.txtProductAmount.text = it.amount.toString()
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
            updateAmount: (ItemInCart,()->Unit) -> Unit
        ): CartViewHolder {
            val binding: ItemInCartBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_in_cart, parent, false)
            return CartViewHolder(binding, onItemClick, updateAmount)
        }
    }

}