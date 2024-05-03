package com.aenadgrleey.kdb.di

import com.aenadgrleey.kdb.debugger.Debugger
import com.aenadgrleey.kdb.debugger.DebuggerImpl
import com.aenadgrleey.kdb.debugger.model.Config
import com.aenadgrleey.kdb.executor.Executor
import com.aenadgrleey.kdb.executor.ExecutorImpl
import com.aenadgrleey.kdb.executor.ProcessOwner
import com.aenadgrleey.kdb.executor.ProcessOwnerImpl
import com.aenadgrleey.kdb.parser.Parser
import com.aenadgrleey.kdb.setter.BreakpointSetter
import com.aenadgrleey.kdb.setter.BreakpointSetterImpl
import com.aenadgrleey.kdb.stepper.Stepper
import com.aenadgrleey.kdb.tracker.SessionTracker
import com.aenadgrleey.kdb.tracker.SessionTrackerImpl
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

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
    protected fun providesTracker(provided: SessionTrackerImpl): SessionTracker = provided

    @DebuggerScope
    @Provides
    protected fun providesProcessOwner(provided: ProcessOwnerImpl): ProcessOwner = provided

    @DebuggerScope
    @Provides
    protected fun providesExecutor(provided: ExecutorImpl): Executor = provided

    @DebuggerScope
    @Provides
    protected fun providesParser(): Parser = TODO("Not yet implemented")

    @DebuggerScope
    @Provides
    protected fun providesStepper(): Stepper = TODO("Not yet implemented")

    companion object
}
