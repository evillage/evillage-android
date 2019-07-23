package nl.worth.clangnotifications.ui.subscription

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_subscription.*
import nl.worth.clangnotifications.R
import nl.worth.clangnotifications.data.interactor.NotificationInteractor
import nl.worth.clangnotifications.ui.ThankYouActivity
import nl.worth.clangnotifications.util.retrieveEmailFromSP

internal class SubscriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)

        val topicsAdapter = TopicsAdapter(getTopics())

        recycler_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = topicsAdapter

        topicsAdapter.onItemClick = { item ->
            NotificationInteractor().subscribeToTopic(item.topic,
                {
                    startActivityForResult(ThankYouActivity.getIntent(this, retrieveEmailFromSP()), 0)
                },
                {
                    showAlertDialogOnErrorOccured(it)
                }
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun getTopics(): List<TopicItem> {
        return mutableListOf(
            TopicItem(false, "Samsung"),
            TopicItem(false, "OnePlus"),
            TopicItem(false, "LG"),
            TopicItem(false, "Xiaomi"),
            TopicItem(false, "GooglePixel"),
            TopicItem(false, "Huawei")
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

}
