package com.doctorblue.thehiveshop.ui.cart

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
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
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

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
        CartAdapter(onItemClick, onItemLongClick,updateAmount)
    }

    private val dialogLoading by lazy {
        Dialog(requireContext())
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
        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoading.setCancelable(false)
        dialogLoading.setContentView(R.layout.dialog_loading)
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
                        dialogLoading.dismiss()
                        if (index == cart.size - 1) {
                            refreshData()
                        }
                    }
                    is Resource.Error -> {
                        dialogLoading.dismiss()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                            .show()

                    }
                    is Resource.Loading -> {
                        dialogLoading.show()
                    }
                }
            })
        }
    }

    private val updateAmount: (ItemInCart, () -> Unit) -> Unit = { item, onError ->
        cartViewModel.updateItemAmount(CartRequest(item = item)).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    dialogLoading.dismiss()
                    getTotalAmount()
                }
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                        .show()

                    onError()
                }
                is Resource.Loading -> {
                    dialogLoading.show()
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

    private val onItemLongClick: (ItemInCart) -> Unit = { item ->
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_confirm)
        val title = dialog.findViewById<MaterialTextView>(R.id.txt_dialog_title)
        title.text = resources.getString(R.string.delete)
        val message = dialog.findViewById<MaterialTextView>(R.id.txt_dialog_message)
        message.text = resources.getString(R.string.delete_message)
        val btnConfirm = dialog.findViewById<MaterialButton>(R.id.btn_confirm)
        val btnCancel = dialog.findViewById<MaterialButton>(R.id.btn_cancel)
        btnConfirm.setOnClickListener {
            dialog.dismiss()
            cartViewModel.deleteItem(CartRequest(item = item)).observe(viewLifecycleOwner, {
                when(it) {
                    is Resource.Success -> {
                        dialogLoading.dismiss()
                        refreshData()
                        getTotalAmount()
                        Toast.makeText(requireContext(), resources.getString(R.string.success), Toast.LENGTH_SHORT)
                    }
                    is Resource.Error -> {
                        dialogLoading.dismiss()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is Resource.Loading -> {
                        dialogLoading.show()
                    }
                }
            })
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}