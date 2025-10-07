package com.example.lab_week_6_new.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_week_6_new.ImageLoader
import com.example.lab_week_6_new.R
import com.example.lab_week_6_new.model.CatBreed
import com.example.lab_week_6_new.model.CatModel
import com.example.lab_week_6_new.model.Gender
import android.widget.ImageView
import android.widget.TextView

class CatAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    // 1️⃣ Data list
    private val cats = mutableListOf<CatModel>()

    // 2️⃣ Fungsi setData untuk mengisi list baru
    fun setData(newCats: List<CatModel>) {
        cats.clear()
        cats.addAll(newCats)
        notifyDataSetChanged()
    }

    // 3️⃣ Fungsi hapus item
    fun removeItem(position: Int) {
        if (position in 0 until cats.size) {
            cats.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    // 4️⃣ Swipe-to-delete callback (dipakai di MainActivity)
    val swipeToDeleteCallback = SwipeToDeleteCallback()

    // 5️⃣ ViewHolder untuk menampilkan setiap item kucing
    inner class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val catNameView: TextView = itemView.findViewById(R.id.cat_name)
        private val catBreedView: TextView = itemView.findViewById(R.id.cat_breed)
        private val catBiographyView: TextView = itemView.findViewById(R.id.cat_biography)
        private val catGenderView: TextView = itemView.findViewById(R.id.cat_gender)
        private val catPhotoView: ImageView = itemView.findViewById(R.id.cat_photo)

        fun bindData(cat: CatModel) {
            // Load image pakai Glide
            imageLoader.loadImage(cat.imageUrl, catPhotoView)

            // Text binding
            catNameView.text = cat.name
            catBreedView.text = when (cat.breed) {
                CatBreed.AmericanCurl -> "American Curl"
                CatBreed.BalineseJavanese -> "Balinese-Javanese"
                CatBreed.ExoticShorthair -> "Exotic Shorthair"
                CatBreed.MaineCoon -> "Maine Coon"
                CatBreed.Bengal -> "Bengal"
                CatBreed.Sphynx -> "Sphynx"
                CatBreed.Siamese -> "Siamese"
                CatBreed.Abyssinian -> "Abyssinian"
                CatBreed.Persian -> "Persian"
                CatBreed.Ragdoll -> "Ragdoll"
                CatBreed.ScottishFold -> "Scottish Fold"
                CatBreed.Birman -> "Birman"
                CatBreed.Oriental -> "Oriental"
            }

            catBiographyView.text = cat.biography

            // Gender symbol
            catGenderView.text = when (cat.gender) {
                Gender.Male -> "\u2642"    // ♂
                Gender.Female -> "\u2640"  // ♀
                Gender.Unknown -> "?"       // Unknown
            }

            // Klik listener
            itemView.setOnClickListener {
                onClickListener.onItemClick(cat)
            }
        }
    }

    // 6️⃣ Inner class untuk swipe-to-delete
    inner class SwipeToDeleteCallback :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return if (viewHolder is CatViewHolder) {
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            } else 0
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                removeItem(position)
            }
        }
    }

    // 7️⃣ Adapter standard override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = layoutInflater.inflate(R.layout.item_list, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bindData(cats[position])
    }

    override fun getItemCount(): Int = cats.size

    // 8️⃣ Listener interface
    interface OnClickListener {
        fun onItemClick(cat: CatModel)
    }
}
