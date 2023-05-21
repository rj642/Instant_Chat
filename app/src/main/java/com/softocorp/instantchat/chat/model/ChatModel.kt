package com.softocorp.instantchat.chat.model

import android.os.Parcelable
import com.softocorp.instantchat.R
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class ChatModel @JvmOverloads constructor(
    var id: String = UUID.randomUUID().toString(),
    var senderData: ProfileData,
    var receiverData: ProfileData,
    var message: List<MessageData> = emptyList(),
    var seen: Boolean = false,
    var lastSentMessage: String = "This is just a test message which will be edited later on", /** This value will be sent by backend [Frontend shouldn't be calculating this using message[message.size - 1] **/
): Parcelable {

}

@Parcelize
data class MessageData @JvmOverloads constructor(
    var messages: String = "Same test message which will be edited later on",
    var attachments: List<String> = emptyList(), // This is merely file keys of attachments uploaded on backend either on s3 or firebase
    var sender: String = "abc1234", // This will be the user's id [user_id]
    var receiver: String = "abc1432", // Receiver's user id [user_id]
    var is_group_chat: Boolean = false
) : Parcelable {

}

@Parcelize
data class ProfileData @JvmOverloads constructor(
    var user_id: String,
    var user_full_name: String,
    var profile_url: String? = "", // Use any storage provider url here to load images
    var is_verified: Boolean = false
) : Parcelable {

}
