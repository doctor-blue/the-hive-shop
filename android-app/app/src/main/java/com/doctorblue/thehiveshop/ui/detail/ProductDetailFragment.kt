package com.doctorblue.thehiveshop.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.doctorblue.thehiveshop.Injection
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.data.CartRequest
import com.doctorblue.thehiveshop.data.User
import com.doctorblue.thehiveshop.databinding.FragmentProductDetailBinding
import com.doctorblue.thehiveshop.model.ItemInCart
import com.doctorblue.thehiveshop.model.Product
import com.doctorblue.thehiveshop.ui.cart.CartViewModel
import com.doctorblue.thehiveshop.utils.ImageRequester
import com.doctorblue.thehiveshop.utils.Resource

class ProductDetailFragment : BaseFragment() {

    private val binding: FragmentProductDetailBinding
        get() = (getViewBinding() as FragmentProductDetailBinding)


    override fun getLayoutId(): Int = R.layout.fragment_product_detail

    var productAmount: Int = 0

    private val cartViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideCartViewModelFactory()
        )[CartViewModel::class.java]
    }
    private var product: Product? = null


    override fun initControls(view: View, savedInstanceState: Bundle?) {
        val product = arguments?.get("PRODUCT_DETAIL") as Product?
        this.product = product
        setData()

    }


    override fun initEvents() {
        binding.btnIncrease.setOnClickListener {
            productAmount++
            binding.txtProductAmount.text = productAmount.toString()
        }

        binding.btnReduce.setOnClickListener {
            productAmount = if (productAmount > 0) productAmount - 1 else productAmount
            binding.txtProductAmount.text = productAmount.toString()
        }

        binding.btnAddToCart.setOnClickListener {
            product?.let {
                val itemInCart =
                    ItemInCart(it.id, it.title, it.url, it.price, it.description, productAmount)
                val request = CartRequest(User.email,itemInCart)
                cartViewModel.addProductToCart(request).observe(viewLifecycleOwner, {
                    when (it) {
                        is Resource.Success -> {
                            Toast.makeText(requireContext(), R.string.success, Toast.LENGTH_SHORT)
                                .show()
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        is Resource.Loading -> {

                        }
                    }
                })
            }
        }
    }

    private fun setData() {
        product?.let {
            ImageRequester.setImageFromUrl(binding.imgProduct, it.url)
            binding.txtProductPrice.text = ("${it.price}$")
            binding.txtProductTitle.text = it.title
            binding.txtProductDescription.text = it.description

        }
    }
}