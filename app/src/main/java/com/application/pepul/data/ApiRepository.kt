package com.application.pepul.data

import com.application.pepul.service.ApiService
import com.application.pepul.ui.model.CommonResponse
import com.application.pepul.ui.model.GetFileResponse
import com.application.pepul.ui.model.InputParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApiRepository @Inject constructor(val apiService: ApiService) {

    fun fileUpload(inputParams: InputParams): Flow<CommonResponse> {
        return flow {
            val response = apiService.fileUpload(inputParams)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun getFile(inputParams: InputParams): Flow<GetFileResponse> {
        return flow {
            val response = apiService.getFile(inputParams)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun deleteFile(inputParams: InputParams): Flow<CommonResponse> {
        return flow {
            val response = apiService.deleteFile(inputParams)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}