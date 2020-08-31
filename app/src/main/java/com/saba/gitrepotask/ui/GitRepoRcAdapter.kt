package com.saba.gitrepotask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.saba.gitrepotask.R
import com.saba.gitrepotask.data.model.CommitsItem
import com.saba.gitrepotask.data.model.GitRepositoriesItem
import com.saba.gitrepotask.databinding.RepoItemBinding

class GitRepoRcAdapter(private val commitCallback: (GitRepositoriesItem, Int) -> Unit) : RecyclerView.Adapter<MyViewHolder>() {

    private val gitRepoList = ArrayList<GitRepositoriesItem>()


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
        holder.bind(gitRepoList[position], commitCallback, position)
    }

    fun setList(gitList: List<GitRepositoriesItem>?){
        gitRepoList.clear()
        gitRepoList.addAll(gitList!!)
    }

    fun updateList(gitRepositoriesItem: GitRepositoriesItem, position: Int){
        gitRepoList.removeAt(position)
        gitRepoList.add(position, gitRepositoriesItem)
    }

}

class MyViewHolder(val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(gitRepositoriesItem: GitRepositoriesItem, commitCallback: (GitRepositoriesItem, Int) -> Unit,
    position: Int){
        binding.nameTv.text = gitRepositoriesItem.name
        binding.starTv.text = "${gitRepositoriesItem.stargazers_count} stars"
        binding.descTv.text = gitRepositoriesItem.description
        binding.createdOnTv.text = "Created at ${gitRepositoriesItem.created_at}"


        if(gitRepositoriesItem.myCommitterName == null || gitRepositoriesItem.myCommitterName == ""){
            commitCallback(gitRepositoriesItem, position)
        }
        else{
            binding.committerTv.text = "Author: ${gitRepositoriesItem.myCommitterName}"
            binding.dateTv.text = "Committed on ${gitRepositoriesItem.myCommitDate}"
            binding.messageTv.text = "Commit message: ${gitRepositoriesItem.myCommitMessage}"

        }


    }
}