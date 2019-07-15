package nl.worth.product_details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import nl.worth.R
import nl.worth.product_overview.ProductOverviewItem

class ProductDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val title = findViewById<TextView>(R.id.name)

    }

    companion object {
        internal val productItem: ProductOverviewItem = ProductOverviewItem()

        fun getIntent(context: Context, productItemName: String, productItemPrice: String): Intent =
            Intent(context, ProductDetailsActivity::class.java).apply {
                putExtra(productItem.name, productItemName)
                putExtra(productItem.price, productItemPrice)
            }
    }
}
