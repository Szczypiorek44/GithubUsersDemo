package com.example.data.mocks

import com.example.data.api.GithubApi
import com.example.data.api.models.GithubUser
import retrofit2.Response

class FakeGithubApi(var onDownloadReturn: () -> Response<List<GithubUser>>) : GithubApi {

    override suspend fun downloadUsers(since: Int): Response<List<GithubUser>> {
        return onDownloadReturn()
    }
}