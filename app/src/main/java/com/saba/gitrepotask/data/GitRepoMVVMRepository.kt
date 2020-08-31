package com.saba.gitrepotask.data

import com.saba.gitrepotask.data.model.Commits
import com.saba.gitrepotask.data.model.GitRepositories
import com.saba.gitrepotask.retrofit.GitRepoService
import retrofit2.Response

class GitRepoMVVMRepository(private val gitRepoService: GitRepoService) {

    suspend fun getGitRepositories() : Response<GitRepositories>{
        return gitRepoService.getGitRepositories()
    }

    suspend fun getLastCommit(url : String) : Response<Commits>{
        return gitRepoService.getLastCommits(url)
    }
}