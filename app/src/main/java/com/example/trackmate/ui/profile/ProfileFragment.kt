package com.example.trackmate.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.trackmate.R
import android.app.AlertDialog
import androidx.navigation.fragment.findNavController

class ProfileFragment : Fragment() {

    private lateinit var etNombre: EditText
    private lateinit var etEmail: EditText
    private lateinit var etCelular: EditText
    private lateinit var etDeporteFavorito: EditText
    private lateinit var etAltura: EditText
    private lateinit var etPeso: EditText
    private lateinit var etCiudad: EditText
    private lateinit var etGenero: EditText
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
        etDeporteFavorito = view.findViewById(R.id.et_deporte_favorito)
        etAltura = view.findViewById(R.id.et_altura)
        etPeso = view.findViewById(R.id.et_peso)
        etCiudad = view.findViewById(R.id.et_ciudad)
        etGenero = view.findViewById(R.id.et_genero)
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

        // Configurar el botón Cerrar Sesión
        btnCerrarSesion.setOnClickListener {
            mostrarDialogCerrarSesion()
        }

        return view
    }

    private fun toggleEditMode(editable: Boolean) {
        etNombre.isEnabled = editable
        etEmail.isEnabled = editable
        etCelular.isEnabled = editable
        etDeporteFavorito.isEnabled = editable
        etAltura.isEnabled = editable
        etPeso.isEnabled = editable
        etCiudad.isEnabled = editable
        etGenero.isEnabled = editable

        btnEditar.visibility = if (editable) View.GONE else View.VISIBLE
        btnGuardar.visibility = if (editable) View.VISIBLE else View.GONE
    }

    // Método para mostrar el Dialog de confirmación de cierre de sesión
    private fun mostrarDialogCerrarSesion() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Cerrar sesión")
        builder.setMessage("¿Estás seguro que deseas cerrar sesión?")

        builder.setPositiveButton("Sí") { _, _ ->
            // Aquí puedes agregar la lógica para cerrar sesión
            cerrarSesion()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss() // Cerrar el diálogo
        }

        val dialog = builder.create()
        dialog.show()
    }

    // Método para cerrar sesión
    private fun cerrarSesion() {
        // Aquí se puede limpiar la sesión, eliminar tokens, o navegar a otra pantalla
        findNavController().navigate(R.id.navigation_home)
    }
}