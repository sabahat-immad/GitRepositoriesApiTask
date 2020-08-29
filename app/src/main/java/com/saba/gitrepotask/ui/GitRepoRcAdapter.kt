package com.saba.gitrepotask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.saba.gitrepotask.R
import com.saba.gitrepotask.data.model.CommitsItem
import com.saba.gitrepotask.data.model.GitRepositoriesItem
import com.saba.gitrepotask.databinding.RepoItemBinding

class GitRepoRcAdapter(private val commitCallback: (String) -> Unit) : RecyclerView.Adapter<MyViewHolder>() {

    private val gitRepoList = ArrayList<GitRepositoriesItem>()
    private var lastCommit : CommitsItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : RepoItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.repo_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return gitRepoList.size
        //return 4
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(gitRepoList[position], commitCallback, lastCommit)
    }

    fun setList(gitList: List<GitRepositoriesItem>?){
        gitRepoList.clear()
        gitRepoList.addAll(gitList!!)
    }

    fun setLastCommit(_lastCommit : CommitsItem){
        this.lastCommit = _lastCommit
    }
}

class MyViewHolder(val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(gitRepositoriesItem: GitRepositoriesItem, commitCallback: (String) -> Unit, lastCommit: CommitsItem?){
        binding.nameTv.text = gitRepositoriesItem.name
        binding.starTv.text = "${gitRepositoriesItem.stargazers_count} stars"
        binding.descTv.text = gitRepositoriesItem.description
        binding.createdOnTv.text = "Created at ${gitRepositoriesItem.created_at}"
        commitCallback(gitRepositoriesItem.commits_url)

        if(lastCommit != null){
            binding.authorTv.text = lastCommit.commit.author.name
        }
    }
}