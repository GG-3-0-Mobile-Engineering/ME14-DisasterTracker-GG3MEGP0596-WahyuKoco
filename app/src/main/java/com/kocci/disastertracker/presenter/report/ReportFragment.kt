package com.kocci.disastertracker.presenter.report

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kocci.disastertracker.R
import com.kocci.disastertracker.databinding.FragmentReportBinding
import com.kocci.disastertracker.domain.model.Reports
import com.kocci.disastertracker.domain.model.reports.FloodReport
import com.kocci.disastertracker.domain.reactive.Async
import com.kocci.disastertracker.util.extension.gone
import com.kocci.disastertracker.util.extension.showToast
import com.kocci.disastertracker.util.extension.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReportViewModel by viewModels()

    private var provinceName: String? = null
    private var disasterType: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchAdapter()
        setupGoogleMaps()
        setupFilter()

        binding.btnReportRefresh.setOnClickListener {
            viewModel.callApi(provinceName, disasterType)
        }

        binding.acTvSearchReport.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                val text = binding.acTvSearchReport.text.toString()
                val keyboard = requireActivity().getSystemService(InputMethodManager::class.java)
                keyboard.hideSoftInputFromWindow(v.windowToken, 0)
                provinceName = text
                viewModel.callApi(provinceName, disasterType)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        viewModel.reports.observe(viewLifecycleOwner) {
            when (it) {
                is Async.Error -> {
                    binding.loadingReport.gone()
                    showToast(it.message)
                }

                Async.Loading -> {
                    binding.loadingReport.visible()
                }

                is Async.Success -> {
                    Log.d("TEST", "onViewCreated: ${it.data}")

                    binding.loadingReport.gone()
                    setupBottomSheetRvAdapter(it.data)

                    val floodDepthList = mutableListOf<Int>()
                    it.data.forEach { report ->
                        if (report is FloodReport) {
                            floodDepthList.add(report.floodDepth)
                        }
                    }
                    floodDepthList.maxOrNull()?.let { maxDepth ->
                        viewModel.showNotificationForFlood(maxDepth)
                    }
                }
            }
        }
    }

    private fun setupBottomSheetRvAdapter(data: List<Reports>) {
        val mAdapter = ReportListAdapter(data)
        val layout = LinearLayoutManager(requireActivity())
        binding.rvReportList.apply {
            adapter = mAdapter
            layoutManager = layout
        }
    }

    private fun setupFilter() {
        binding.rgReport.apply {
            check(binding.rbReportAll.id) //flood is the the first check
            setOnCheckedChangeListener { _, checkedId ->
                val radioButton = findViewById<RadioButton>(checkedId)
                val selectedText = radioButton.text.toString()
                disasterType = selectedText.lowercase()

                if (selectedText == getString(R.string.all_report)) {
                    disasterType = null
                }

                viewModel.callApi(provinceName, disasterType)
            }
        }
    }


    override fun onMapReady(maps: GoogleMap) {
        viewModel.reports.observe(viewLifecycleOwner) {
            when (it) {
                is Async.Error -> {
                    //already handled in reports
                }

                Async.Loading -> {
                    //already handled in reports
                }

                is Async.Success -> {
                    maps.clear()

                    val reports = it.data
                    val coordinates = reports[0].coordinates
                    val latLng = LatLng(coordinates.lat, coordinates.lng)
                    maps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8f))

                    reports.forEach { data ->
                        var snippet = "Disaster : ${data.disasterType}"
                        val position = LatLng(data.coordinates.lat, data.coordinates.lng)

                        if (data is FloodReport) {
                            snippet += ", Depth : ${data.floodDepth}"
                        }

                        val markerOpt = MarkerOptions()
                            .position(position)
                            .title(data.title)
                            .snippet(snippet)

                        maps.addMarker(markerOpt)
                    }
                }
            }
        }
    }

    private fun setupGoogleMaps() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun setupSearchAdapter() {
        val searchAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_dropdown_item_1line,
            viewModel.getAvailableProvince()
        )
        binding.acTvSearchReport.setAdapter(searchAdapter)
    }


}