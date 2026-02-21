package io.github.pavelannin.veto.core.logger

import android.util.Log

public actual fun VetoLogger(): VetoLogger {
    return AndroidLogger()
}

private class AndroidLogger : VetoLogger {
    override fun info(message: String) {
        Log.i("Veto", message)
    }
}
