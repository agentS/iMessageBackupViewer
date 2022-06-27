import com.schurkenhuber.imessagagebackupviewer.dal.ChatDataAccessor
import com.schurkenhuber.imessagagebackupviewer.dal.filesystem.FileSystemChatDataAccessor
import com.schurkenhuber.imessagagebackupviewer.dal.ui.ChatRenderer
import com.schurkenhuber.imessagagebackupviewer.dal.ui.html.HtmlChatRenderer
import org.thymeleaf.ITemplateEngine
import java.io.File

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

private const val CHAT_DIRECTORY_NAME = "chats"

fun main(arguments: Array<String>) {
    if (arguments.size != 1) {
        System.err.println("Please supply the path to the extracted ZIP archive as command line argument.")
    }

    val chatDataAccessor: ChatDataAccessor = FileSystemChatDataAccessor(
        File(arguments[0], CHAT_DIRECTORY_NAME).path
    )
    val chats = chatDataAccessor.fetchChats()
    println("chat count: ${chats.size}")

    val chatRenderer: ChatRenderer = HtmlChatRenderer()

    chatRenderer.renderChatOverview(chats)
    for (chat in chats) {
        chatRenderer.renderChat(chat)
    }
}
