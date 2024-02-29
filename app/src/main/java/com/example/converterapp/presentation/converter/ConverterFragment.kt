package com.example.converterapp.presentation.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.converterapp.R
import com.example.converterapp.databinding.FragmentConverterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConverterFragment : Fragment() {
    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ConverterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectUiState()
        setupSpinner()
        setupConvertButton()
    }

    private fun setupSpinner() {
        val currencyCodes = resources.getStringArray(R.array.currency_codes)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            currencyCodes
        )
        binding.spToCurrency.adapter = adapter
        binding.spToCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCurrency = currencyCodes[position]
                    viewModel.setConvertedToCurrency(selectedCurrency)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            combine(
                viewModel.convertedValue,
                viewModel.errorMessage
            ) { convertedValue, errorMessage ->
                Pair(convertedValue, errorMessage)
            }.flowWithLifecycle(lifecycle)
                .collect {
                    val (convertedValue, errorMessage) = it
                    binding.tvResult.text = convertedValue
                    if (errorMessage != null) {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupConvertButton() {
        binding.btnConvert.setOnClickListener {
            val amountStr = binding.etFrom.text.toString()
            viewModel.convertValue(amountStr)
        }
    }
}