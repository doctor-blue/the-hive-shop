package com.doctorblue.thehiveshop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.databinding.LoadStateViewItemBinding

class LoadStateViewHolder(
    private val binding: LoadStateViewItemBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

    fun onBind(loadState: LoadState){
        if (loadState is LoadState.Error){
            binding.txtError.text = loadState.error.toString()
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.txtError.isVisible = loadState !is LoadState.Loading
        binding.btnRetry.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(
            parent:ViewGroup,
            retry: () -> Unit
        ):LoadStateViewHolder{
            val inflater = LayoutInflater.from(parent.context)
            val binding: LoadStateViewItemBinding = DataBindingUtil.inflate(inflater,R.layout.load_state_view_item,parent,false)
            return  LoadStateViewHolder(binding,retry)
        }
    }

}