package com.schurkenhuber.imessagagebackupviewer.dal.ui.html

import com.schurkenhuber.imessagagebackupviewer.dal.ui.ChatRenderer
import com.schurkenhuber.imessagagebackupviewer.model.Chat
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.io.File
import java.nio.file.Paths

class HtmlChatRenderer : ChatRenderer {
    private val templateEngine: TemplateEngine

    companion object {
        private const val OUTPUT_PATH = "result"
        private const val SINGLE_CHAT_OUTPUT_PATH = "chats"
    }

    init {
        this.templateEngine = TemplateEngine()
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.prefix = "/templates/"
        templateResolver.suffix = ".html"
        templateResolver.characterEncoding = "UTF-8"
        templateResolver.templateMode = TemplateMode.HTML
        templateEngine.setTemplateResolver(templateResolver)
    }

    override fun renderChatOverview(chats: List<Chat>) {
        val context = Context()
        context.setVariable("chats", chats)
        val chatNameList = chats
            .map { chat -> chat.participants }
            .map { chatParticipants -> chatParticipants.entries }
            .map { chatParticipants -> chatParticipants.joinToString(separator = ", ") { entry -> "${entry.value} (${entry.key})" } }
        val chatNames = chats
            .map { chat -> chat.chatId }
            .zip(chatNameList)
            .map { chatIdWithChatName -> chatIdWithChatName.first to chatIdWithChatName.second }
            .toMap()
        context.setVariable("chatNames", chatNames)

        this.createOutputDirectories()
        Paths.get(OUTPUT_PATH, "index.html").toFile().writeText(
            this.templateEngine.process("chats.html", context)
        )
    }

    private fun createOutputDirectories() {
        val outputDirectory = Paths.get(OUTPUT_PATH).toFile()
        if (!outputDirectory.exists()) {
            outputDirectory.mkdir()
        }
        val singleChatsOutputDirectory = Paths.get(OUTPUT_PATH, SINGLE_CHAT_OUTPUT_PATH).toFile()
        if (!singleChatsOutputDirectory.exists()) {
            singleChatsOutputDirectory.mkdir()
        }
    }

    override fun renderChat(chat: Chat) {
        val context = Context()
        context.setVariable("chat", chat)

        val chatName = chat.participants
            .map { chatParticipants -> "${chatParticipants.value} (${chatParticipants.key})" }
            .joinToString(separator = ", ")
        context.setVariable("chatName", chatName)

        Paths.get(OUTPUT_PATH, SINGLE_CHAT_OUTPUT_PATH, "${chat.chatId}.html").toFile().writeText(
            this.templateEngine.process("chat.html", context)
        )
    }
}
