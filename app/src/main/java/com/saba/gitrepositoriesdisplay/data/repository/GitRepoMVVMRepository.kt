package com.saba.gitrepositoriesdisplay.data.repository

import com.saba.gitrepositoriesdisplay.data.model.Commits
import com.saba.gitrepositoriesdisplay.data.model.GitRepositories
import com.saba.gitrepositoriesdisplay.data.retrofit.GitRepoService
import retrofit2.Response

class GitRepoMVVMRepository(private val gitRepoService: GitRepoService) {

    suspend fun getGitRepositories(gitUrl : String) : Response<GitRepositories>{
        return gitRepoService.getGitRepositories(gitUrl)
    }

    suspend fun getLastCommit(url : String) : Response<Commits>{
        return gitRepoService.getLastCommits(url)
    }
}