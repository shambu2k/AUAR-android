package com.example.auar.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.auar.R
import com.example.auar.databinding.FragmentLobbyBinding
import com.example.auar.utils.viewLifecycle
import com.example.auar.viewmodels.MainViewModel

class LobbyFragment : Fragment() {

    private var binding by viewLifecycle<FragmentLobbyBinding>()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.getRate().observe(viewLifecycleOwner, Observer { it ->
            binding.lobbyPlayers.text = it.toString()
        })

        binding.leaveRoom.setOnClickListener {
            viewModel.leaveRoom(arguments?.getString("playerName")!!, arguments?.getString("roomId")!!)
            findNavController().navigate(R.id.action_lobbyFragment_to_homeFragment)
        }
    }
}
