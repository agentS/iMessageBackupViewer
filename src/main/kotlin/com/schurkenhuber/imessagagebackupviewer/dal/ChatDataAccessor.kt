package com.schurkenhuber.imessagagebackupviewer.dal

import com.schurkenhuber.imessagagebackupviewer.model.Chat

interface ChatDataAccessor {
    fun fetchChats(): List<Chat>
}