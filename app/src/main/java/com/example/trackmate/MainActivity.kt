package com.example.trackmate

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.trackmate.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Desactivar ActionBar
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        navView.visibility = View.GONE // Ocultar la barra al inicio

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_homepage,
                R.id.navigation_routes,
                R.id.navigation_favorites,
                R.id.navigation_configuration,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Verificar si hay sesión activa
        checkSession()
    }

    private fun checkSession() {
        if (auth.currentUser != null) {
            // Usuario autenticado, mostrar barra de navegación
            showBottomNavigation()
        } else {
            // Redirigir al login si no hay sesión activa
            findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_dashboard)
        }
    }

    fun showBottomNavigation() {
        binding.navView.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        binding.navView.visibility = View.GONE
    }

    fun toggleHeart(view: View) {}
}