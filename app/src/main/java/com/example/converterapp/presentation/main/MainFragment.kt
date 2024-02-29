package com.example.converterapp.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converterapp.data.utils.Constants.API_KEY
import com.example.converterapp.databinding.FragmentMainBinding
import com.example.converterapp.presentation.adapter.CurrencyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchData()
        observeEvents()
    }

    private fun setupRecyclerView() {
        currencyAdapter = CurrencyAdapter()
        binding.recyclerView.adapter = currencyAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun fetchData() {
        viewModel.fetchCurrencies(API_KEY, "EUR")
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            combine(
                viewModel.currencyData,
                viewModel.loading,
                viewModel.error
            ) { currencyList, isLoading, errorMessage ->
                Triple(currencyList, isLoading, errorMessage)
            }.flowWithLifecycle(lifecycle)
                .collect { (currencyList, isLoading, errorMessage) ->
                    currencyAdapter.updateData(currencyList)
                    if (isLoading) {
                        binding.progressBarMain.visibility = View.VISIBLE
                    } else {
                        binding.progressBarMain.visibility = View.GONE
                    }
                    if (errorMessage != null) {
                        binding.errorMessageTv.visibility = View.VISIBLE
                        binding.errorMessageTv.text = errorMessage
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
