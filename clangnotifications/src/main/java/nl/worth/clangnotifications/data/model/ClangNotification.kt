package nl.worth.clangnotifications.data.model

import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import nl.worth.clangnotifications.util.*
import org.json.JSONArray
import org.json.JSONException
import java.io.Serializable

/**
 * Data class for Clang notifications
 *
 * Unwraps data fields (a [Map] of [String]s) of a [RemoteMessage] coming from Clang
 *
 */
class ClangNotification(remoteMessage: RemoteMessage) : Serializable {
    var id: String? = null
        private set
    var title: String? = null
        private set
    var message: String? = null
        private set
    var actions: ArrayList<ClangAction> = arrayListOf()
        private set

    init {
        val data = remoteMessage.data
        title = data[tagNotificationTitle]
        message = data[tagNotificationMessage]
        id = data[tagNotificationId]

        try {
            val jsonArray = JSONArray(data[tagNotificationActions])
            for (i in 0 until jsonArray.length()) {
                val action = jsonArray.getJSONObject(i)
                actions.add(
                    ClangAction(
                        action.getString(tagActionsId), action.getString(tagActionsTitle)
                    )
                )
            }
        } catch (exception: JSONException) {
            Log.e(
                this::class.java.simpleName,
                "Failed to parse Clang actions from remote message",
                exception
            )
        }
    }

    /**
     * Data class for actions included within a Clang [RemoteMessage] data section (a [Map] of [String]s)
     * The tag for the data is "actions" and the structure is of a Json array as [{"id":"..","title":"..", ...}]
     */
    data class ClangAction(val id: String, val title: String) : Serializable

    /**
     * Static methods
     */
    companion object : Serializable {

        /**
         * Checks if received [RemoteMessage] is coming from Clang
         */
        fun isClangNotification(remoteMessage: RemoteMessage): Boolean {
            remoteMessage.data.let { data ->
                return when (data[tagNotificationType]) {
                    typeClangNotification -> {
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }
}

