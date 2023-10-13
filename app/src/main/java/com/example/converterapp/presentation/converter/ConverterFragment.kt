package com.example.converterapp.presentation.converter

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.converterapp.R
import com.example.converterapp.databinding.FragmentConverterBinding
import kotlinx.coroutines.launch


class ConverterFragment : Fragment() {
    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ConverterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)

        binding.btnConvert.setOnClickListener {
            onConvertButtonClicked()
        }

        val currencyCodes = resources.getStringArray(R.array.currency_codes)

        binding.spFromCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCurrency = currencyCodes[position]
                viewModel.setBaseCurrency(selectedCurrency)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.spToCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCurrency = currencyCodes[position]
                viewModel.setConvertedToCurrency(selectedCurrency)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        viewModel.convertedValue.observe(viewLifecycleOwner) { convertedValue ->
            binding.tvResult.text = convertedValue
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onConvertButtonClicked() {
        val amountStr = binding.etFrom.text.toString()
        viewModel.convertValue(amountStr)
    }
}
