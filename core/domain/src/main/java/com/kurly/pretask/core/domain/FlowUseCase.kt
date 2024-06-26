package com.kurly.pretask.core.domain

import com.kurly.pretask.core.domain.common.asResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.kurly.pretask.core.domain.common.Result

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    operator fun invoke(parameters: P): Flow<Result<R>> =
        execute(parameters).asResult()
            .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<R>
}