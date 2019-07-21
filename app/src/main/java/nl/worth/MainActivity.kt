package nl.worth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import nl.worth.clangnotifications.ClangNotifications
import nl.worth.product_overview.ProductOverviewActivity


class MainActivity : AppCompatActivity() {

    lateinit var clangNotifications: ClangNotifications

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val registerBtn = findViewById<Button>(R.id.register)
        val productsBtn = findViewById<Button>(R.id.products)
        val mainText = findViewById<TextView>(R.id.mainText)

        clangNotifications = ClangNotifications.getInstance(this)

        registerBtn.setOnClickListener {
            clangNotifications.createAccountWithUI(this)
        }
        productsBtn.setOnClickListener {
            val intent = Intent(this, ProductOverviewActivity::class.java)
            startActivity(intent)
        }
    }
}
