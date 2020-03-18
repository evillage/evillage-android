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
import androidx.recyclerview.widget.RecyclerView
import nl.worth.R
import nl.worth.clangnotifications.Clang
import nl.worth.thank_you.ThankYouActivity

class PollActivity : AppCompatActivity() {

    lateinit var clang: Clang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poll)

        val question = findViewById<TextView>(R.id.question)
        question.text = "What is you favourite car color?"

        clang = Clang.getInstance(
            applicationContext,
            "46b6dfb6-d5fe-47b1-b4a2-b92cbb30f0a5",
            "63f4bf70-2a0d-4eb2-b35a-531da0a61b20")

        val topicsAdapter = QuestionsAdapter(getAnswers())

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = topicsAdapter

        topicsAdapter.onItemClick = { item ->
            clang.logEvent("pollSubmit", mapOf("title" to "FavouriteCarColour", "value" to item.text),
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
        fun getIntent(context: Context): Intent =
            Intent(context, PollActivity::class.java)
    }
}
