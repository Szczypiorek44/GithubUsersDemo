package com.example.data.api

import com.example.data.api.models.GithubUser
import retrofit2.http.GET
import retrofit2.http.Query

private const val RESULTS_PER_PAGE = 20

interface GithubApi {
    @GET("users?per_page=$RESULTS_PER_PAGE")
    suspend fun getUsers(@Query("since") since: Int): List<GithubUser>

}
