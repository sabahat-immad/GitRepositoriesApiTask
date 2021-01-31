package com.saba.gitrepositoriesdisplay.data.repository

import com.saba.gitrepositoriesdisplay.data.model.Commits
import com.saba.gitrepositoriesdisplay.data.model.GitRepositories
import retrofit2.Response


interface GitRepoMVVMRepository {
    suspend fun getGitRepositories(gitUrl: String): Response<GitRepositories>

    suspend fun getLastCommit(url: String): Response<Commits>
}
