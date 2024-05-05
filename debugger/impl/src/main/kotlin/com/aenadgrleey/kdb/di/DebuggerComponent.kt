package com.aenadgrleey.kdb.di

import com.aenadgrleey.kdb.async.DebugEventParser
import com.aenadgrleey.kdb.async.DebugEventParserImpl
import com.aenadgrleey.kdb.async.parsers.BreakpointHitParser
import com.aenadgrleey.kdb.async.parsers.ExecutionBreakParser
import com.aenadgrleey.kdb.async.parsers.ExecutionFinishParser
import com.aenadgrleey.kdb.async.parsers.ExecutionStartParser
import com.aenadgrleey.kdb.async.parsers.WatchpointHitParser
import com.aenadgrleey.kdb.debugger.Debugger
import com.aenadgrleey.kdb.debugger.DebuggerImpl
import com.aenadgrleey.kdb.debugger.model.Config
import com.aenadgrleey.kdb.debugger.model.OSType
import com.aenadgrleey.kdb.debugger.model.Output
import com.aenadgrleey.kdb.executor.Executor
import com.aenadgrleey.kdb.executor.ExecutorImpl
import com.aenadgrleey.kdb.io.IOHandler
import com.aenadgrleey.kdb.io.IOHandlerImpl
import com.aenadgrleey.kdb.process.ProcessOwner
import com.aenadgrleey.kdb.setter.BreakpointSetter
import com.aenadgrleey.kdb.setter.BreakpointSetterImpl
import com.aenadgrleey.kdb.terminal.LinuxTerminal
import com.aenadgrleey.kdb.terminal.MacTerminal
import com.aenadgrleey.kdb.terminal.Terminal
import com.aenadgrleey.kdb.terminal.WindowsTerminal
import com.aenadgrleey.kdb.tracker.EmptySessionTracker
import com.aenadgrleey.kdb.tracker.FileSessionTracker
import com.aenadgrleey.kdb.tracker.MutableSessionTracker
import com.aenadgrleey.kdb.tracker.SessionTracker
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

@Suppress("TooManyFunctions")
@DebuggerScope
@Component
abstract class DebuggerComponent(
    private val config: Config,
) {
    @DebuggerScope
    abstract val debugger: Debugger

    @DebuggerScope
    @Provides
    protected fun providesConfig(): Config = config

    @DebuggerScope
    @Provides
    protected fun providesDebugger(debugger: DebuggerImpl): Debugger = debugger

    @DebuggerScope
    @Provides
    protected fun providesSetter(provided: BreakpointSetterImpl): BreakpointSetter = provided

    @DebuggerScope
    @Provides
    protected fun providesMutableTracker(): MutableSessionTracker =
        when (config.output) {
            Output.File -> FileSessionTracker(config)
            Output.Empty -> EmptySessionTracker()
        }

    @DebuggerScope
    @Provides
    protected fun providesTracker(provided: MutableSessionTracker): SessionTracker = provided

    @DebuggerScope
    @Provides
    protected fun providesProcessOwner(sessionTracker: MutableSessionTracker): ProcessOwner =
        ProcessOwner(config, sessionTracker)

    @DebuggerScope
    @Provides
    protected fun providesExecutor(provided: ExecutorImpl): Executor = provided

    @DebuggerScope
    @Provides
    protected fun providesTerminal(): Terminal {
        return when (config.osType) {
            OSType.Linux -> LinuxTerminal()
            OSType.Win -> WindowsTerminal()
            OSType.Mac -> MacTerminal()
        }
    }

    @DebuggerScope
    @Provides
    protected fun providesIoHandler(provided: IOHandlerImpl): IOHandler = provided

    abstract val parsers: Set<DebugEventParser>

    @IntoSet
    @Provides
    protected fun providesBreakpointHitParser(provided: BreakpointHitParser): DebugEventParser = provided

    @IntoSet
    @Provides
    protected fun providesExecutionBreakParser(provided: ExecutionBreakParser): DebugEventParser = provided

    @IntoSet
    @Provides
    protected fun providesExecutionFinishParser(provided: ExecutionFinishParser): DebugEventParser = provided

    @IntoSet
    @Provides
    protected fun providesExecutionStartParser(provided: ExecutionStartParser): DebugEventParser = provided

    @IntoSet
    @Provides
    protected fun providesWatchPointParser(provided: WatchpointHitParser): DebugEventParser = provided

    @DebuggerScope
    @Provides
    protected fun providesEventParser(): DebugEventParser = DebugEventParserImpl(parsers)

    companion object
}
