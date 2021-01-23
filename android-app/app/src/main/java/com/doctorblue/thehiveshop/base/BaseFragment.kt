package com.doctorblue.thehiveshop.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    private lateinit var binding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControls(view, savedInstanceState)
        initEvents()
    }

    abstract fun getLayoutId(): Int

    abstract fun initControls(view: View, savedInstanceState: Bundle?)

    abstract fun initEvents()

    fun getViewBinding():ViewDataBinding{
        return binding
    }

}