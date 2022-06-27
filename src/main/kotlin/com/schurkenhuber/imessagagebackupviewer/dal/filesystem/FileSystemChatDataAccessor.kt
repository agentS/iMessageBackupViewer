package com.schurkenhuber.imessagagebackupviewer.dal.filesystem

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.schurkenhuber.imessagagebackupviewer.dal.ChatDataAccessor
import com.schurkenhuber.imessagagebackupviewer.model.Chat
import java.io.File
import java.io.FilenameFilter

class FileSystemChatDataAccessor(private val chatFilesPath: String) : ChatDataAccessor {
    override fun fetchChats(): List<Chat> {
        val chatFileDirectory = File(this.chatFilesPath)
        val chatFileNames = chatFileDirectory.listFiles(object : FilenameFilter {
            override fun accept(directory: File?, fileName: String?): Boolean {
                return fileName?.endsWith("json") ?: false
            }
        })

        val jsonMapper = jacksonObjectMapper()
        return chatFileNames.map { chatFile -> jsonMapper.readValue(chatFile, Chat::class.java) }
    }
}