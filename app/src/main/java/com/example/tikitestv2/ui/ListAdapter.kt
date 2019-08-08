package com.example.tikitestv2.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tikitestv2.R

class ListAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    private var data = ArrayList<String>()

    fun addList(list: List<String>?) {
        data.clear()
        list?.let {
            data.addAll(list)
            notifyItemRangeInserted(0, list.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        holder.bindData(data[pos])
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, holder.text.text, Toast.LENGTH_SHORT).show()
        }
    }


}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text: TextView = itemView.findViewById(R.id.text)

    fun bindData(data: String) {
        text.text = data
        (itemView as CardView).setCardBackgroundColor(getColorValue())
    }

    private fun getColorValue(): Int {
        val rnd = java.util.Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(168), rnd.nextInt(256))
    }
}