package nl.worth.clangnotifications.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import nl.worth.clangnotifications.R
import nl.worth.clangnotifications.data.interactor.AccountInteractor
import nl.worth.clangnotifications.util.isEmailValid

class CreateAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        val submit = findViewById<Button>(R.id.submit)
        val email = findViewById<EditText>(R.id.email)
        submit.setOnClickListener {
            if(email.text.toString().isEmailValid()) saveEmailToSHaredPreferences(email.text.toString())
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

    private fun saveEmailToSHaredPreferences(email: String) {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(getString(R.string.saved_email_key), email)
            apply()
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
