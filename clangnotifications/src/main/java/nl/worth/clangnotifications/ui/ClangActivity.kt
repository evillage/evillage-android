package nl.worth.clangnotifications.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import nl.worth.clangnotifications.R
import nl.worth.clangnotifications.data.model.KeyValue
import nl.worth.clangnotifications.firebase.ClangIntentService

internal class ClangActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clang)
        val title = findViewById<TextView>(R.id.title)
        val description = findViewById<TextView>(R.id.description)
        val action1Btn = findViewById<Button>(R.id.action1)
        val action2Btn = findViewById<Button>(R.id.action2)
        val action3Btn = findViewById<Button>(R.id.action3)

        val keyValue: ArrayList<KeyValue> = intent.getParcelableArrayListExtra("keyValue")

        title.text = keyValue.find { it.key == "notificationTitle" }?.value
        description.text = keyValue.find { it.key == "notificationBody" }?.value
        action1Btn.text = keyValue.find { it.key == "action1Title" }?.value
        action2Btn.text = keyValue.find { it.key == "action2Title" }?.value
        action3Btn.text = keyValue.find { it.key == "action3Title" }?.value

        val productId = keyValue.find { it.key == "notificationId" }?.value
        productId?.let {
            action1Btn.apply {
                setOnClickListener {
                    onActionClick(keyValue, productId, "action1Id")
                }
            }
            action2Btn.apply {
                setOnClickListener {
                    onActionClick(keyValue, productId, "action2Id")
                }
            }
            action3Btn.apply {
                setOnClickListener {
                    onActionClick(keyValue, productId, "action3Id")
                }
            }
        }
    }

    private fun onActionClick(
        keyValue: ArrayList<KeyValue>,
        productId: String,
        actionKey: String
    ) {
        keyValue.find { it.key == actionKey }?.value?.let {
            sendAction(it, productId)
        }
        finish()
    }

    private fun sendAction(actionId: String, productId: String) {
        startService(Intent(this, ClangIntentService::class.java).apply {
            this.putExtra("productId", productId)
            this.putExtra("actionId", actionId)
        })
    }
}
