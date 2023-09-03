package com.michaelcarrano.detectivedroid.presentation.appdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.michaelcarrano.detectivedroid.R
import com.michaelcarrano.detectivedroid.databinding.FragmentAppDetailBinding
import com.michaelcarrano.detectivedroid.presentation.model.LibraryUiModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppDetailFragment : Fragment() {

    @Inject
    protected lateinit var viewModelFactory: AppDetailViewModelFactory

    private val viewModel by viewModels<AppDetailViewModel> { viewModelFactory }

    private val clickListener: ClickListener = this::onLibraryClick

    private val recyclerViewAdapter: LibraryAdapter by lazy {
        LibraryAdapter(emptyList(), clickListener)
    }

    private val args: AppDetailFragmentArgs by navArgs()

    private var _binding: FragmentAppDetailBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAppDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.observableState.observe(
            viewLifecycleOwner,
            Observer { state ->
                state?.let { renderState(state) }
            },
        )

        viewModel.dispatch(Action.ScanApp(args.app.packageName))
    }

    override fun onDestroyView() {
        binding.librariesRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }

    private fun renderState(state: State) {
        with(state) {
            when {
                isScanning -> renderScanningState()
                isError -> renderErrorState()
                isEmpty -> renderEmptyState()
                else -> renderLibrariesState(libraries)
            }
        }
    }

    private fun renderScanningState() {
        binding.loadingIndicator.visibility = View.VISIBLE
    }

    private fun renderErrorState() {
        binding.loadingIndicator.visibility = View.GONE
        Snackbar.make(requireView(), R.string.error_scanning_app, LENGTH_LONG).show()
    }

    private fun renderEmptyState() {
        binding.loadingIndicator.visibility = View.GONE
        binding.librariesRecyclerView.visibility = View.GONE
        binding.noLibrariesFound.visibility = View.VISIBLE
    }

    private fun renderLibrariesState(libraries: List<LibraryUiModel>) {
        binding.loadingIndicator.visibility = View.GONE
        recyclerViewAdapter.updateLibraries(libraries)
        binding.librariesRecyclerView.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        binding.librariesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.librariesRecyclerView.adapter = recyclerViewAdapter
        binding.librariesRecyclerView.setHasFixedSize(true)
    }

    private fun onLibraryClick(library: LibraryUiModel) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(library.source)
        startActivity(intent)
    }
}
