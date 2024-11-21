package com.example.trackmate.ui.configuration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.trackmate.R

class ConfigurationFragment : Fragment() {

    private lateinit var sectionPasswordSecurity: LinearLayout
    private lateinit var contentPasswordSecurity: LinearLayout

    private lateinit var sectionPersonalData: LinearLayout
    private lateinit var contentPersonalData: LinearLayout

    private var sectionActivity: LinearLayout? = null
    private var contentActivity: LinearLayout? = null

    private var sectionPrivacy: LinearLayout? = null
    private var contentPrivacy: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_configuration, container, false)

        // Inicializar referencias para secciones obligatorias
        sectionPasswordSecurity = view.findViewById(R.id.section_password_security)
        contentPasswordSecurity = view.findViewById(R.id.content_password_security)

        sectionPersonalData = view.findViewById(R.id.section_personal_data)
        contentPersonalData = view.findViewById(R.id.content_personal_data)

        // Inicializar referencias para secciones opcionales
        sectionActivity = view.findViewById(R.id.section_activity)
        contentActivity = view.findViewById(R.id.content_activity)

        sectionPrivacy = view.findViewById(R.id.section_privacy)
        contentPrivacy = view.findViewById(R.id.content_privacy)

        // Configurar secciones desplegables
        setupExpandableSection(sectionPasswordSecurity, contentPasswordSecurity)
        setupExpandableSection(sectionPersonalData, contentPersonalData)

        sectionActivity?.let { section ->
            contentActivity?.let { content ->
                setupExpandableSection(section, content)
            }
        }

        sectionPrivacy?.let { section ->
            contentPrivacy?.let { content ->
                setupExpandableSection(section, content)
            }
        }

        return view
    }

    private fun setupExpandableSection(section: LinearLayout, content: LinearLayout) {
        section.setOnClickListener {
            content.visibility = if (content.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }
}
