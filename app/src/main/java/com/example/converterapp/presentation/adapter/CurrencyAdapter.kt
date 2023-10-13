package com.example.converterapp.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.converterapp.databinding.CurrencyItemBinding
import com.example.converterapp.domain.model.CurrencyEntity


class CurrencyAdapter(val context: Context) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {
    var currencyList: List<CurrencyEntity> = emptyList()

    inner class CurrencyViewHolder(val binding: CurrencyItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = CurrencyItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencyList[position]
        val currencyHolder = holder.binding
        currencyHolder.textViewName.text = currency.name
        currencyHolder.textViewChanges.text = currency.rateChange.toString()
        currencyHolder.textViewCurrentRate.text = currency.currentRate.toString()
        currencyHolder.root.setBackgroundColor(currency.color)

    }

    override fun getItemCount() = currencyList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<CurrencyEntity>){
        currencyList = list
        notifyDataSetChanged()
    }
}