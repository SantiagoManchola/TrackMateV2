package com.example.trackmate.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trackmate.R
import com.example.trackmate.databinding.FragmentNotificationsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = Firebase.auth

        // Configura el botón "Regresar" para volver a la vista anterior
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()  // Navega a la vista anterior
        }

        binding.loginLink.setOnClickListener {
            findNavController().navigate(R.id.navigation_dashboard)
        }

//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.buttonRegister.setOnClickListener {

            val email : String = binding.emailInputRegister.text.toString()
            val password : String = binding.passwordInputRegister.text.toString()

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    task->
                if(task.isSuccessful)
                {
                    val toast = Toast.makeText(context, "INICIO DE SESION CORRECTO", Toast.LENGTH_SHORT)
                    toast.show()
                    findNavController().navigate(R.id.navigation_dashboard)
                }
                else
                {
                    val toast = Toast.makeText(context, "LA CONTRASEÑA DEBE TENER ALMENOS SEIS CARACTERES", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}