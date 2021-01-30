package com.saba.gitrepositoriesdisplay.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saba.gitrepositoriesdisplay.R
import com.saba.gitrepositoriesdisplay.data.model.GitRepositoriesItem
import com.saba.gitrepositoriesdisplay.databinding.RepoItemBinding

class GitRepoRcAdapter(private val commitCallback: (GitRepositoriesItem, Int) -> Unit) : RecyclerView.Adapter<MyViewHolder>() {

    private val gitRepoList = ArrayList<GitRepositoriesItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : RepoItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.repo_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return gitRepoList.size
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
            binding.committerTv.text = "Committer: ${gitRepositoriesItem.myCommitterName}"
            binding.dateTv.text = "Committed on ${gitRepositoriesItem.myCommitDate}"
            binding.messageTv.text = "Commit message: ${gitRepositoriesItem.myCommitMessage}"
            Glide.with(binding.root)
                .load(gitRepositoriesItem.myCommitterAvatarUrl)
                .into(binding.committerDp)


        }


    }
}