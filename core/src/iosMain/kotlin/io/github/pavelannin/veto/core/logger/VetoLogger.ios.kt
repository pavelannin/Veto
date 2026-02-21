package io.github.pavelannin.veto.core.logger

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter

public actual fun VetoLogger(): VetoLogger {
    return IOSLogger()
}

private class IOSLogger : VetoLogger {
    private val dateFormatter by lazy { NSDateFormatter().apply { dateFormat = "YYYY-MM-dd HH:mm:ss.SSS" } }

    override fun info(message: String) {
        log("INFO", message)
    }

    private fun log(tag: String, message: String) {
        val currentTime = dateFormatter.stringFromDate(NSDate())
        val str = "$currentTime [$tag] Veto - $message"
        println(str)
    }
}
