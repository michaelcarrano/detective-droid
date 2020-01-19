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

        viewModel.dispatch(Action.LoadApps(sharedPreferences.getBoolean("pref_system_apps", false)))
    }

    override fun onDestroyView() {
        binding.appsRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
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
        Snackbar.make(requireView(), R.string.error_loading_apps, Snackbar.LENGTH_LONG)
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
}
