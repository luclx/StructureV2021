package com.luclx.structure2021.data

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@KoinApiExtension
abstract class NetworkResourceManager<Entity : Any, ViewModel>(
    private val shouldShowLoading: Boolean = true
) : KoinComponent {

    @WorkerThread
    abstract suspend fun doFetchFromNetwork(): Entity

    @WorkerThread
    abstract suspend fun processResponse(response: Entity): ViewModel

    /**
     * [execute] Requesting to Api server
     *
     * - 1: In the case of checking for HttpConnection error, MoshiParser
     * - 2: In the case of checking for ApiConnection error. Preparing data with error handler
     * - 3: In the case of checking for ApiConnection success. Preparing data with success handler
     */
    fun execute(): Flow<Resource<ViewModel>> = flow {
        if (shouldShowLoading) emit(Resource.Loading)
        val networkResponse = doFetchFromNetwork()
        // some apis need to check code or flag
        // emit(Resource.Error(BaseError(""), processResponse(networkResponse)))
        // default is success
        emit(ResponseHandler.handleSuccess(processResponse(networkResponse)))
    }.catch { error ->
        Log.e(NetworkResourceManager::class.java.name, "$error")
        emit(ResponseHandler.handleError(error))
    }.flowOn(Dispatchers.IO)
}