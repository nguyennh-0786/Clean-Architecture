package com.louis.data.repository.remote.api

import retrofit2.http.GET

interface ClearArchNoneAuthApi {
    @GET("/authentication/token/new")
    fun createRequestToken()
}