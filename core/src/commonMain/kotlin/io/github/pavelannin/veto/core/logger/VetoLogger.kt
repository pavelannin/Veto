package io.github.pavelannin.veto.core.logger

public interface VetoLogger {
    public fun info(message: String)
}

public expect fun VetoLogger(): VetoLogger
