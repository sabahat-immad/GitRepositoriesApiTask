package com.saba.gitrepotask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saba.gitrepotask.R
import com.saba.gitrepotask.data.GitRepoMVVMRepository
import com.saba.gitrepotask.data.model.GitRepositoriesItem
import com.saba.gitrepotask.retrofit.GitRepoService
import com.saba.gitrepotask.retrofit.RetrofitInstance
import com.saba.gitrepotask.databinding.ActivityMainBinding
import com.saba.gitrepotask.utils.NetworkUtils

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

        adapter = GitRepoRcAdapter(){ gitSelected : GitRepositoriesItem, pos : Int -> getLastCommit(
            gitSelected, pos)}
        binding.gitRecyclerView.adapter = adapter

        if(NetworkUtils.hasNetwork(this)!!){
            getGitRepositories()
        }
        else{
            binding.loadingSpinner.visibility = View.GONE
            Toast.makeText(this, "Oops, looks like you are not connected to " +
                    "the internet.",Toast.LENGTH_SHORT).show()
        }

    }
    private fun getGitRepositories(){
        val responseLiveData = gitRepoViewModel.getGitRepositories()

        responseLiveData.observe(this, Observer {
            binding.loadingSpinner.visibility = View.GONE

            if(it.body() != null){
                val gitList = it.body()?.toList()
                adapter.setList(gitList)
                adapter.notifyDataSetChanged()

            }
            else{
                Toast.makeText(this,"Network error: ${it.message()}",Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun getLastCommit(gitRepositpryItemSelected: GitRepositoriesItem, position : Int){
        if(NetworkUtils.hasNetwork(this)!!){
            val responseLiveData = gitRepoViewModel.getLastCommit(
                gitRepositpryItemSelected)

            responseLiveData.observe(this, Observer {
                if(it.body() != null){
                    val lastCommit = it.body()?.listIterator()?.next()
                    gitRepositpryItemSelected.myCommitterName =
                        lastCommit?.commit?.committer?.name.toString()
                    gitRepositpryItemSelected.myCommitDate =
                        lastCommit?.commit?.committer?.date.toString()
                    gitRepositpryItemSelected.myCommitMessage =
                        lastCommit?.commit?.message.toString()

                    adapter.updateList(gitRepositpryItemSelected,position)
                    adapter.notifyItemChanged(position)

                }
                else{
                    Toast.makeText(this,"Network error: ${it.message()}",Toast.LENGTH_LONG).show()
                }
            })
        }
        else{

            Toast.makeText(this, "Oops, looks like you are not connected to " +
                    "the internet.",Toast.LENGTH_SHORT).show()
        }
    }


}