package com.saba.gitrepotask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saba.gitrepotask.R
import com.saba.gitrepotask.data.GitRepoMVVMRepository
import com.saba.gitrepotask.data.retrofit.GitRepoService
import com.saba.gitrepotask.data.retrofit.RetrofitInstance
import com.saba.gitrepotask.databinding.ActivityMainBinding

class GitRepoActivity : AppCompatActivity() {

    private lateinit var retService: GitRepoService
    private lateinit var gitRepoViewModel: GitRepoViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : GitRepoRcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        retService = RetrofitInstance.getRetrofitInstance().create(GitRepoService::class.java)
        val repository : GitRepoMVVMRepository = GitRepoMVVMRepository(retService)
        val factory = GitRepoViewModelFactory(repository)
        gitRepoViewModel = ViewModelProvider(this,factory).get(GitRepoViewModel::class.java)
        binding.gitRepoViewModel = gitRepoViewModel
        binding.lifecycleOwner = this

        initRecyclerview()

    }
    private fun initRecyclerview(){
        binding.gitRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter = GitRepoRcAdapter(){ url : String -> getLastCommit(url)}
        binding.gitRecyclerView.adapter = adapter
        getGitRepositories()
    }
    private fun getGitRepositories(){
        val responseLiveData = gitRepoViewModel.getGitRepositories()

        responseLiveData.observe(this, Observer {
            val gitList = it.body()?.toList()
            adapter.setList(gitList)
            adapter.notifyDataSetChanged()
        })
    }

    private fun getLastCommit(url : String){
        val responseLiveData = gitRepoViewModel.getLastCommit(url)

        responseLiveData.observe(this, Observer {
            val commits = it.body()?.toList()
            Log.i("LAST_COMMIT", commits.toString())
        })
    }
}