package nl.worth.product_overview

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_product_overview.*
import nl.worth.R
import nl.worth.product_details.ProductDetailsActivity

class ProductOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_overview)

        val productOverviewAdapter = ProductOverviewAdapter(getDummyProducts())


        recycler_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = productOverviewAdapter

        productOverviewAdapter.onItemClick = { item ->
            startActivity(ProductDetailsActivity.getIntent(this, item.name))
        }

    }

    fun getDummyProducts() : List<ProductOverviewItem> {
        val products: ArrayList<ProductOverviewItem> = ArrayList()

        products.add(ProductOverviewItem("Raspberry Pi 4 Model B - 2GB", R.drawable.raspberry_pi, "€ 49.95"))
        products.add(ProductOverviewItem("Raspberry Pi Official Beginners Guide", R.drawable.book, "€ 11.00"))
        products.add(ProductOverviewItem("Raspberry Pi Official Beginners Guide", R.drawable.book, "€ 11.00"))
        products.add(ProductOverviewItem("Raspberry Pi 4 Model B - 2GB", R.drawable.raspberry_pi, "€ 49.95"))
        products.add(ProductOverviewItem("Raspberry Pi 4 Model B - 2GB", R.drawable.raspberry_pi, "€ 49.95"))
        products.add(ProductOverviewItem("Raspberry Pi 4 Model B - 2GB", R.drawable.raspberry_pi, "€ 49.95"))
        products.add(ProductOverviewItem("Raspberry Pi 4 Model B - 2GB", R.drawable.raspberry_pi, "€ 49.95"))
        products.add(ProductOverviewItem("Raspberry Pi 4 Model B - 2GB", R.drawable.raspberry_pi, "€ 49.95"))
        products.add(ProductOverviewItem("Raspberry Pi Official Beginners Guide", R.drawable.book, "€ 11.00"))
        products.add(ProductOverviewItem("Raspberry Pi Official Beginners Guide", R.drawable.book, "€ 11.00"))
        products.add(ProductOverviewItem("Raspberry Pi Official Beginners Guide", R.drawable.book, "€ 11.00"))
        products.add(ProductOverviewItem("Raspberry Pi Official Beginners Guide", R.drawable.book, "€ 11.00"))
        products.add(ProductOverviewItem("Raspberry Pi Official Beginners Guide", R.drawable.book, "€ 11.00"))

        return products

    }
}
