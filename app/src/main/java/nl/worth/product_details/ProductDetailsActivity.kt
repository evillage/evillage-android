package nl.worth.product_details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import nl.worth.R

class ProductDetailsActivity : AppCompatActivity() {

    lateinit var productDetails: ProductDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val title = findViewById<TextView>(R.id.name)
        val picture = findViewById<ImageView>(R.id.picture)
        val description = findViewById<TextView>(R.id.description)
        val price = findViewById<TextView>(R.id.price)

        productDetails = getProductDetails().first { product -> product.name == intent.getStringExtra(productName) }

        title.text = productDetails.name
        picture.setImageResource(productDetails.imageId)
        description.text = productDetails.description
        price.text = productDetails.price

    }

    companion object {
        internal val productName: String = "name"

        fun getProductDetails(): List<ProductDetails> {
            val products: ArrayList<ProductDetails> = ArrayList()

            products.add(
                ProductDetails(
                    "One Plus One 2014 3/64GB",
                    " Android Version: 5.1.1\n" +
                            " OS Version: Rooted Cyanogen OS 12.1.1\n" +
                            " Chipset: Qualcomm MSM8974AC Snapdragon 801 (28 nm)\n" +
                            " CPU: Quad-core 2.5 GHz Krait 400\n" +
                            " Display size: 5.5 inches, 83.4 cm2 (~71.9% screen-to-body ratio)\n" +
                            " Display resolution: 1080 x 1920 pixels, 16:9 ratio (~401 ppi density)",
                    "€ 336.95",
                    R.drawable.phone
                )
            )
            products.add(
                ProductDetails(
                    "Xiaomi Redmi 4 (4X) 2017 3/32GB",
                    " Android Version: 9.0\n" +
                            " OS Version: Rooted PixysOs (vanilla android)\n" +
                            " Chipset: Qualcomm MSM8940 Snapdragon 435 (28 nm)\n" +
                            " CPU: Octa-core 1.4 GHz Cortex-A53\n" +
                            " Display size: 5.0 inches, 68.9 cm2 (~70.7% screen-to-body ratio)\n" +
                            " Display resolution: 720 x 1280 pixels, 16:9 ratio (~294 ppi density)",
                    "€ 197.00",
                    R.drawable.phone
                )
            )
            products.add(
                ProductDetails(
                    "Google Pixel 2 2017 4/64GB",
                    " Android Version: 8.0\n" +
                            " OS Version: Rooted Cyanogen OS 12.1.1\n" +
                            " Chipset: Qualcomm MSM8998 Snapdragon 835 (10 nm)\n" +
                            " CPU: Octa-core (4x2.35 GHz Kryo & 4x1.9 GHz Kryo)\n" +
                            " Display size: 5.0 inches, 68.9 cm2 (~67.9% screen-to-body ratio)\n" +
                            " Display resolution: 1080 x 1920 pixels, 16:9 ratio (~441 ppi density)",
                    "€ 309.99",
                    R.drawable.phone
                )
            )
            products.add(
                ProductDetails(
                    "LG V30 2017 4/64GB",
                    " Android Version: 8.0\n" +
                            " OS Version: LG UX 6\n" +
                            " Chipset: Qualcomm MSM8998 Snapdragon 835 (10 nm)\n" +
                            " CPU: Octa-core (4x2.45 GHz Kryo & 4x1.9 GHz Kryo)\n" +
                            " Display size: 6.0 inches, 92.9 cm2 (~81.2% screen-to-body ratio)\n" +
                            " Display resolution: 1440 x 2880 pixels, 18:9 ratio (~537 ppi density)",
                    "€ 359.00",
                    R.drawable.phone
                )
            )
            products.add(
                ProductDetails(
                    "Lenovo Moto G (3rd gen) 2015 1/8GB",
                    " Android Version: 9.0\n" +
                            " OS Version: Rooted  Pixel Experince\n" +
                            " Chipset: Qualcomm MSM8916 Snapdragon 410 (28 nm)\n" +
                            " CPU: Quad-core 1.4 GHz Cortex-A53\n" +
                            " Display size: 5.0 inches, 68.9 cm2 (~67.0% screen-to-body ratio)\n" +
                            " Display resolution: 720 x 1280 pixels, 16:9 ratio (~294 ppi density)",
                    "€ 139.15",
                    R.drawable.phone
                )
            )

            return products
        }

        fun getIntent(context: Context, productItemName: String): Intent =
            Intent(context, ProductDetailsActivity::class.java).apply {
                putExtra(productName, productItemName)
            }
    }
}
