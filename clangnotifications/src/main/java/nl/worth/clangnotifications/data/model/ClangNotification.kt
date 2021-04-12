package nl.worth.clangnotifications.data.model

import android.util.Log
import androidx.annotation.Keep
import com.google.firebase.messaging.RemoteMessage
import nl.worth.clangnotifications.util.*
import org.json.JSONArray
import org.json.JSONException
import java.io.Serializable
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

/**
 * Data class for Clang notifications
 *
 * Unwraps data fields (a [Map] of [String]s) of a [RemoteMessage] coming from Clang
 *
 */
@Keep
class ClangNotification(remoteMessage: RemoteMessage) : Serializable {
    var id: String? = null
        private set
    var category: String? = null
        private set
    var type: String? = null
        private set
    var title: String? = null
        private set
    var message: String? = null
        private set
    var actions: ArrayList<ClangAction> = arrayListOf()
        private set
    var customFields: ArrayList<ClangCustomField> = arrayListOf()
        private set

    init {
        val data = remoteMessage.data
        title = data[tagNotificationTitle]
        message = data[tagNotificationMessage]
        id = data[tagNotificationId]
        type = data[tagType]
        category = data[tagCategory]

        if (remoteMessage.data.containsKey(tagNotificationActions)) {
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
            } catch (exception: Exception) {
                Log.e(this::class.java.simpleName, "Failed to parse Clang actions from remote message", exception)
            }
        }

        if (remoteMessage.data.containsKey(tagNotificationCustomFields)) {
            try {
                val jsonArray = JSONArray(data[tagNotificationCustomFields])
                for (i in 0 until jsonArray.length()) {
                    val customField = jsonArray.getJSONObject(i)
                    customFields.add(
                        ClangCustomField(
                            customField.getString(tagCustomFieldId), customField.getString(tagCustomFieldTitle)
                        )
                    )
                }
            } catch (exception: Exception) {
                Log.e(this::class.java.simpleName, "Failed to parse Clang customFields from remote message", exception)
            }
        }

        if (remoteMessage.data.containsKey(tagNotificationActionOneId) && remoteMessage.data.containsKey(tagNotificationActionOneTitle)) {
            remoteMessage.data[tagNotificationActionOneId]?.let { id ->
                remoteMessage.data[tagNotificationActionOneTitle]?.let { title ->
                    actions.add(ClangAction(id, title))
                }
            }
        }

        if (remoteMessage.data.containsKey(tagNotificationActionTwoId) && remoteMessage.data.containsKey(tagNotificationActionTwoTitle)) {
            remoteMessage.data[tagNotificationActionTwoId]?.let { id ->
                remoteMessage.data[tagNotificationActionTwoTitle]?.let { title ->
                    actions.add(ClangAction(id, title))
                }
            }
        }

        if (remoteMessage.data.containsKey(tagNotificationActionThreeId) && remoteMessage.data.containsKey(tagNotificationActionThreeTitle)) {
            remoteMessage.data[tagNotificationActionThreeId]?.let { id ->
                remoteMessage.data[tagNotificationActionThreeTitle]?.let { title ->
                    actions.add(ClangAction(id, title))
                }
            }
        }
    }

    /**
     * Data class for actions included within a Clang [RemoteMessage] data section (a [Map] of [String]s)
     * The tag for the data is "actions" and the structure is of a Json array as [{"id":"..","title":"..", ...}]
     */
    @Keep
    data class ClangAction(val id: String, val title: String) : Serializable

    /**
     * Data class for custom fields included within a Clang [RemoteMessage] data section (a [Map] of [String]s)
     * The tag for the data is "actions" and the structure is of a Json array as [{"id":"..","title":"..", ...}]
     */
    @Keep
    data class ClangCustomField(val id: String, val title: String) : Serializable

    /**
     * Static methods
     */
    @Keep
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

