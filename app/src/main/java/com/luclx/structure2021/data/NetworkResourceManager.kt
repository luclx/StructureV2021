package com.luclx.structure2021.data

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
abstract class NetworkResourceManager<NetworkModel : Any, ViewModel>(
    private val shouldShowLoading: Boolean = true
) : KoinComponent {

    private val context: Context by inject()

    @WorkerThread
    abstract suspend fun doFetchFromNetwork(): NetworkModel

    @WorkerThread
    abstract suspend fun processResponse(response: NetworkModel): ViewModel

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