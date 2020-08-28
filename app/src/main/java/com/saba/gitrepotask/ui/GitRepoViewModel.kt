package com.saba.gitrepotask.ui

import androidx.lifecycle.ViewModel
import com.saba.gitrepotask.data.GitRepoMVVMRepository
import java.util.*
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.saba.gitrepotask.data.model.Commits
import com.saba.gitrepotask.data.model.GitRepositories
import kotlinx.coroutines.launch
import retrofit2.Response

class GitRepoViewModel(private val gitRepoMVVMRepository: GitRepoMVVMRepository) : ViewModel(), Observable{

    fun getGitRepositories() : LiveData<Response<GitRepositories>>{
        lateinit var data : LiveData<Response<GitRepositories>>
        viewModelScope.launch {
            data =  liveData {
                val response : Response<GitRepositories> = gitRepoMVVMRepository.getGitRepositories()
                emit(response)
            }

        }
        return data
    }

    fun getLastCommit(url : String) : LiveData<Response<Commits>>{
        lateinit var data : LiveData<Response<Commits>>
        viewModelScope.launch {
            val newUrl = url.dropLast(6)
             data =  liveData {
                val response = gitRepoMVVMRepository.getLastCommit(newUrl)
                emit(response)
            }

        }
        return data
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}