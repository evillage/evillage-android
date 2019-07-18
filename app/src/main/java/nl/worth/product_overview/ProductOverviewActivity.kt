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

        products.add(ProductOverviewItem("One Plus One 2014 3/64GB", R.drawable.phone, "€ 336.95"))
        products.add(ProductOverviewItem("Xiaomi Redmi 4 (4X) 2017 3/32GB", R.drawable.phone, "€ 197.00"))
        products.add(ProductOverviewItem("Google Pixel 2 2017 4/64GB", R.drawable.phone, "€ 309.99"))
        products.add(ProductOverviewItem("LG V30 2017 4/64GB", R.drawable.phone, "€ 359.00"))
        products.add(ProductOverviewItem("Lenovo Moto G (3rd gen) 2015 1/8GB", R.drawable.phone, "€ 139.15"))


        return products

    }
}
