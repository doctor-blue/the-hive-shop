package com.doctorblue.thehiveshop.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.databinding.FragmentProductBinding
import com.doctorblue.thehiveshop.databinding.FragmentSignupBinding

class ProductFragment : BaseFragment() {

    private val binding = getViewBinding() as FragmentProductBinding

    override fun getLayoutId(): Int = R.layout.fragment_product

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }

    override fun initEvents() {

    }

}