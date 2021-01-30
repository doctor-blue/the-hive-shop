package com.doctorblue.thehiveshop.ui.detail

import android.os.Bundle
import android.view.View
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.databinding.FragmentProductDetailBinding
import com.doctorblue.thehiveshop.model.Product
import com.doctorblue.thehiveshop.utils.ImageRequester

class ProductDetailFragment : BaseFragment() {

    private val binding: FragmentProductDetailBinding
        get() = (getViewBinding() as FragmentProductDetailBinding)


    override fun getLayoutId(): Int = R.layout.fragment_product_detail

    var productAmount: Int = 0

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        val product = arguments?.get("PRODUCT_DETAIL") as Product?
        setData(product)

    }

    override fun initEvents() {
        binding.btnIncrease.setOnClickListener {
            productAmount ++
            binding.txtProductAmount.text=productAmount.toString()
        }

        binding.btnReduce.setOnClickListener {
            productAmount = if (productAmount > 0) productAmount - 1 else productAmount
            binding.txtProductAmount.text=productAmount.toString()
        }

        binding.btnAddToCart.setOnClickListener {

        }
    }

    private fun setData(product: Product?) {
        product?.let {
            ImageRequester.setImageFromUrl(binding.imgProduct, product.url)
            binding.txtProductPrice.text = ("${product.price}$")
            binding.txtProductTitle.text = product.title
            binding.txtProductDescription.text = product.description

        }
    }
}