package com.softocorp.instantchat.chat.model

import com.softocorp.instantchat.R

data class ChatModel @JvmOverloads constructor(
    @JvmField var senderData: ProfileData,
    @JvmField var receiverData: ProfileData,
    @JvmField var message: List<MessageData> = emptyList(),
    @JvmField var seen: Boolean = false,
    @JvmField var lastSentMessage: String = "This is just a test message which will be edited later on", /** This value will be sent by backend [Frontend shouldn't be calculating this using message[message.size - 1] **/
)

data class MessageData @JvmOverloads constructor(
    @JvmField var messages: String = "Same test message which will be edited later on",
    @JvmField var attachments: List<String> = emptyList(), // This is merely file keys of attachments uploaded on backend either on s3 or firebase
    @JvmField var sender: String = "abc1234", // This will be the user's id [user_id]
    @JvmField var receiver: String = "abc1432", // Receiver's user id [user_id]
    @JvmField var is_group_chat: Boolean = false
)

data class ProfileData @JvmOverloads constructor(
    var user_id: String,
    var user_full_name: String,
    @JvmField var profile_url: String? = "", // Use any storage provider url here to load images
    @JvmField var is_verified: Boolean = false
)
