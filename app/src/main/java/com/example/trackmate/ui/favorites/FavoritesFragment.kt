package com.example.trackmate.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.trackmate.R

class FavoritesFragment : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        // Obtén la referencia al ícono de corazón en tu layout
        val heartIcon: ImageView = view.findViewById(R.id.iv_heart)
        val heartIcon2: ImageView = view.findViewById(R.id.iv_heart2)
        val heartIcon3: ImageView = view.findViewById(R.id.iv_heart3)
        val heartIcon4: ImageView = view.findViewById(R.id.iv_heart4)

        // Asigna el listener de clic para cambiar el ícono
        heartIcon.setOnClickListener {
            toggleHeart(it)
        }
        heartIcon2.setOnClickListener {
            toggleHeart(it)
        }
        heartIcon3.setOnClickListener {
            toggleHeart(it)
        }
        heartIcon4.setOnClickListener {
            toggleHeart(it)
        }

        return view
    }
}

// Define la función toggleHeart dentro de la clase
private fun toggleHeart(view: View) {
    val heartIcon = view as ImageView
    val isFilled = heartIcon.tag == "filled"
    if (isFilled) {
        heartIcon.setImageResource(R.drawable.ic_heart_filled)
        heartIcon.tag = "outline"
    } else {
        heartIcon.setImageResource(R.drawable.ic_heart_outline)
        heartIcon.tag = "filled"
    }
}
