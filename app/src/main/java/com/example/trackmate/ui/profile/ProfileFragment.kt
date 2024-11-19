package com.example.trackmate.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.trackmate.R

class ProfileFragment : Fragment() {

    private lateinit var etNombre: EditText
    private lateinit var etEmail: EditText
    private lateinit var etCelular: EditText
    private lateinit var btnEditar: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnCerrarSesion: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Referencias a los elementos
        etNombre = view.findViewById(R.id.et_nombre)
        etEmail = view.findViewById(R.id.et_email)
        etCelular = view.findViewById(R.id.et_celular)
        btnEditar = view.findViewById(R.id.btn_editar)
        btnGuardar = view.findViewById(R.id.btn_guardar)
        btnCerrarSesion = view.findViewById(R.id.btn_cerrar_sesion)

        // Configurar el botón Editar
        btnEditar.setOnClickListener {
            toggleEditMode(true)
        }

        // Configurar el botón Guardar
        btnGuardar.setOnClickListener {
            // Guardar cambios
            toggleEditMode(false)
        }

        return view
    }

    private fun toggleEditMode(editable: Boolean) {
        etNombre.isEnabled = editable
        etEmail.isEnabled = editable
        etCelular.isEnabled = editable

        btnEditar.visibility = if (editable) View.GONE else View.VISIBLE
        btnGuardar.visibility = if (editable) View.VISIBLE else View.GONE
    }
}