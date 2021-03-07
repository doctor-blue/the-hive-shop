package com.doctorblue.thehiveshop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.databinding.FragmentProductBinding
import com.doctorblue.thehiveshop.model.Product
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProvider(
            this,
            ProductViewModel.ProductViewModelFactory()
        )[ProductViewModel::class.java]
    }

    private val adapter: ProductAdapter by lazy {
        ProductAdapter(onProductItemClick)
    }
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)

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
        binding.rvProduct.setHasFixedSize(true)
        binding.rvProduct.layoutManager = GridLayoutManager(requireContext(),2)

        binding.rvProduct.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter{ adapter.retry() },
            footer = LoadStateAdapter{ adapter.retry() },
        )

        getProductFromApi()
    }

    private fun getProductFromApi() {
        job?.cancel()
        job = lifecycleScope.launch {
            productViewModel.getAllProduct().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private val onProductItemClick: (Product) -> Unit = {

    }

}