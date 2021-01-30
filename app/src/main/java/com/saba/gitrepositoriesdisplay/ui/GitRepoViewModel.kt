package com.saba.gitrepositoriesdisplay.ui

import androidx.lifecycle.ViewModel
import com.saba.gitrepositoriesdisplay.data.repository.GitRepoMVVMRepository
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.saba.gitrepositoriesdisplay.data.model.Commits
import com.saba.gitrepositoriesdisplay.data.model.GitRepositories
import com.saba.gitrepositoriesdisplay.data.model.GitRepositoriesItem
import kotlinx.coroutines.launch
import retrofit2.Response

class GitRepoViewModel(private val gitRepoMVVMRepository: GitRepoMVVMRepository) : ViewModel(), Observable{

    fun getGitRepositories(gitUser : String) : LiveData<Response<GitRepositories>>{
        lateinit var data : LiveData<Response<GitRepositories>>
        val gitRepoUrl = "/users/$gitUser/repos"
        viewModelScope.launch {
            data =  liveData {
                val response : Response<GitRepositories> = gitRepoMVVMRepository.getGitRepositories(gitRepoUrl)
                emit(response)
            }

        }
        return data
    }

    fun getLastCommit(gitRepositoriesItem: GitRepositoriesItem) : LiveData<Response<Commits>>{

        lateinit var data : LiveData<Response<Commits>>
        viewModelScope.launch {
            val newUrl = gitRepositoriesItem.commits_url.dropLast(6)
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