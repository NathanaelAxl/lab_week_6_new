package com.example.lab_week_6_new.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_week_6_new.ImageLoader
import com.example.lab_week_6_new.R
import com.example.lab_week_6_new.model.CatModel

class CatAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<CatViewHolder>() {

    // 4) Delete Callback Instantiation (exposed so Activity bisa attach)
    val swipeToDeleteCallback = SwipeToDeleteCallback()

    // Mutable list for storing all the list data
    private val cats = mutableListOf<CatModel>()

    // 2) setData
    fun setData(newCats: List<CatModel>) {
        cats.clear()
        cats.addAll(newCats)
        notifyDataSetChanged()
    }

    // 1) removeItem function
    fun removeItem(position: Int) {
        if (position in 0 until cats.size) {
            cats.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    // 3) Inner class for swipe-to-delete
    inner class SwipeToDeleteCallback :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        // We don't support move (drag & drop) in this example
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        // Optionally restrict swipe to only our ViewHolder type
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return if (viewHolder is CatViewHolder) {
                // no drag, allow left & right swipe
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            } else {
                0
            }
        }

        // Called when swipe detected — remove the item from adapter
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                removeItem(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = layoutInflater.inflate(R.layout.item_list, parent, false)
        return CatViewHolder(view, imageLoader, onClickListener)
    }

    override fun getItemCount() = cats.size

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bindData(cats[position])
    }

    // Listener interface for clicks (ke MainActivity)
    interface OnClickListener {
        fun onItemClick(cat: CatModel)
    }
}
