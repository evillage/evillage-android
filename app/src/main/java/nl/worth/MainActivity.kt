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
        val mainText = findViewById<TextView>(R.id.mainText)

        clang = Clang.getInstance(this)

        registerBtn.setOnClickListener {
            clang.createAccount(this, {
                val toast = Toast.makeText(applicationContext, it.id, Toast.LENGTH_LONG)
                toast.show()
            }, {
                val toast = Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG)
                toast.show()
            })
        }

        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        pollBtn.setOnClickListener {
            val intent = Intent(this, PollActivity::class.java)
            startActivity(intent)
        }
    }
}
