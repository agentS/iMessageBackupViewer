package com.schurkenhuber.imessagagebackupviewer.dal.ui

import com.schurkenhuber.imessagagebackupviewer.model.Chat

interface ChatRenderer {
    fun renderChatOverview(chats: List<Chat>)
    fun renderChat(chat: Chat)
}