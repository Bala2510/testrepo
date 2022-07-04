package com.application.pepul.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.application.pepul.data.ApiRepository
import com.application.pepul.service.Resource
import com.application.pepul.ui.model.CommonResponse
import com.application.pepul.ui.model.GetFileResponse
import com.application.pepul.ui.model.InputParams
import com.application.pepul.util.UtilsDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    application: Application,
    private val repository: ApiRepository
) : AndroidViewModel(application) {

    fun fileUpload(inputParams: InputParams) = liveData<Resource<CommonResponse>> {
        if (UtilsDefault.isOnline()) {
            repository.fileUpload(inputParams)
                .onStart {
                    emit(Resource.loading(data = null))
                }
                .catch {
                    emit(Resource.error(data = null, msg = "Cannot reach server..try again"))
                }
                .collect {
                    emit(Resource.success(it))
                }
        } else {
            emit(Resource.error(data = null, msg = "No internet connection"))
        }

    }

    fun getFile(inputParams: InputParams) = liveData<Resource<GetFileResponse>> {
        if (UtilsDefault.isOnline()) {
            repository.getFile(inputParams)
                .onStart {
                    emit(Resource.loading(data = null))
                }
                .catch {
                    emit(Resource.error(data = null, msg = "Cannot reach server..try again"))
                }
                .collect {
                    emit(Resource.success(it))
                }
        } else {
            emit(Resource.error(data = null, msg = "No internet connection"))
        }

    }

    fun deleteFile(inputParams: InputParams) = liveData<Resource<CommonResponse>> {
        if (UtilsDefault.isOnline()) {
            repository.deleteFile(inputParams)
                .onStart {
                    emit(Resource.loading(data = null))
                }
                .catch {
                    emit(Resource.error(data = null, msg = "Cannot reach server..try again"))
                }
                .collect {
                    emit(Resource.success(it))
                }
        } else {
            emit(Resource.error(data = null, msg = "No internet connection"))
        }

    }

}