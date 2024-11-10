package com.example.trackmate.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import com.example.trackmate.R
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class HomepageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)

        // Obtén la referencia al ícono de corazón en tu layout
        val heartIcon: ImageView = view.findViewById(R.id.iv_heart)

        // Asigna el listener de clic para cambiar el ícono
        heartIcon.setOnClickListener {
            toggleHeart(it)
        }

        return view
    }

    // Define la función toggleHeart dentro de la clase
    private fun toggleHeart(view: View) {
        val heartIcon = view as ImageView
        val isFilled = heartIcon.tag == "filled"
        if (isFilled) {
            heartIcon.setImageResource(R.drawable.ic_heart_outline)
            heartIcon.tag = "outline"
        } else {
            heartIcon.setImageResource(R.drawable.ic_heart_filled)
            heartIcon.tag = "filled"
        }
    }
}
