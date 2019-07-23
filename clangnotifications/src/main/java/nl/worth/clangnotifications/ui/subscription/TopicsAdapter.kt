package nl.worth.clangnotifications.ui.subscription

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import nl.worth.clangnotifications.R
import androidx.recyclerview.widget.RecyclerView

internal class TopicsAdapter(private val topics: List<TopicItem>): RecyclerView.Adapter<TopicsAdapter.ViewHolder>() {

    var onItemClick: ((TopicItem) -> Unit)? = null

    override fun getItemCount() = topics.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.topic_row, parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(topics[position])
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.list_view_item_text)

        fun onBind(item: TopicItem) {
            text.text = item.topic
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(topics[adapterPosition])
            }
        }
    }
}