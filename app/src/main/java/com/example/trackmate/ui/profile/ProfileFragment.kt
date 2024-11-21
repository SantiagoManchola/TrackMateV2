package com.example.trackmate.ui.profile

import android.app.Activity
import android.app.AlertDialog
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
import androidx.navigation.fragment.findNavController
import com.example.trackmate.R
import com.example.trackmate.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Context

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
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val userId: String?
        get() = auth.currentUser?.uid

    private val PICK_IMAGE_REQUEST = 1 // Código de solicitud para abrir la galería
    private val REQUEST_IMAGE_CAPTURE = 2 // Código para tomar una foto

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
        imgUser = view.findViewById(R.id.img_user)

        // Cargar datos del usuario desde Firestore
        loadUserData()

        // Configurar el botón Editar
        btnEditar.setOnClickListener { toggleEditMode(true) }

        // Configurar el botón Guardar
        btnGuardar.setOnClickListener { saveUserData() }

        // Configurar clic en la imagen para cambiarla
        imgUser.setOnClickListener {
            showImageSourceDialog()
        }

        // Configurar el botón Cerrar Sesión
        btnCerrarSesion.setOnClickListener { mostrarDialogCerrarSesion() }

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

    private fun loadUserData() {
        userId?.let { id ->
            firestore.collection("users").document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        etNombre.setText(document.getString("name"))
                        etEmail.setText(document.getString("email"))
                        etCelular.setText(document.getString("phone"))
                        etDeporteFavorito.setText(document.getString("favoriteSport"))
                        etAltura.setText(document.getString("height"))
                        etPeso.setText(document.getString("weight"))
                        etCiudad.setText(document.getString("city"))
                        etGenero.setText(document.getString("gender"))
                    }
                }
                .addOnFailureListener { e ->
                    // Manejar errores
                }
        }
    }

    private fun saveUserData() {
        val userMap = hashMapOf(
            "name" to etNombre.text.toString(),
            "email" to etEmail.text.toString(),
            "phone" to etCelular.text.toString(),
            "favoriteSport" to etDeporteFavorito.text.toString(),
            "height" to etAltura.text.toString(),
            "weight" to etPeso.text.toString(),
            "city" to etCiudad.text.toString(),
            "gender" to etGenero.text.toString()
        )

        userId?.let { id ->
            firestore.collection("users").document(id).set(userMap)
                .addOnSuccessListener {
                    toggleEditMode(false)
                }
                .addOnFailureListener { e ->
                    // Manejar errores
                }
        }
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Tomar foto", "Seleccionar desde galería")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Seleccionar imagen")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> openCamera() // Opción para tomar foto
                1 -> openGallery() // Opción para seleccionar desde galería
            }
        }
        builder.show()
    }

    // Abre la galería del dispositivo
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Abre la cámara frontal del dispositivo
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    // Maneja el resultado de la selección de imagen o foto
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    val imageUri: Uri? = data?.data
                    if (imageUri != null) {
                        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("profile_image", imageUri.toString())
                            apply()
                        }
                        imgUser.setImageURI(imageUri)
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    val photo: Bitmap? = data?.extras?.get("data") as? Bitmap
                    if (photo != null) {
                        // Guarda la imagen en el almacenamiento local o Firebase si es necesario
                        imgUser.setImageBitmap(photo)
                    }
                }
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