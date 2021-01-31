package com.saba.gitrepositoriesdisplay.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.saba.gitrepositoriesdisplay.data.model.Commit
import com.saba.gitrepositoriesdisplay.data.model.Commits
import com.saba.gitrepositoriesdisplay.data.model.GitRepositories
import com.saba.gitrepositoriesdisplay.data.model.GitRepositoriesItem
import com.saba.gitrepositoriesdisplay.data.repository.GitRepoMVVMRepository
import com.saba.gitrepositoriesdisplay.data.repository.GitRepoMVVMRepositoryImpl
import com.saba.gitrepositoriesdisplay.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GitRepoViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var gitRepoMVVMRepository: GitRepoMVVMRepository


    @Mock
    private lateinit var apiUsersObserver: Observer<Response<GitRepositories>>

    @Mock
    private lateinit var apiCommitsObserver: Observer<Response<Commits>>

    @Mock
    private lateinit var gitRepositoriesItem: GitRepositoriesItem



    @Test
    fun givenServerResponse200_whenFetchGitUsers_shouldReturnSuccess() {

         val url = "/users/android/repos"
         val user = "android"
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<GitRepositories>())
                .`when`(gitRepoMVVMRepository)
                .getGitRepositories(url)
            val viewModel = GitRepoViewModel(gitRepoMVVMRepository)
            viewModel.getGitRepositories(user).observeForever(apiUsersObserver)
            verify(gitRepoMVVMRepository).getGitRepositories(url)
           // verify(apiUsersObserver).onChanged(emptyList())
            viewModel.getGitRepositories(user).removeObserver(apiUsersObserver)
        }
    }



}