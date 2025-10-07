package com.example.lab_week_6_new.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_week_6_new.ImageLoader
import com.example.lab_week_6_new.model.CatBreed
import com.example.lab_week_6_new.model.CatModel
import com.example.lab_week_6_new.model.Gender
import com.example.lab_week_6_new.R

private val FEMALE_SYMBOL = "\u2640"
private val MALE_SYMBOL = "\u2642"
private const val UNKNOWN_SYMBOL = "?"

class CatViewHolder(containerView: View, private val imageLoader: ImageLoader) :
    RecyclerView.ViewHolder(containerView) {

    private val catBiographyView: TextView by lazy { containerView.findViewById<TextView>(R.id.cat_biography) }
    private val catBreedView: TextView by lazy { containerView.findViewById<TextView>(R.id.cat_breed) }
    private val catGenderView: TextView by lazy { containerView.findViewById<TextView>(R.id.cat_gender) }
    private val catNameView: TextView by lazy { containerView.findViewById<TextView>(R.id.cat_name) }
    private val catPhotoView: ImageView by lazy { containerView.findViewById<ImageView>(R.id.cat_photo) }

    fun bindData(cat: CatModel) {
        imageLoader.loadImage(cat.imageUrl, catPhotoView)
        catNameView.text = cat.name
        catBreedView.text = when (cat.breed) {
            CatBreed.AmericanCurl -> "American Curl"
            CatBreed.BalineseJavanese -> "Balinese-Javanese"
            CatBreed.ExoticShorthair -> "Exotic Shorthair"
        }
        catBiographyView.text = cat.biography
        catGenderView.text = when (cat.gender) {
            Gender.Female -> FEMALE_SYMBOL
            Gender.Male -> MALE_SYMBOL
            Gender.Unknown -> UNKNOWN_SYMBOL
        }
    }
}
