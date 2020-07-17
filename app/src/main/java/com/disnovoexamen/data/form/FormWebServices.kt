package com.disnovoexamen.data.form

import com.disnovoexamen.data.RestConstant
import com.disnovoexamen.data.form.request.FormRequest
import com.disnovoexamen.data.form.response.FormResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FormWebServices {

    @GET(RestConstant.ENDPOINT_FORM)
    fun formGet(@Header("token") token: String): Call<FormResponse>

    @POST(RestConstant.ENDPOINT_FORM)
    fun formPost(@Header("token") token: String,
                 @Body request: FormRequest): Call<FormResponse>
}
