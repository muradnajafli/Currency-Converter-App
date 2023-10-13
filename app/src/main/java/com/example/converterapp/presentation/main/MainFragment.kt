package com.example.converterapp.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converterapp.data.utils.API_KEY
import com.example.converterapp.databinding.FragmentMainBinding
import com.example.converterapp.presentation.adapter.CurrencyAdapter

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        setupRecyclerView()
        fetchData()
        observeEvents()

        return binding.root
    }

    private fun setupRecyclerView() {
        currencyAdapter = CurrencyAdapter(requireContext())
        binding.recyclerView.adapter = currencyAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun fetchData() {
        viewModel.fetchCurrencies(API_KEY, "EUR")
    }

    private fun observeEvents() {
        viewModel.currencyData.observe(viewLifecycleOwner) { currencyList ->
            currencyAdapter.updateData(currencyList)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBarMain.visibility = View.VISIBLE
            } else {
                binding.progressBarMain.visibility = View.GONE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            binding.errorMessageTv.visibility = View.VISIBLE
            binding.errorMessageTv.text = errorMessage.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
