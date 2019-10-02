package nl.worth.product_overview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nl.worth.R
import nl.worth.product_details.ProductDetailsActivity

class ProductOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_overview)

        val productOverviewAdapter = ProductOverviewAdapter(getDummyProducts())
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productOverviewAdapter

        productOverviewAdapter.onItemClick = { item ->
            startActivity(ProductDetailsActivity.getIntent(this, item.name))
        }

    }

    fun getDummyProducts(): List<ProductOverviewItem> = listOf(
        ProductOverviewItem("One Plus One 2014 3/64GB", R.drawable.phone, "€ 336.95"),
        ProductOverviewItem("Xiaomi Redmi 4 (4X) 2017 3/32GB", R.drawable.phone, "€ 197.00"),
        ProductOverviewItem("Google Pixel 2 2017 4/64GB", R.drawable.phone, "€ 309.99"),
        ProductOverviewItem("LG V30 2017 4/64GB", R.drawable.phone, "€ 359.00"),
        ProductOverviewItem("Lenovo Moto G (3rd gen) 2015 1/8GB", R.drawable.phone, "€ 139.15")
    )
}
