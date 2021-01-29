package com.doctorblue.thehiveshop.ui.product

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.doctorblue.thehiveshop.Injection
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.databinding.FragmentProductBinding
import com.doctorblue.thehiveshop.model.Product
import com.doctorblue.thehiveshop.utils.Resource
import kotlinx.android.synthetic.main.fragment_product.*

class ProductFragment : BaseFragment() {

    private val binding: FragmentProductBinding
        get() = (getViewBinding() as FragmentProductBinding)

    private val productViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideProductViewModelFactory()
        )[ProductViewModel::class.java]
    }

    private val controller by lazy {
        findNavController()
    }

    private val productAdapter by lazy {
        ProductAdapter(openProductDetail)
    }

    override fun getLayoutId(): Int = R.layout.fragment_product

    override fun initControls(view: View, savedInstanceState: Bundle?) {

        binding.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProduct.setHasFixedSize(true)
        binding.rvProduct.adapter = productAdapter


        refreshData()
    }

    override fun initEvents() {

        binding.productToolBar.setNavigationOnClickListener {
            controller.navigate(R.id.action_productFragment_to_settingsFragment)
        }

        binding.sfProduct.setOnRefreshListener {
            refreshData()
        }

        binding.retryButton.setOnClickListener {
            refreshData()
        }

    }

    private val openProductDetail: (Product) -> Unit = {

    }

    private fun refreshData() {
        productViewModel.getAllProduct().observe(viewLifecycleOwner, {
            binding.retryButton.isVisible = (it !is Resource.Loading && it !is Resource.Success)
            binding.emptyList.isVisible = (it !is Resource.Loading && it !is Resource.Success)
            rv_product.isVisible = it is Resource.Success

            when (it) {
                is Resource.Success -> {
                    productAdapter.products = it.data
                    binding.sfProduct.isRefreshing = false

                }
                is Resource.Error -> {
                    binding.sfProduct.isRefreshing = false
//                    binding.emptyList.text = it.message
                }
                is Resource.Loading -> {
                    binding.sfProduct.isRefreshing = true

                }
            }
        })
    }

}