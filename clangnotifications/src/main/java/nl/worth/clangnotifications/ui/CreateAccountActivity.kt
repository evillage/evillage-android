package nl.worth.clangnotifications.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import nl.worth.clangnotifications.ClangNotifications
import nl.worth.clangnotifications.R
import nl.worth.clangnotifications.ui.subscription.SubscriptionActivity

internal class CreateAccountActivity : AppCompatActivity() {

    lateinit var clangNotifications: ClangNotifications

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        val submit = findViewById<Button>(R.id.submit)
        val email = findViewById<EditText>(R.id.email)

        clangNotifications = ClangNotifications.getInstance(this)

        submit.setOnClickListener {
            clangNotifications.createAccoun(email.text.toString(),
                {
                    this.startActivity(Intent(this, SubscriptionActivity::class.java))
                },
                {
                    showAlertDialogOnErrorOccured(it)
                }
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) finish()
    }

    private fun showAlertDialogOnErrorOccured(throwable: Throwable) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error!Error!Panic!")
        builder.setMessage(throwable.message)

        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            Toast.makeText(
                applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT
            ).show()
        }
        builder.show()
    }
}
