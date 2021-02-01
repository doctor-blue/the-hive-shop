package com.doctorblue.thehiveshop.ui.cart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.doctorblue.thehiveshop.Injection
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.data.CartRequest
import com.doctorblue.thehiveshop.databinding.FragmentCartBinding
import com.doctorblue.thehiveshop.model.ItemInCart
import com.doctorblue.thehiveshop.utils.Resource

class CartFragment : BaseFragment() {

    private val binding: FragmentCartBinding
        get() = (getViewBinding() as FragmentCartBinding)

    private val cartViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideCartViewModelFactory()
        )[CartViewModel::class.java]
    }

    private val cartAdapter by lazy {
        CartAdapter(onItemClick, updateAmount)
    }

    private var cart: List<ItemInCart> = listOf()
        set(value) {
            field = value
            getTotalAmount()
        }
    private val controller by lazy {
        findNavController()
    }

    override fun getLayoutId(): Int = R.layout.fragment_cart

    override fun initControls(view: View, savedInstanceState: Bundle?) {

        binding.rvCart.setHasFixedSize(false)
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = cartAdapter

        refreshData()

    }

    override fun initEvents() {
        binding.sfCart.setOnRefreshListener {
            refreshData()
        }

        binding.btnBuy.setOnClickListener {
            buy()
        }

        binding.toolbarCart.setNavigationOnClickListener {
            controller.popBackStack()
        }
    }

    private fun refreshData() {
        cartViewModel.getCart().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), R.string.success, Toast.LENGTH_SHORT)
                        .show()
                    cartAdapter.cart = it.data
                    cart = it.data
                    binding.sfCart.isRefreshing = false
                }
                is Resource.Error -> {
                    binding.sfCart.isRefreshing = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Loading -> {
                    binding.sfCart.isRefreshing = true
                }
            }
        })
    }

    private fun buy() {
        for ((index, value) in cart.withIndex()) {
            cartViewModel.deleteItem(CartRequest(item = value)).observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Success -> {
                        if (index == cart.size - 1) {
                            refreshData()
                        }
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

    private val updateAmount: (ItemInCart, () -> Unit) -> Unit = { item, onError ->
        cartViewModel.updateItemAmount(CartRequest(item = item)).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    getTotalAmount()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                        .show()

                    onError()
                }
                is Resource.Loading -> {

                }
            }
        })
    }

    private fun getTotalAmount() {
        var totalAmount = 0f
        cart.forEach {
            totalAmount += (it.price * it.amount)
        }
        binding.txtAmount.text = ("$${totalAmount}")
    }

    private val onItemClick: (ItemInCart) -> Unit = {

    }

}