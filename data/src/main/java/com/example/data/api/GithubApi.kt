package com.example.data.api

import android.util.Log
import com.example.data.api.models.DownloadUsersResult
import com.example.data.api.models.GithubUser
import com.example.data.utils.asDownloadUsersResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

private const val TAG = "GithubApi"

private const val RESULTS_PER_PAGE = 20
const val STARTING_USER_ID = 0


interface GithubApi {
    @GET("users?per_page=$RESULTS_PER_PAGE")
    suspend fun downloadUsers(@Query("since") since: Int): Response<List<GithubUser>>

}

suspend fun GithubApi.downloadUsersAsResult(sinceId: Int): DownloadUsersResult? {
    try {
        val response = downloadUsers(sinceId)

        if (response.isSuccessful) {
            return response.asDownloadUsersResult()
        } else {
            val responseError = response.errorBody()?.charStream()?.readText()
            Log.d(TAG, "Failed to download users. Error: $responseError")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}

fun Response<List<GithubUser>>.containsLinkNextHeader(): Boolean {
    return headers()["link"]?.contains("next") ?: false
}