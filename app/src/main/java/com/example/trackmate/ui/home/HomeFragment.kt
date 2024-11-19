package com.example.trackmate.ui.home

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trackmate.R
import com.example.trackmate.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private var mediaPlayer: MediaPlayer? = null//variable para la melodia

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.audiodef)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        // Lógica para el botón que redirige a DashboardFragment
        binding.buttonCalories.setOnClickListener {
            // Carga la animación
            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.button_scale)
            // Aplica la animación al botón
            it.startAnimation(animation)

            // Navega a la siguiente pantalla después de un pequeño retraso
            it.postDelayed({
                findNavController().navigate(R.id.navigation_dashboard)
            }, 200) // Asegúrate de que el retraso coincida con la duración de la animación
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
        _binding = null
    }
}