package com.kocci.disastertracker.presenter.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kocci.disastertracker.R
import com.kocci.disastertracker.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val images = arrayOf(R.drawable.baseline_settings_48, R.drawable.baseline_info_48)
    private val menuName = arrayOf("Settings", "About")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list: MutableList<HashMap<String, Any>> = mutableListOf()
        for (i in menuName.indices) {
            val map = hashMapOf<String, Any>()
            map.apply {
                put("menuName", menuName[i])
                put("menuImage", images[i])
            }
            list.add(map)
        }
        val from = arrayOf("menuName", "menuImage")
        val to = intArrayOf(R.id.tvMenuName, R.id.imgMenuItem)

        val adapter = SimpleAdapter(context, list, R.layout.item_menu, from, to)
        binding.listViewMenu.adapter = adapter

        binding.listViewMenu.setOnItemClickListener { _, _, position, _ ->
            when (menuName[position]) {
                "Settings" -> {
                    val direction = MenuFragmentDirections.actionMenuFragmentToSettingFragment()
                    findNavController().navigate(direction)
                }
            }
        }
    }
}