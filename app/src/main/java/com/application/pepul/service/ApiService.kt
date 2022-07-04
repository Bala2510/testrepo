package com.application.pepul.service

import com.application.pepul.ui.model.CommonResponse
import com.application.pepul.ui.model.GetFileResponse
import com.application.pepul.ui.model.InputParams
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST(ApiUrl.UPLOAD_FILE)
    suspend fun fileUpload(@Body inputParams: InputParams): CommonResponse

    @POST(ApiUrl.GET_FILE)
    suspend fun getFile(@Body inputParams: InputParams): GetFileResponse

    @POST(ApiUrl.DELETE_FILE)
    suspend fun deleteFile(@Body inputParams: InputParams): CommonResponse
}