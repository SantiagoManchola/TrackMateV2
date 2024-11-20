package com.example.trackmate.ui.configuration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.trackmate.R

class ConfigurationFragment : Fragment() {

    private lateinit var sectionPasswordSecurity: LinearLayout
    private lateinit var contentPasswordSecurity: LinearLayout
    private lateinit var arrowPasswordSecurity: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_configuration, container, false)

        // Referencias
        sectionPasswordSecurity = view.findViewById(R.id.section_password_security)
        contentPasswordSecurity = view.findViewById(R.id.content_password_security)
        arrowPasswordSecurity = view.findViewById(R.id.arrow_password_security)

        // Configurar el comportamiento del primer desplegable
        sectionPasswordSecurity.setOnClickListener {
            toggleSection(contentPasswordSecurity, arrowPasswordSecurity)
        }

        return view
    }

    // Alterna entre mostrar y ocultar el contenido de la sección
    private fun toggleSection(content: LinearLayout, arrow: ImageView) {
        if (content.visibility == View.VISIBLE) {
            content.visibility = View.GONE
            arrow.setImageResource(R.drawable.abajo)
        } else {
            content.visibility = View.VISIBLE
            arrow.setImageResource(R.drawable.derecha)
        }
    }
}
