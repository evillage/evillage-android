package nl.worth.product_overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nl.worth.R

class ProductOverviewAdapter(private val productList: List<ProductOverviewItem>):
    RecyclerView.Adapter<ProductOverviewAdapter.ViewHolder>() {

    var onItemClick: ((ProductOverviewItem) -> Unit)? = null


    override fun getItemCount() = productList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.product_overview_row, parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(productList[position])
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.name)
        val productPrice: TextView = view.findViewById(R.id.price)
        val productThumbnail: ImageView = view.findViewById(R.id.thumbnail)

        fun onBind(productOverviewItem: ProductOverviewItem) {
            productName.text = productOverviewItem.name
            productPrice.text = productOverviewItem.price
            productThumbnail.setImageResource(productOverviewItem.thumbnailId)
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(productList[adapterPosition])
            }
        }
    }

}

