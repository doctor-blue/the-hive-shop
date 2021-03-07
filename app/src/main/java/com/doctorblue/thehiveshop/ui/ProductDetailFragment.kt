package com.doctorblue.thehiveshop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.databinding.FragmentProductDetailBinding
import com.doctorblue.thehiveshop.model.Product
import com.doctorblue.thehiveshop.utils.ImageRequester

class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControls()
        initEvents()
    }

    private fun initEvents() {

    }

    private fun initControls() {
        val product = arguments?.get("PRODUCT_DETAIL") as Product?

        product?.let {
            ImageRequester.setImageFromUrl(binding.imgProduct, it.url)
            binding.txtProductTitle.text = it.title
            binding.txtProductPrice.text = ("$${it.price}")
            binding.txtProductDescription.text = it.description
        }
    }
}