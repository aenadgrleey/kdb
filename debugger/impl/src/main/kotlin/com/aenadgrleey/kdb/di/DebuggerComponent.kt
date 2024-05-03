package com.aenadgrleey.kdb.di

import com.aenadgrleey.kdb.Stepper
import com.aenadgrleey.kdb.debugger.Debugger
import com.aenadgrleey.kdb.debugger.DebuggerImpl
import com.aenadgrleey.kdb.debugger.model.Config
import com.aenadgrleey.kdb.executor.Executor
import com.aenadgrleey.kdb.parser.Parser
import com.aenadgrleey.kdb.setter.BreakpointSetter
import com.aenadgrleey.kdb.setter.BreakpointSetterImpl
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class DebuggerComponent(
    private val config: Config,
) {
    abstract val debugger: Debugger

    @Provides
    protected fun providesConfig(): Config = config

    @Provides
    protected fun providesDebugger(debugger: DebuggerImpl): Debugger = debugger

    @Provides
    protected fun providesSetter(): BreakpointSetter = BreakpointSetterImpl()

    @Provides
    protected fun providesExecutor(): Executor = TODO("Not yet implemented")

    @Provides
    protected fun providesParser(): Parser = TODO("Not yet implemented")

    @Provides
    protected fun providesStepper(): Stepper = TODO("Not yet implemented")

    companion object
}
