package com.bitmovin.trdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide

class TRPlayerAdapter(
    private val listener: (Double) -> Unit,
) : Adapter<TRPlayerAdapter.Holder>() {
    private val items = ArrayList<TRPlayerViewModel.Item>()

    fun setItems(items: List<TRPlayerViewModel.Item>) {
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.item_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.root.setOnClickListener { listener.invoke(item.startTime) }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: View = itemView.findViewById(R.id.root)
        private val imageView: ImageView = itemView.findViewById(R.id.image)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val duration: TextView = itemView.findViewById(R.id.duration)

        fun bind(item: TRPlayerViewModel.Item) {
            title.text = item.title
            duration.text = "duration- > ${item.duration.toMinutesSeconds()} | start -> ${item.startTime.toMinutesSeconds()}"

            val imageUrl: String? = item.resources
                .firstOrNull { it.entityType == "ImageResource" }?.uri

            Glide
                .with(root.context)
                .load(imageUrl)
                .dontAnimate()
                .into(imageView)
        }
    }

}

fun Double.toMinutesSeconds(): String {
    if (this < 60) return "${this.toInt()}s"
    val minutes = (this / 60).toInt()
    val seconds = (this % 60).toInt()
    return "$minutes:$seconds"
}