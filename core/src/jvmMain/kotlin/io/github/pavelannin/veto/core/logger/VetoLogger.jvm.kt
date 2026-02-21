package io.github.pavelannin.veto.core.logger

import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger
import java.util.logging.SimpleFormatter

public actual fun VetoLogger(): VetoLogger {
    return JvmLogger()
}

private class JvmLogger : VetoLogger {
    private val logger by lazy {
        val consoleHandler: ConsoleHandler = ConsoleHandler().apply {
            level = Level.ALL
            formatter = SimpleFormatter()
        }
        Logger.getLogger(JvmLogger::class.java.name).apply {
            level = Level.ALL
            addHandler(consoleHandler)
            useParentHandlers = false
        }
    }

    override fun info(message: String) {
        logger.info("[INFO] Veto - $message")
    }
}
