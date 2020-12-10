package com.michaelcarrano.detectivedroid.presentation.applist

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.michaelcarrano.detectivedroid.R
import com.michaelcarrano.detectivedroid.databinding.FragmentAppListBinding
import com.michaelcarrano.detectivedroid.presentation.appdetail.AppDetailFragmentArgs
import com.michaelcarrano.detectivedroid.presentation.model.AppUiModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AppListFragment : DaggerFragment() {

    @Inject protected lateinit var viewModelFactory: AppListViewModelFactory

    @Inject protected lateinit var packageManager: PackageManager

    @Inject protected lateinit var sharedPreferences: SharedPreferences

    private val viewModel by viewModels<AppListViewModel> { viewModelFactory }

    private val clickListener: ClickListener = this::onAppClick

    private val recyclerViewAdapter: AppAdapter by lazy {
        AppAdapter(packageManager, clickListener)
    }

    private val showSystemApps
        get() = sharedPreferences.getBoolean("pref_system_apps", false)

    private var _binding: FragmentAppListBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentAppListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.observableState.observe(viewLifecycleOwner, Observer { state ->
            state?.let { renderState(state) }
        })

        viewModel.dispatch(Action.LoadApps(showSystemApps))
    }

    override fun onDestroyView() {
        binding.appsRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        setupSearch(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(this)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun renderState(state: State) {
        with(state) {
            when {
                isLoading -> renderLoadingState()
                isError -> renderErrorState()
                else -> renderAppsState(apps)
            }
        }
    }

    private fun renderLoadingState() {
        binding.loadingIndicator.visibility = View.VISIBLE
    }

    private fun renderErrorState() {
        binding.loadingIndicator.visibility = View.GONE
        Snackbar.make(requireView(), R.string.error_loading_apps, Snackbar.LENGTH_LONG).show()
    }

    private fun renderAppsState(apps: List<AppUiModel>) {
        binding.loadingIndicator.visibility = View.GONE
        recyclerViewAdapter.updateApps(apps)
        binding.appsRecyclerView.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        binding.appsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.appsRecyclerView.adapter = recyclerViewAdapter
        binding.appsRecyclerView.setHasFixedSize(true)
    }

    private fun onAppClick(app: AppUiModel) {
        findNavController(this).navigate(
            R.id.appDetailFragment,
            AppDetailFragmentArgs(app.name, app).toBundle()
        )
    }

    private fun setupSearch(menu: Menu) {
        val searchItem = handleSearchExpand(menu)
        handleSearchQuery(searchItem)
    }

    private fun handleSearchQuery(searchItem: MenuItem?) {
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                doSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                doSearch(newText)
                return true
            }
        })
    }

    private fun handleSearchExpand(menu: Menu): MenuItem? {
        val settingsItem = menu.findItem(R.id.action_settings)
        val searchItem = menu.findItem(R.id.action_search)

        val expandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                settingsItem.isVisible = true
                return true // Return true to collapse action view
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                settingsItem.isVisible = false
                return true // Return true to expand action view
            }
        }

        searchItem.setOnActionExpandListener(expandListener)
        return searchItem
    }

    private fun doSearch(query: String?) {
        query?.let {
            viewModel.dispatch(Action.Search(it, showSystemApps))
        }
    }
}
