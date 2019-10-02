package nl.worth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import nl.worth.clangnotifications.Clang
import nl.worth.poll.PollActivity


class MainActivity : AppCompatActivity() {

    lateinit var clang: Clang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val registerBtn = findViewById<Button>(R.id.register)
        val loginBtn = findViewById<Button>(R.id.login)
        val pollBtn = findViewById<Button>(R.id.poll)

        clang = Clang.getInstance(this)

        registerBtn.setOnClickListener {
            clang.createAccount(this, {
                Toast.makeText(applicationContext, it.id, Toast.LENGTH_LONG).show()
            }, {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
            })
        }

        loginBtn.setOnClickListener {
            startActivity(LoginActivity.getIntent(this))
        }
        pollBtn.setOnClickListener {
            startActivity(PollActivity.getIntent(this))
        }
    }
}
