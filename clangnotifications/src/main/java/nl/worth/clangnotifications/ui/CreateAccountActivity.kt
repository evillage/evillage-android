package nl.worth.clangnotifications.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import nl.worth.clangnotifications.R
import nl.worth.clangnotifications.data.interactor.AccountInteractor

class CreateAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        val submit = findViewById<Button>(R.id.submit)
        val email = findViewById<EditText>(R.id.email)
        submit.setOnClickListener {
            AccountInteractor().registerAccount(email.text.toString(),
                {
                    startActivityForResult(ThankYouActivity.getIntent(this, email.text.toString()), 0)
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

    fun showAlertDialogOnErrorOccured(throwable: Throwable) {
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
