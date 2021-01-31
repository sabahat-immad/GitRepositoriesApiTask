package com.saba.gitrepositoriesdisplay.data.retrofit

import com.saba.gitrepositoriesdisplay.data.model.Commits
import com.saba.gitrepositoriesdisplay.data.model.GitRepositories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GitRepoService {

    @GET
    suspend fun getGitRepositories(@Url url: String) : Response<GitRepositories>

    @GET
    suspend fun getLastCommits(@Url url : String) : Response<Commits>
}