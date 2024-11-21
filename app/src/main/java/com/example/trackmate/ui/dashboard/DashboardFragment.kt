package com.example.trackmate.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import com.example.trackmate.R
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trackmate.databinding.FragmentDashboardBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.example.trackmate.MainActivity
import com.google.firebase.firestore.FirebaseFirestore

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Configura el botón "Regresar" para volver a la vista anterior
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)  // Navega a la vista anterior
        }

        binding.registerLink.setOnClickListener {
            // Cambiar el estilo del texto a negrilla
            binding.registerLink.setTypeface(null, android.graphics.Typeface.BOLD)

            // Opcional: Cambiar el color del texto para resaltar
            binding.registerLink.setTextColor(resources.getColor(R.color.purple_500, null))

            findNavController().navigate(R.id.navigation_notifications)
        }

       /*
       val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
           textView.text = it
        }
        */


        binding.buttonLogin.setOnClickListener {
            val email : String = binding.userInputLogin.text.toString().trim()
            val password : String = binding.passwordInputLogin.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                // Muestra un mensaje de error al usuario
                Toast.makeText(requireContext(), "No puedes iniciar sesión con datos sin llenar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Detiene la ejecución del listener
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid
                    val db = FirebaseFirestore.getInstance()

                    // Recupera el documento del usuario desde Firestore
                    db.collection("users")
                        .document(uid ?: "")
                        .get()
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Error al recuperar el correo desde Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                        }

                    val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.button_scale)
                    it.startAnimation(animation)

                    Toast.makeText(context, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show()

                    (requireActivity() as MainActivity).showBottomNavigation()

                    it.postDelayed({
                        findNavController().navigate(R.id.navigation_homepage)
                    }, 50)
                } else{
                    val toast = Toast.makeText(context, "Los datos son incorrectos", Toast.LENGTH_SHORT)
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