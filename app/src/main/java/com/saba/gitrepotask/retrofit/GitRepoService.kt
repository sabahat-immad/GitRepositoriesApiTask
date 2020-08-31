package com.saba.gitrepotask.retrofit

import com.saba.gitrepotask.data.model.Commits
import com.saba.gitrepotask.data.model.GitRepositories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GitRepoService {

    @GET("/users/android/repos")
    suspend fun getGitRepositories() : Response<GitRepositories>

    @GET
    suspend fun getLastCommits(@Url url : String) : Response<Commits>
}