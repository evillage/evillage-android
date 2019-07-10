package nl.worth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import nl.worth.clangnotifications.ClangNotifications


class MainActivity : AppCompatActivity() {

    lateinit var clangNotifications: ClangNotifications

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val registerBtn = findViewById<Button>(R.id.register)
        val mainText = findViewById<TextView>(R.id.mainText)

        clangNotifications = ClangNotifications.getInstance()

        registerBtn.setOnClickListener {
            clangNotifications.createAccountWithUI(this)
        }
    }
}
