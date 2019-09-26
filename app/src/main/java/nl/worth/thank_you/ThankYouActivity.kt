package nl.worth.thank_you

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import nl.worth.R

internal class ThankYouActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)
        setResult(Activity.RESULT_OK)
        val thankYouText = findViewById<TextView>(R.id.thankyou)
        thankYouText.text = getString(R.string.thank_you)
        findViewById<Button>(R.id.done).setOnClickListener {
            finish()
        }
    }

    companion object {
        internal val EMAIL_EXTRA = "email"

        fun getIntent(context: Context, email: String): Intent =
            Intent(context, ThankYouActivity::class.java).apply {
                putExtra(EMAIL_EXTRA, email)
            }
    }
}
