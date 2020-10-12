package com.example.auar.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.auar.R
import com.example.auar.databinding.FragmentHomeBinding
import com.example.auar.utils.viewLifecycle
import com.example.auar.viewmodels.MainViewModel

class HomeFragment : Fragment() {

    private var binding by viewLifecycle<FragmentHomeBinding>()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.createRoom.setOnClickListener {
            viewModel.createRoom(binding.playerName.text.toString(), binding.roomId.text.toString())
            findNavController().navigate(R.id.action_homeFragment_to_lobbyFragment)
        }
        binding.joinRoom.setOnClickListener {
            viewModel.joinRoom(
                binding.playerName.text.toString(),
                binding.roomId.text.toString()
            )
            var bundle = bundleOf("playerName" to binding.playerName.text.toString(), "roomId" to binding.roomId.text.toString())
            findNavController().navigate(R.id.action_homeFragment_to_lobbyFragment, bundle)
        }
    }
}
