package nl.worth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import nl.worth.clangnotifications.ClangNotifications
import nl.worth.clangnotifications.data.model.PollData
import nl.worth.thank_you.ThankYouActivity

class LoginActivity : AppCompatActivity() {

    lateinit var clangNotifications: ClangNotifications

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val submit = findViewById<Button>(R.id.submit)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        clangNotifications = ClangNotifications.getInstance(this)

        submit.setOnClickListener {
            clangNotifications.logEvent(this, "login", mutableListOf(PollData("login", email.text.toString())),
                {
                    startActivityForResult(ThankYouActivity.getIntent(this, email.text.toString()), 0)
                },
                {
                    showAlertDialogOnErrorOccured(it)
                }
            )
        }

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
