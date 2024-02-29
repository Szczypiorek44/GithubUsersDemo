package com.example.data.api

import com.example.data.models.GithubUser
import retrofit2.http.GET

private const val RESULTS_PER_PAGE = 20

interface GithubApi {
    @GET("users?per_page=$RESULTS_PER_PAGE")
    suspend fun getUsers(): List<GithubUser>

}
