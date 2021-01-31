package com.saba.gitrepositoriesdisplay.data.repository

import com.saba.gitrepositoriesdisplay.data.model.Commits
import com.saba.gitrepositoriesdisplay.data.model.GitRepositories
import com.saba.gitrepositoriesdisplay.data.retrofit.GitRepoService
import retrofit2.Response

class GitRepoMVVMRepositoryImpl(private val gitRepoService: GitRepoService) : GitRepoMVVMRepository{

   override suspend fun getGitRepositories(gitUrl : String) : Response<GitRepositories>{
        return gitRepoService.getGitRepositories(gitUrl)
    }

   override suspend fun getLastCommit(url : String) : Response<Commits>{
        return gitRepoService.getLastCommits(url)
    }
}