package com.saba.gitrepositoriesdisplay.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bumptech.glide.load.engine.Resource
import com.saba.gitrepositoriesdisplay.data.model.GitRepositories
import com.saba.gitrepositoriesdisplay.data.repository.GitRepoMVVMRepository
import com.saba.gitrepositoriesdisplay.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.Response.success
import kotlin.Result.Companion.success

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

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        val url = "/users/android/repos"
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<GitRepositories>())
                .`when`(gitRepoMVVMRepository)
                .getGitRepositories(url)
            val viewModel = GitRepoViewModel(gitRepoMVVMRepository)
            viewModel.getGitRepositories(url).observeForever(apiUsersObserver)
            verify(gitRepoMVVMRepository).getGitRepositories(url)
           // verify(apiUsersObserver).onChanged(emptyList())
            viewModel.getGitRepositories(url).removeObserver(apiUsersObserver)
        }
    }
}