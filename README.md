# GitRepositoriesApiDisplay

## About this project:

* Its a single activity app that uses Git REST Api(https://api.github.com) to fetch the public repositories of any selected git user. It shows the list of repositories of the selected git user in a RecyclerView,
then gets the "last commit" data for each git repository asynchronously and updates the list (RecyclerView).

* The App has Clean Architecture, follows MVVM pattern and TDD(Test Driven Development).
* Common use-cases of Kotlin Coroutines in Android has been implemented in this project.
* The ViewModel uses LiveData and Data Binding to update the UI. 
* Also, learned and implemented writing Unit-Test for ViewModel which uses Kotlin Coroutines using Mockito.
* Created UI tests for the Activity using Esspresso.

## App Components

### 1. GitRepoActivity
Its the main View of the application. It has a dropdown menu(Spinner) with some prominent git users. Shows the list of Public git repositories of that user in a RecyclerView.
Also it fetches the Commits History of each repository Asynchronously(using Kotlin Coroutines), and shows the last commit details of each repository by updating the RecyclerView
adapter.
It interacts with a ViewModel to fetch the Api Data.

### 2.GitRepoViewModel
It has the logical code to fetch the Api data and provide to the View. It requests all the data from the local Repository using Kotlin coroutines.
Also, Uses Data Binding and Live Data to update the UI.

### 3.Repository Layer
It has classes to request the network Api for git user data and provide it to the ViewModel.

### 4.Data Layer
It has classes to do the network calls to the Git Api (https://api.github.com) to fetch user data using Retrofit and OKHTTP logging.

### 6. UI Tests
There are tests in place to ensure the UI is visible and all the components of it are to the point. Used Esspresso for UI tests and Esspresso Idling Resourse for 
RecyclerView.

### 7. Unit Test
ViewModel test by mocking objects using Mockito.


