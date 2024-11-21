package com.example.trackmate.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trackmate.R
import com.example.trackmate.databinding.FragmentNotificationsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

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

            // Cambiar el estilo del texto a negrilla
            binding.loginLink.setTypeface(null, android.graphics.Typeface.BOLD)

            // Opcional: Cambiar el color del texto para resaltar
            binding.loginLink.setTextColor(resources.getColor(R.color.purple_500, null))

            findNavController().navigate(R.id.navigation_dashboard)
        }

//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.buttonRegister.setOnClickListener {

            val email : String = binding.emailInputRegister.text.toString().trim()
            val password : String = binding.passwordInputRegister.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                // Muestra un mensaje de error al usuario, por ejemplo, con un Toast
                Toast.makeText(requireContext(), "El correo electrónico y la contraseña no pueden estar vacíos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Agrega esta línea para detenerla ejecución

            }
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    task->
                if (task.isSuccessful) {
                    // Obtén el usuario recién creado
                    val user = auth.currentUser
                    val uid = user?.uid
                    val db = FirebaseFirestore.getInstance()

                    // Crea un nuevo documento con el correo del usuario
                    val userMap = hashMapOf(
                        "email" to email
                    )

                    // Agrega el documento al collection "users"
                    db.collection("users")
                        .document(uid ?: "")
                        .set(userMap)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.navigation_dashboard)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Error al guardar el correo en Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                else
                {
                    val toast = Toast.makeText(context, "La contraseña debe tener al menos seis caracteres", Toast.LENGTH_SHORT)
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