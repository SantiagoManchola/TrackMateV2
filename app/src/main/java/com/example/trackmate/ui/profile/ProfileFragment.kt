package com.example.trackmate.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.trackmate.R
import java.io.IOException
import android.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.example.trackmate.MainActivity

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
    private lateinit var imgUser: ImageView
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val PICK_IMAGE_REQUEST = 1 // Código de solicitud para abrir la galería

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
        imgUser = view.findViewById(R.id.img_user) // Imagen del usuario

        // Configurar el botón Editar
        btnEditar.setOnClickListener {
            toggleEditMode(true)
        }

        // Configurar el botón Guardar
        btnGuardar.setOnClickListener {
            // Guardar cambios
            toggleEditMode(false)
        }

        // Configurar clic en la imagen para cambiarla
        imgUser.setOnClickListener {
            openGallery()
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

    // Abre la galería del dispositivo
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Maneja el resultado de la selección de imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            try {
                // Convierte la URI en un Bitmap y lo establece en el ImageView
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                imgUser.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
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
        // Cerrar sesión de Firebase
        auth.signOut()

        // Ocultar barra de navegación
        (requireActivity() as MainActivity).hideBottomNavigation()

        // Navegar a la pantalla de login o inicio
        findNavController().navigate(R.id.navigation_home)
    }
}