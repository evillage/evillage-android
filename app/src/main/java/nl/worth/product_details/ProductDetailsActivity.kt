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
        price.text  = productDetails.price

    }

    companion object {
        internal val productName: String = "name"

        fun getProductDetails(): List<ProductDetails> {
            val products: ArrayList<ProductDetails> = ArrayList()

            products.add(
                ProductDetails(
                    "Raspberry Pi 4 Model B - 2GB",
                    "De Raspberry Pi 4 Model B 2GB is het nieuwste lid van de Raspberry Pi familie. De Raspberry Pi 4 biedt een baanbrekende toename in snelheid van de processor, multimedia prestaties, geheugen en connectiviteit in vergelijking met de Raspberry Pi 3 Model B+, met behoud van backwards compatibility en een bijna gelijk stroomverbruik. Voor de eindgebruiker biedt Raspberry Pi 4 Model B desktopprestaties vergelijkbaar met instapniveau x86 pc-systemen.",
                    "€ 49.95",
                    R.drawable.raspberry_pi
                )
            )
            products.add(
                ProductDetails(
                    "Raspberry Pi Official Beginners Guide",
                    "Discover the 50 best Raspberry Pi tips in this month's edition of The MagPi.The Magpi Team has come together to offer a definitive collection of hacks, hints, and tricks. Covering Raspbian, security, coding, making, networking, and the command line. This is a definitive collection of essential advice for any Raspberry Pi maker.",
                    "€ 11.23",
                    R.drawable.book
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
