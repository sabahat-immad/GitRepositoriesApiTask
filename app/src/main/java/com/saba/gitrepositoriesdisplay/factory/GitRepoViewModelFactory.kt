package com.saba.gitrepositoriesdisplay.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saba.gitrepositoriesdisplay.data.repository.GitRepoMVVMRepository
import com.saba.gitrepositoriesdisplay.ui.GitRepoViewModel

/**if a viewmodel class has a constructor value then we need to also create viewmodel factory class. you implement
 * its method and write a fixed boilerplate code copied from somewhere.
 * it should have the same constructor as its view model.
 */
class GitRepoViewModelFactory(private val gitRepoMVVMRepository: GitRepoMVVMRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GitRepoViewModel::class.java)){
            return GitRepoViewModel(gitRepoMVVMRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}