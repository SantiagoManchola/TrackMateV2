package com.example.trackmate.ui.dashboard

    import android.os.Bundle
    import android.view.LayoutInflater
    import com.example.trackmate.R
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import android.widget.Toast
    import androidx.fragment.app.Fragment
    import androidx.lifecycle.ViewModelProvider
    import androidx.navigation.fragment.findNavController
    import com.example.trackmate.databinding.FragmentDashboardBinding
    import com.google.firebase.Firebase
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.auth

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
            findNavController().navigate(R.id.navigation_notifications)
        }

       /*
       val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
           textView.text = it
        }
        */


        binding.buttonLogin.setOnClickListener {

            val email : String = binding.userInputLogin.text.toString()
            val password : String = binding.passwordInputLogin.text.toString()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                task->
                if(task.isSuccessful)
                {
                    val toast = Toast.makeText(context, "INICIO DE SESION CORRECTO", Toast.LENGTH_SHORT)
                    toast.show()
                    findNavController().navigate(R.id.navigation_homepage)
                }
                else
                {
                    val toast = Toast.makeText(context, "LOS DATOS SON INCORRECTOS", Toast.LENGTH_SHORT)
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