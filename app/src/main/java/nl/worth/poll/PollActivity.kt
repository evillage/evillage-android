package nl.worth.poll

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_product_overview.*
import nl.worth.R
import nl.worth.clangnotifications.ClangNotifications
import nl.worth.clangnotifications.data.model.PollData
import nl.worth.thank_you.ThankYouActivity

class PollActivity : AppCompatActivity() {

    lateinit var clangNotifications: ClangNotifications

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poll)

        val question = findViewById<TextView>(R.id.question)
        question.text = "What is you favourite car color?"

        clangNotifications = ClangNotifications.getInstance(this)
        val topicsAdapter = QuestionsAdapter(getAnswers())


        recycler_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = topicsAdapter

        topicsAdapter.onItemClick = { item ->

            clangNotifications.logEvent(this, "pollSubmit", mutableListOf(PollData("POLL_TITLE", item.text)),
                {
                    startActivityForResult(ThankYouActivity.getIntent(this, EMAIL_EXTRA), 0)
                },
                {
                    showAlertDialogOnErrorOccured(it)
                }
            )
        }
    }

    private fun getAnswers(): List<QuestionItem> {
        return mutableListOf(
            QuestionItem(false, "Red"),
            QuestionItem(false, "Grey"),
            QuestionItem(false, "Black"),
            QuestionItem(false, "Blue")
        )
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
        internal val EMAIL_EXTRA = "email"

        fun getIntent(context: Context, email: String): Intent =
            Intent(context, PollActivity::class.java).apply {
                putExtra(EMAIL_EXTRA, email)
            }
    }
}
