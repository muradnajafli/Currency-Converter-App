package com.example.converterapp.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.converterapp.databinding.CurrencyItemBinding
import com.example.converterapp.domain.model.CurrencyEntity


class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {
    private var currencyList: List<CurrencyEntity> = emptyList()
    class CurrencyViewHolder(
        private val binding: CurrencyItemBinding
    ) : ViewHolder(binding.root) {
        fun bind(currency: CurrencyEntity) {
            binding.textViewName.text = currency.name
            binding.textViewChanges.text = currency.rateChange.toString()
            binding.textViewCurrentRate.text = currency.currentRate.toString()
            binding.root.setBackgroundColor(currency.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding =
            CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencyList[position]
        holder.bind(currency)
    }

    override fun getItemCount() = currencyList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<CurrencyEntity>){
        currencyList = list
        notifyDataSetChanged()
    }
}