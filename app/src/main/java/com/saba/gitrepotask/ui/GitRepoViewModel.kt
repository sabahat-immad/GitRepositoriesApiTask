package com.saba.gitrepotask.ui

import androidx.lifecycle.ViewModel
import com.saba.gitrepotask.data.GitRepoMVVMRepository
import java.util.*
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.saba.gitrepotask.data.model.Commits
import com.saba.gitrepotask.data.model.GitRepositories
import retrofit2.Response

class GitRepoViewModel(private val gitRepoMVVMRepository: GitRepoMVVMRepository) : ViewModel(), Observable{

    fun getGitRepositories() : LiveData<Response<GitRepositories>>{
        return liveData {
            val response : Response<GitRepositories> = gitRepoMVVMRepository.getGitRepositories()
            emit(response)
        }
    }

    fun getLastCommit(url : String) : LiveData<Response<Commits>>{
        val newUrl = url.dropLast(6)
        return liveData {
            val response = gitRepoMVVMRepository.getLastCommit(newUrl)
            emit(response)
        }
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}