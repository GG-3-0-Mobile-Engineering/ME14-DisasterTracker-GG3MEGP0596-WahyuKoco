package com.kocci.disastertracker.presenter.menu.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.kocci.disastertracker.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarSetting.setupWithNavController(findNavController())

        /**
         * There is bug with material3 dropdown (in time period feature).
         * After ui recreated, it doesn't appear any option.
         * https://github.com/material-components/material-components-android/issues/1464
         */


        viewModel.darkThemeLiveData().observe(viewLifecycleOwner) { darkMode ->
            binding.switchDarkMode.isChecked = darkMode
        }

        viewModel.timePeriodLiveData().observe(viewLifecycleOwner) { time ->
            binding.acTvTimePeriod.setText(time.showToUi, false)
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateDarkTheme(isChecked)
        }

        val availableTime = viewModel.getAvailableTime().map { it.showToUi }.toTypedArray()

        binding.acTvTimePeriod.apply {
            isSaveEnabled = false //? to solve bug issues
            (this as? MaterialAutoCompleteTextView)?.setSimpleItems(availableTime)
            setOnItemClickListener { _, _, _, _ ->
                val text = binding.acTvTimePeriod.text.toString()
                viewModel.updateTimePeriod(text)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}