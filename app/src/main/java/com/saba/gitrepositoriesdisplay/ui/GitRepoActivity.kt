package com.saba.gitrepositoriesdisplay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saba.gitrepositoriesdisplay.R
import com.saba.gitrepositoriesdisplay.data.repository.GitRepoMVVMRepository
import com.saba.gitrepositoriesdisplay.data.model.GitRepositoriesItem
import com.saba.gitrepositoriesdisplay.retrofit.GitRepoService
import com.saba.gitrepositoriesdisplay.retrofit.RetrofitInstance
import com.saba.gitrepositoriesdisplay.databinding.ActivityMainBinding
import com.saba.gitrepositoriesdisplay.factory.GitRepoViewModelFactory
import com.saba.gitrepositoriesdisplay.utils.EspressoIdlingResource
import com.saba.gitrepositoriesdisplay.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*

class GitRepoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var retService: GitRepoService
    private lateinit var gitRepoViewModel: GitRepoViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : GitRepoRcAdapter
    private lateinit var gitUsers: Array<String>



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        retService = RetrofitInstance.getRetrofitInstance().create(GitRepoService::class.java)
        val repository : GitRepoMVVMRepository = GitRepoMVVMRepository(retService)
        val factory = GitRepoViewModelFactory(repository)
        gitRepoViewModel = ViewModelProvider(this,factory).get(GitRepoViewModel::class.java)
        binding.gitRepoViewModel = gitRepoViewModel
        binding.lifecycleOwner = this

        initUserDropDown()
        initRecyclerview()
        getGitRepositories(gitUsers[0])


    }

    private fun initUserDropDown() {
         gitUsers = resources.getStringArray(R.array.Users)
        var dropDownAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, gitUsers)
        dropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(user_spinner){

                adapter = dropDownAdapter
                setSelection(0, true)
                onItemSelectedListener = this@GitRepoActivity
                prompt = context.getString(R.string.user_dropdown_promt)
                gravity = Gravity.CENTER

        }
    }


    private fun initRecyclerview(){
        binding.gitRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter = GitRepoRcAdapter(){ gitSelected : GitRepositoriesItem, pos : Int -> getLastCommit(
            gitSelected, pos)}
        binding.gitRecyclerView.adapter = adapter

        /*if(NetworkUtils.hasNetwork(this)!!){
            getGitRepositories(gitUsers[0])
        }
        else{
            binding.loadingSpinner.visibility = View.GONE
            showToast("Oops, looks like you are not connected to " +
                    "the internet.",Toast.LENGTH_SHORT)
        }*/

    }
    private fun getGitRepositories(gitUser : String){
        if(NetworkUtils.hasNetwork(this)!!) {
            EspressoIdlingResource.increment()
            val responseLiveData = gitRepoViewModel.getGitRepositories(gitUser)

            responseLiveData.observe(this, Observer {
                binding.loadingSpinner.visibility = View.GONE
                EspressoIdlingResource.decrement()
                if (it.body() != null) {
                    val gitList = it.body()?.toList()
                    adapter.setList(gitList)
                    adapter.notifyDataSetChanged()

                } else {
                    Toast.makeText(this, "Network error: ${it.message()}", Toast.LENGTH_LONG).show()
                }

            })
        }
        else{
            binding.loadingSpinner.visibility = View.GONE
            showToast("Oops, looks like you are not connected to " +
                    "the internet.",Toast.LENGTH_SHORT)
        }
    }

    private fun getLastCommit(gitRepositpryItemSelected: GitRepositoriesItem, position : Int){
        if(NetworkUtils.hasNetwork(this)!!){
            EspressoIdlingResource.increment()
            val responseLiveData = gitRepoViewModel.getLastCommit(
                gitRepositpryItemSelected)

            responseLiveData.observe(this, Observer {
                EspressoIdlingResource.decrement()
                if(it.body() != null){
                    val lastCommit = it.body()?.listIterator()?.next()
                    gitRepositpryItemSelected.myCommitterName =
                        lastCommit?.commit?.committer?.name.toString()
                    gitRepositpryItemSelected.myCommitDate =
                        lastCommit?.commit?.committer?.date.toString()
                    gitRepositpryItemSelected.myCommitMessage =
                        lastCommit?.commit?.message.toString()
                    gitRepositpryItemSelected.myCommitterAvatarUrl=
                        lastCommit?.committer?.avatar_url.toString()

                    adapter.updateList(gitRepositpryItemSelected,position)
                    adapter.notifyItemChanged(position)

                }
                else{
                    showToast("Network error: ${it.message()}",Toast.LENGTH_LONG)
                }
            })
        }
        else{
            showToast("Oops, looks like you are not connected to " +
                    "the internet.",Toast.LENGTH_SHORT)

        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        getGitRepositories(gitUsers[position])
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
    private fun showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(this, message, duration).show()
    }

}