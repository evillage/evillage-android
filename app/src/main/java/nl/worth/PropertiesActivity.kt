package nl.worth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import nl.worth.clangnotifications.Clang
import nl.worth.thank_you.ThankYouActivity

class PropertiesActivity : AppCompatActivity() {

    lateinit var clang: Clang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val submit = findViewById<Button>(R.id.submit)

        clang = Clang.getInstance(this)

        submit.setOnClickListener {
            clang.updateProperties(mapOf("title" to "Login", "userEmail" to "test"),
                {
                    startActivityForResult(ThankYouActivity.getIntent(this), 0)
                    finish()
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

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, PropertiesActivity::class.java)
    }
}
